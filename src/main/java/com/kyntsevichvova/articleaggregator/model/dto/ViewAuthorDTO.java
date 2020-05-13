package com.kyntsevichvova.articleaggregator.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ViewAuthorDTO {

    private Long id;

    private String name;

}
