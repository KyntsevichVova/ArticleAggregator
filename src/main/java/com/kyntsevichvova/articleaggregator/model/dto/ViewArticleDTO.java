package com.kyntsevichvova.articleaggregator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ViewArticleDTO {

    private Long id;

    private String annotation;

    private String title;

    private String articleText;

    private List<ViewAuthorDTO> articleAuthors;

    private String link;

}
