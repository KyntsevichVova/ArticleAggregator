package com.kyntsevichvova.articleaggregator.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REPO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repo {

    @Id @Column(name = "ID", nullable = false, unique = true)
    private Long id;

    @Column(name = "LINK")
    private String link;

    @Column(name = "NAME")
    private String name;

    @Column(name = "HOSTNAME", nullable = false)
    private String hostname;

}
