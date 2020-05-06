package com.kyntsevichvova.articleaggregator.model.dto;

import com.kyntsevichvova.articleaggregator.model.entity.Repo;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CreateArticleDTO {

    private Repo repo;

    private String articleId;

    private String link;

    private String title;

    private String annotation;

    private String articleText;

    private String articleFileUrl;

    private List<String> articleAuthors;

}
