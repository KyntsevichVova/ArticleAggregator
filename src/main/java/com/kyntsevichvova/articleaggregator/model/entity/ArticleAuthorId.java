package com.kyntsevichvova.articleaggregator.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"articleId", "authorId"})
public class ArticleAuthorId implements Serializable {

    @Column(name = "ARTICLE_ID")
    private Long articleId;

    @Column(name = "AUTHOR_ID")
    private Long authorId;

}
