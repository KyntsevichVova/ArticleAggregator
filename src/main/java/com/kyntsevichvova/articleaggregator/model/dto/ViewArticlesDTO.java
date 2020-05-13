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
public class ViewArticlesDTO {

    private long offset;

    private long limit;

    private long total;

    private List<ViewArticleDTO> articles;

}
