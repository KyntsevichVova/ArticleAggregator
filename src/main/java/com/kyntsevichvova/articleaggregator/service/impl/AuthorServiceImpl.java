package com.kyntsevichvova.articleaggregator.service.impl;

import com.kyntsevichvova.articleaggregator.model.entity.Article;
import com.kyntsevichvova.articleaggregator.model.entity.ArticleAuthor;
import com.kyntsevichvova.articleaggregator.model.entity.ArticleAuthorId;
import com.kyntsevichvova.articleaggregator.model.entity.Author;
import com.kyntsevichvova.articleaggregator.model.entity.Repo;
import com.kyntsevichvova.articleaggregator.repository.ArticleAuthorRepository;
import com.kyntsevichvova.articleaggregator.repository.AuthorRepository;
import com.kyntsevichvova.articleaggregator.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ArticleAuthorRepository articleAuthorRepository;

    @Override
    public Author getOrCreateAuthorByName(Repo repo, String name) {
        return Optional.ofNullable(authorRepository.getByRepoAndName(repo, name))
                .orElseGet(() -> createAuthorForRepo(repo, name));
    }

    @Override
    public Author createAuthorForRepo(Repo repo, String name) {
        Author author = Author.builder()
                .repo(repo)
                .name(name)
                .articleAuthors(new ArrayList<>())
                .build();

        return authorRepository.save(author);
    }

    @Override
    public void addArticleAuthor(Article article, Author author) {
        ArticleAuthorId articleAuthorId = new ArticleAuthorId();
        articleAuthorId.setArticleId(article.getId());
        articleAuthorId.setAuthorId(author.getId());

        ArticleAuthor articleAuthor = new ArticleAuthor();
        articleAuthor.setArticle(article);
        articleAuthor.setAuthor(author);
        articleAuthor.setArticleAuthorId(articleAuthorId);

        articleAuthorRepository.save(articleAuthor);

        article.getArticleAuthors().add(articleAuthor);
        author.getArticleAuthors().add(articleAuthor);
    }

}
