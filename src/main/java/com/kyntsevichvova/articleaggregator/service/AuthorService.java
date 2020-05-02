package com.kyntsevichvova.articleaggregator.service;

import com.kyntsevichvova.articleaggregator.model.entity.Article;
import com.kyntsevichvova.articleaggregator.model.entity.Author;
import com.kyntsevichvova.articleaggregator.model.entity.Repo;

public interface AuthorService {

    Author getOrCreateAuthorByName(Repo repo, String name);

    Author createAuthorForRepo(Repo repo, String name);

    void addArticleAuthor(Article article, Author author);

}
