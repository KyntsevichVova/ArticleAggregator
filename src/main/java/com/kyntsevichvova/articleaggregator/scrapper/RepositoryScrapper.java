package com.kyntsevichvova.articleaggregator.scrapper;

import com.kyntsevichvova.articleaggregator.model.entity.Article;

import java.util.List;

public interface RepositoryScrapper {
    List<Article> fullScrap();
    List<Article> deltaScrap();
}
