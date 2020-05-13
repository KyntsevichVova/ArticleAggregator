package com.kyntsevichvova.articleaggregator.controller;

import com.kyntsevichvova.articleaggregator.facade.ArticleFacade;
import com.kyntsevichvova.articleaggregator.model.dto.ViewArticleDTO;
import com.kyntsevichvova.articleaggregator.model.dto.ViewArticlesDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class ArticleController {

    @Autowired
    private ArticleFacade articleFacade;

    @GetMapping("/articles")
    public ViewArticlesDTO getArticles(@RequestParam(value = "query", required = false) String query,
                                       @RequestParam(value = "offset", required = false, defaultValue = "0") int offset,
                                       @RequestParam(value = "limit", required = false, defaultValue = "10") int limit) {
        return articleFacade.getArticlesByQuery(query, offset, limit);
    }

    @GetMapping("/article/{id}")
    public ViewArticleDTO getArticle(@PathVariable(value = "id") Long id) {
        return articleFacade.getArticleById(id);
    }

}
