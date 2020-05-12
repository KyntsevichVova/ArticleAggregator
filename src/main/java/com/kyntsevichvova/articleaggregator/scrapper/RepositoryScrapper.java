package com.kyntsevichvova.articleaggregator.scrapper;

import com.kyntsevichvova.articleaggregator.model.dto.CreateArticleDTO;
import com.kyntsevichvova.articleaggregator.model.dto.ScrapArticleDTO;

import java.util.List;

public interface RepositoryScrapper {

    List<ScrapArticleDTO> getAvailableArticles();

    CreateArticleDTO scrapArticle(ScrapArticleDTO scrapArticleDTO);

}
