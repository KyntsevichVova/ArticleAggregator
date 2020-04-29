package com.kyntsevichvova.articleaggregator.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "author")
@Data
public class Author {

    @Id @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "REPO_ID")
    private Long repo_id;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LINK")
    private String link;

    @OneToMany(mappedBy = "author")
    private List<ArticleAuthor> articleAuthors;

}