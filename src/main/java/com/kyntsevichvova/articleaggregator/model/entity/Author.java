package com.kyntsevichvova.articleaggregator.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "AUTHOR")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "REPO_ID")
    private Repo repo;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "LINK")
    private String link;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<ArticleAuthor> articleAuthors;

}
