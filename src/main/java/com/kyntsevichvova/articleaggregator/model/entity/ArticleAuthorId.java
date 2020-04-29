package com.kyntsevichvova.articleaggregator.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
public class ArticleAuthorId implements Serializable {

    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "AUTHOR_ID")
    private Long authorId;

}
