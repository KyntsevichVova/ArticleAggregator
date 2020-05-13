package com.kyntsevichvova.articleaggregator.facade.impl;

import com.kyntsevichvova.articleaggregator.facade.ArticleFacade;
import com.kyntsevichvova.articleaggregator.model.converter.SolrListToViewArticleDTOConverter;
import com.kyntsevichvova.articleaggregator.model.dto.CreateArticleDTO;
import com.kyntsevichvova.articleaggregator.model.dto.ViewArticleDTO;
import com.kyntsevichvova.articleaggregator.model.dto.ViewArticlesDTO;
import com.kyntsevichvova.articleaggregator.model.entity.Article;
import com.kyntsevichvova.articleaggregator.model.entity.ArticleAuthor;
import com.kyntsevichvova.articleaggregator.model.entity.Author;
import com.kyntsevichvova.articleaggregator.model.entity.Repo;
import com.kyntsevichvova.articleaggregator.repository.ArticleRepository;
import com.kyntsevichvova.articleaggregator.repository.AuthorRepository;
import com.kyntsevichvova.articleaggregator.service.AuthorService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ArticleFacadeImpl implements ArticleFacade {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private SolrClient solrClient;

    @Autowired
    private SolrListToViewArticleDTOConverter solrListToViewArticleDTOConverter;

    @Override
    public void saveArticles(List<CreateArticleDTO> articleDTOList) {
        for (var articleDTO : articleDTOList) {
            saveArticle(articleDTO);
        }
    }

    @Transactional
    @Override
    public void saveArticle(CreateArticleDTO articleDTO) {
        Repo repo = articleDTO.getRepo();

        if (isArticlePresent(repo, articleDTO.getArticleId())) {
            return;
        }

        List<Author> authors = new ArrayList<>();
        for (var authorName : articleDTO.getArticleAuthors()) {
            authors.add(authorService.getOrCreateAuthorByName(repo, authorName));
        }

        byte[] articleFile = downloadArticleFile(articleDTO.getArticleFileUrl());

        if (articleFile != null && articleFile.length > 0) {
            articleDTO.setArticleText(parseFile(articleFile));
        }

        Article article = Article.builder()
                .repo(repo)
                .articleFileUrl(articleDTO.getArticleFileUrl())
                .articleId(articleDTO.getArticleId())
                .annotation(articleDTO.getAnnotation())
                .link(articleDTO.getLink())
                .title(articleDTO.getTitle())
                .articleFile(articleFile)
                .articleText(articleDTO.getArticleText())
                .articleAuthors(new ArrayList<>())
                .build();

        Article savedArticle = articleRepository.save(article);
        savedArticle.setArticleAuthors(new ArrayList<>());

        for (var author : authors) {
            authorService.addArticleAuthor(savedArticle, author);
        }

        saveArticleToSolr(savedArticle);
    }

    @Override
    public boolean isArticlePresent(Repo repo, String articleId) {
        return articleRepository.findByRepoAndArticleId(repo, articleId).isPresent();
    }

    @Override
    public byte[] downloadArticleFile(String articleFileUrl) {
        byte[] file = null;

        if (StringUtils.isNotEmpty(articleFileUrl)) {
            try (InputStream inputStream = new URL(articleFileUrl).openStream()) {
                file = IOUtils.toByteArray(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return file;
    }

    @Override
    public String parseFile(byte[] file) {
        Tika tika = new Tika();
        try (InputStream inputStream = new ByteArrayInputStream(file)) {
            String text = tika.parseToString(inputStream);
            //System.out.println(text);
            return text;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ViewArticlesDTO getArticlesByQuery(String query, int offset, int limit) {
        final Map<String, String> params = new HashMap<>();
        if (StringUtils.isNotEmpty(query)) {
            params.put("q", "query_text:" + query);
        } else {
            params.put("q", "*:*");
        }
        params.put("fl", "id, annotation, title, article_text, article_authors, link, repo_id");
        params.put("start", Integer.toString(offset, 10));
        params.put("rows", Integer.toString(limit, 10));
        ViewArticlesDTO results;
        try {
            QueryResponse response = solrClient.query(new MapSolrParams(params));
            SolrDocumentList list = response.getResults();
            results = solrListToViewArticleDTOConverter.convert(list);
            results.setOffset(offset);
            results.setLimit(limit);
        } catch (Exception e) {
            e.printStackTrace();
            results = new ViewArticlesDTO();
        }
        return results;
    }

    @Override
    public ViewArticleDTO getArticleById(Long id) {
        ViewArticlesDTO articlesDTO = getArticlesByQuery("", 0, 1);
        if (articlesDTO.getTotal() < 1) {
            return null;
        }
        return articlesDTO.getArticles().get(0);
    }

    @Override
    public void saveArticleToSolr(Article article) {
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id", article.getId());
        solrInputDocument.addField("repo_id", article.getRepo().getId());
        solrInputDocument.addField("link", article.getLink());
        solrInputDocument.addField("title", article.getTitle());
        solrInputDocument.addField("annotation", article.getAnnotation());
        solrInputDocument.addField("article_text", article.getArticleText());
        article.getArticleAuthors()
                .stream()
                .map(ArticleAuthor::getAuthor)
                .map(Author::getName)
                .forEach(s -> solrInputDocument.addField("article_authors", s));

        try {
            solrClient.add(solrInputDocument);
            solrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Article> getArticles() {
        return articleRepository.findAll();
    }
}
