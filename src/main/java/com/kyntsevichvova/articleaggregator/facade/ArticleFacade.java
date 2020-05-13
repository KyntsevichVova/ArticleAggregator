package com.kyntsevichvova.articleaggregator.facade;

import com.kyntsevichvova.articleaggregator.model.dto.CreateArticleDTO;
import com.kyntsevichvova.articleaggregator.model.dto.ViewArticleDTO;
import com.kyntsevichvova.articleaggregator.model.dto.ViewArticlesDTO;
import com.kyntsevichvova.articleaggregator.model.entity.Article;
import com.kyntsevichvova.articleaggregator.model.entity.Repo;

import java.util.List;

public interface ArticleFacade {

    void saveArticles(List<CreateArticleDTO> articleDTOList);

    void saveArticle(CreateArticleDTO articleDTO);

    void saveArticleToSolr(Article article);

    boolean isArticlePresent(Repo repo, String articleId);

    byte[] downloadArticleFile(String articleFileUrl);

    List<Article> getArticles();

    String parseFile(byte[] file);

    ViewArticlesDTO getArticlesByQuery(String query, int offset, int limit);

    ViewArticleDTO getArticleById(Long id);
}
