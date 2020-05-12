package com.kyntsevichvova.articleaggregator.service.impl;

import com.kyntsevichvova.articleaggregator.facade.ArticleFacade;
import com.kyntsevichvova.articleaggregator.model.dto.CreateArticleDTO;
import com.kyntsevichvova.articleaggregator.model.dto.ScrapArticleDTO;
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
        /*for (var article : articleFacade.getArticles()) {
            articleFacade.saveArticleToSolr(article);
        }*/
        for (var scrapper : scrappers) {
            List<ScrapArticleDTO> availableArticles = scrapper.getAvailableArticles();
            for (var availableArticle : availableArticles) {
                if (availableArticle != null && !articleFacade.isArticlePresent(availableArticle.getRepo(), availableArticle.getArticleId())) {
                    CreateArticleDTO createArticleDTO = scrapper.scrapArticle(availableArticle);
                    if (createArticleDTO != null) {
                        articleFacade.saveArticle(createArticleDTO);
                    }
                }
            }
        }
    }

}