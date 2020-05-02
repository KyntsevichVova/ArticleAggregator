package com.kyntsevichvova.articleaggregator.service.impl;

import com.kyntsevichvova.articleaggregator.facade.ArticleFacade;
import com.kyntsevichvova.articleaggregator.scrapper.RepositoryScrapper;
import com.kyntsevichvova.articleaggregator.service.ScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScrapperServiceImpl implements ScrapperService {

    @Autowired
    private List<RepositoryScrapper> scrappers;

    @Autowired
    private ArticleFacade articleFacade;

    @Scheduled(cron = "${scrapping.schedule.cron}")
    public void scrap() {
        for (var scrapper : scrappers) {
            articleFacade.saveArticles(scrapper.scrap());
        }
    }

}