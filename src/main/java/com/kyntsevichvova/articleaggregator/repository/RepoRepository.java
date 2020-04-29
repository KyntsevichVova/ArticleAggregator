package com.kyntsevichvova.articleaggregator.repository;

import com.kyntsevichvova.articleaggregator.model.entity.Repo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepoRepository extends JpaRepository<Repo, Long> {

    Repo findByName(String name);

}
