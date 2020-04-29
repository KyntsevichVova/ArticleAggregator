package com.kyntsevichvova.articleaggregator.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "ARTICLE")
@Data
@Builder
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "REPO_ID")
    private Repo repo;

    @Column(name = "ARTICLE_ID")
    private String article_id;

    @Column(name = "LINK")
    private String link;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "ANNOTATION")
    private String annotation;

    @Column(name = "ARTICLE_TEXT")
    private String article_text;

    @OneToMany(mappedBy = "article")
    private List<ArticleAuthor> articleAuthors;

}
