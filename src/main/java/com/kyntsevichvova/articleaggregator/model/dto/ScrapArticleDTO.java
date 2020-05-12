package com.kyntsevichvova.articleaggregator.model.dto;

import com.kyntsevichvova.articleaggregator.model.entity.Repo;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ScrapArticleDTO {

    private Repo repo;
    private String articleId;
    private String url;

}
