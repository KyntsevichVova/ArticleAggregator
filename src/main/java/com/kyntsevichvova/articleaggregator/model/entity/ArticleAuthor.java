package com.kyntsevichvova.articleaggregator.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "ARTICLE_AUTHOR")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleAuthor {

    @EmbeddedId
    private ArticleAuthorId articleAuthorId;

    @ManyToOne
    @MapsId("articleId")
    @JoinColumn(name = "ARTICLE_ID")
    private Article article;

    @ManyToOne
    @MapsId("authorId")
    @JoinColumn(name = "AUTHOR_ID")
    private Author author;

}
