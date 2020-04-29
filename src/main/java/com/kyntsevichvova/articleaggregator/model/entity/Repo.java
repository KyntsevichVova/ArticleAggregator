package com.kyntsevichvova.articleaggregator.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REPO")
@Data
public class Repo {

    @Id @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column
    private String link;

    @Column
    private String name;

}
