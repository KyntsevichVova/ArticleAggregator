package com.kyntsevichvova.articleaggregator.scrapper.impl;

import com.kyntsevichvova.articleaggregator.common.ApplicationConstant;
import com.kyntsevichvova.articleaggregator.model.dto.CreateArticleDTO;
import com.kyntsevichvova.articleaggregator.model.dto.ScrapArticleDTO;
import com.kyntsevichvova.articleaggregator.model.entity.Repo;
import com.kyntsevichvova.articleaggregator.repository.RepoRepository;
import com.kyntsevichvova.articleaggregator.scrapper.RepositoryScrapper;
import org.apache.commons.lang3.tuple.Pair;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BsuirScrapper implements RepositoryScrapper {

    private final String PATH = "/browse?type=dateissued&sort_by=2&order=DESC&rpp=100";

    private final String REPO_NAME = "bsuir";

    private final Repo repo;

    @Autowired
    public BsuirScrapper(RepoRepository repoRepository) {
        repo = repoRepository.findByName(REPO_NAME);
    }

    @Override
    public List<ScrapArticleDTO> getAvailableArticles() {
        List<ScrapArticleDTO> articles = new ArrayList<>();
        try {
            Document document = Jsoup.connect(repo.getHostname() + PATH)
                    .userAgent(ApplicationConstant.FIREFOX_USER_AGENT)
                    .get();

            boolean hasNext = true;
            do {
                Elements elements = document.select("div.container div.panel table tbody tr");
                elements.remove(0);
                for (Element element : elements) {
                    Elements children = element.children();
                    Element secondColumn = children.eq(1).get(0);
                    String articleUrl = repo.getHostname() +
                            secondColumn.select("a").get(0).attr("href");
                    articles.add(new ScrapArticleDTO(repo, articleUrl, articleUrl));
                }
                Element next = document.selectFirst("div.container div.panel div.panel-heading a.pull-right");
                if (next == null) {
                    hasNext = false;
                } else {
                    document = Jsoup.connect(repo.getHostname() + next.attr("href"))
                            .userAgent(ApplicationConstant.FIREFOX_USER_AGENT)
                            .get();
                }
            } while (hasNext);
        } catch (IOException e) {

        }
        return articles;
    }

    @Override
    public CreateArticleDTO scrapArticle(ScrapArticleDTO scrapArticleDTO) {

        String articleUrl = scrapArticleDTO.getUrl() + "?mode=full";

        try {
            Document document = Jsoup.connect(articleUrl)
                    .userAgent(ApplicationConstant.FIREFOX_USER_AGENT)
                    .get();

            Elements elements = document.select("div.container div.panel table tbody tr");

            List<Pair<String, String>> fields = new ArrayList<>();

            elements.remove(0);
            for (var element : elements) {
                Elements children = element.children();
                String first = children.eq(0).get(0).text();
                String second = children.eq(1).get(0).text();
                fields.add(Pair.of(first, second));
            }

            String link = scrapArticleDTO.getUrl();
            String title = getField(fields, "dc.title");
            String annotation = getField(fields, "dc.description.abstract");
            List<String> articleAuthors = fields.stream()
                    .filter(p -> p.getKey().equals("dc.contributor.author"))
                    .map(p -> p.getValue())
                    .collect(Collectors.toList());

            Elements articleFileElements = document.select("div.container div.panel table tbody tr td a.btn");

            String articleFileUrl;
            try {
                articleFileUrl = repo.getHostname() + articleFileElements.get(0).attr("href");
            } catch (IndexOutOfBoundsException e) {
                System.out.println(articleUrl);
                articleFileUrl = "";
            }

            return CreateArticleDTO.builder()
                    .repo(repo)
                    .articleId(link)
                    .annotation(annotation)
                    .link(link)
                    .title(title)
                    .articleAuthors(articleAuthors)
                    .articleFileUrl(articleFileUrl)
                    .build();
        } catch (IOException e) {
            return null;
        }
    }

    private String getField(List<Pair<String, String>> fields, String key) {
        return fields.stream()
                .filter(p -> p.getKey().equals(key))
                .map(p -> p.getValue())
                .findFirst()
                .orElse("");
    }

}
