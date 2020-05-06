package com.kyntsevichvova.articleaggregator.scrapper;

import com.kyntsevichvova.articleaggregator.model.dto.CreateArticleDTO;

import java.util.List;

public interface RepositoryScrapper {

    List<CreateArticleDTO> scrap();

}
