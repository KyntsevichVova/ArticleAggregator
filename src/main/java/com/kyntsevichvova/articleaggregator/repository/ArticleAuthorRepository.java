package com.kyntsevichvova.articleaggregator.repository;

import com.kyntsevichvova.articleaggregator.model.entity.ArticleAuthor;
import com.kyntsevichvova.articleaggregator.model.entity.ArticleAuthorId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleAuthorRepository extends JpaRepository<ArticleAuthor, ArticleAuthorId> {
}
