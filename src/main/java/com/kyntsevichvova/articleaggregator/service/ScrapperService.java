package com.kyntsevichvova.articleaggregator.service;

import com.kyntsevichvova.articleaggregator.repository.ArticleRepository;
import com.kyntsevichvova.articleaggregator.scrapper.RepositoryScrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapperService {

    private final List<RepositoryScrapper> scrappers;
    private final ArticleRepository articleRepository;

    @Autowired
    public ScrapperService(List<RepositoryScrapper> scrappers, ArticleRepository articleRepository) {
        this.scrappers = scrappers;
        this.articleRepository = articleRepository;
    }

    @Scheduled(cron = "${scrapping.schedule.cron}")
    public void deltaScrap() {
        for (var scrapper : scrappers) {
            articleRepository.saveAll(scrapper.deltaScrap());
        }
    }

    public void fullScrap() {
        for (var scrapper : scrappers) {
            articleRepository.saveAll(scrapper.fullScrap());
        }
    }
}