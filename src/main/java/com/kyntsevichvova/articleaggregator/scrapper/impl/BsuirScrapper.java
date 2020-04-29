package com.kyntsevichvova.articleaggregator.scrapper.impl;

import com.kyntsevichvova.articleaggregator.model.entity.Article;
import com.kyntsevichvova.articleaggregator.model.entity.Repo;
import com.kyntsevichvova.articleaggregator.repository.RepoRepository;
import com.kyntsevichvova.articleaggregator.scrapper.RepositoryScrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BsuirScrapper implements RepositoryScrapper {

    private final String HOSTNAME = "https://libeldoc.bsuir.by";
    private final String PATH = "/browse?type=dateissued&sort_by=2&order=DESC&rpp=100";
    private final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:70.0) Gecko/20100101 Firefox/70.0";

    private final String REPO_NAME = "bsuir";

    private Repo repo;

    @Autowired
    public BsuirScrapper(RepoRepository repoRepository) {
        repo = repoRepository.findByName(REPO_NAME);
    }

    @Override
    public List<Article> fullScrap() {
        List<Article> articles = new ArrayList<>();
        try {
            Document document = Jsoup.connect(HOSTNAME + PATH)
                    .userAgent(USER_AGENT)
                    .get();

            boolean hasNext = true;
            do {
                Elements elements = document.select("div.container div.panel table tbody tr");
                boolean first = true;
                for (Element element : elements) {
                    if (first) {
                        first = false;
                    } else {
                        Elements children = element.children();
                        String title = children.eq(1).get(0).text();
                        articles.add(Article.builder()
                                .repo_id(repo.getId())
                                .title(title)
                                .build()
                        );
                    }
                }
                Element next = document.selectFirst("div.container div.panel div.panel-heading a.pull-right");
                if (next == null) {
                    hasNext = false;
                } else {
                    document = Jsoup.connect(HOSTNAME + next.attr("href"))
                            .userAgent(USER_AGENT)
                            .get();
                }
            } while (hasNext);
        } catch (IOException e) {

        }
        return articles;
    }

    @Override
    public List<Article> deltaScrap() {
        return null;
    }
}
