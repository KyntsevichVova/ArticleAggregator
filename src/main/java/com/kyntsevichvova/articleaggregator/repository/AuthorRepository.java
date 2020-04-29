package com.kyntsevichvova.articleaggregator.repository;

import com.kyntsevichvova.articleaggregator.model.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
}
