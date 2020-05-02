package com.kyntsevichvova.articleaggregator.facade.impl;

import com.kyntsevichvova.articleaggregator.facade.ArticleFacade;
import com.kyntsevichvova.articleaggregator.model.dto.CreateArticleDTO;
import com.kyntsevichvova.articleaggregator.model.entity.Article;
import com.kyntsevichvova.articleaggregator.model.entity.Author;
import com.kyntsevichvova.articleaggregator.model.entity.Repo;
import com.kyntsevichvova.articleaggregator.repository.ArticleRepository;
import com.kyntsevichvova.articleaggregator.repository.AuthorRepository;
import com.kyntsevichvova.articleaggregator.service.AuthorService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class ArticleFacadeImpl implements ArticleFacade {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

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
    public void saveArticleToSolr(Article article) {

    }

}
