package com.kyntsevichvova.articleaggregator.model.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ARTICLE")
@Data
@Builder
public class Article {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "YEAR")
    private Integer year;

    @Column(name = "TITLE")
    private String title;

}
