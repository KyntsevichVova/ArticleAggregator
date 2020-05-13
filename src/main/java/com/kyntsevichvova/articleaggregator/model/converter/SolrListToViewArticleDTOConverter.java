package com.kyntsevichvova.articleaggregator.model.converter;

import com.kyntsevichvova.articleaggregator.model.dto.ViewArticleDTO;
import com.kyntsevichvova.articleaggregator.model.dto.ViewArticlesDTO;
import com.kyntsevichvova.articleaggregator.model.dto.ViewAuthorDTO;
import com.kyntsevichvova.articleaggregator.model.entity.Author;
import com.kyntsevichvova.articleaggregator.model.entity.Repo;
import com.kyntsevichvova.articleaggregator.repository.RepoRepository;
import com.kyntsevichvova.articleaggregator.service.AuthorService;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SolrListToViewArticleDTOConverter {

    @Autowired
    private RepoRepository repoRepository;

    @Autowired
    private AuthorService authorService;

    public ViewArticlesDTO convert(SolrDocumentList list) {
        ViewArticlesDTO viewArticlesDTO = new ViewArticlesDTO();
        viewArticlesDTO.setTotal(list.getNumFound());
        viewArticlesDTO.setArticles(new ArrayList<>());

        for (var document : list) {
            Long repoId = Long.valueOf((String) document.getFieldValue("repo_id"), 10);
            Long articleId = Long.valueOf((String) document.getFieldValue("id"), 10);
            String link = (String) document.getFieldValue("link");
            String articleText = (String) document.getFieldValue("article_text");
            String annotation = (String) document.getFieldValue("annotation");
            String title = (String) document.getFieldValue("title");
            Repo repo = repoRepository.getOne(repoId);
            Object o = document.getFieldValue("article_authors");
            List<String> authors;
            if (o != null) {
                authors = (List<String>) o;
            } else {
                authors = new ArrayList<>();
            }
            List<ViewAuthorDTO> viewAuthorDTOList = new ArrayList<>();
            for (var authorName : authors) {
                Author articleAuthor = authorService.getAuthorByName(repo, authorName);
                ViewAuthorDTO authorDTO = new ViewAuthorDTO(articleAuthor.getId(), articleAuthor.getName());
                viewAuthorDTOList.add(authorDTO);
            }
            viewArticlesDTO.getArticles().add(
                    ViewArticleDTO.builder()
                            .id(articleId)
                            .annotation(annotation)
                            .articleAuthors(viewAuthorDTOList)
                            .articleText(articleText)
                            .link(link)
                            .title(title)
                            .build()
            );
        }
        return viewArticlesDTO;
    }

}
