package com.kyntsevichvova.articleaggregator;

import com.kyntsevichvova.articleaggregator.service.ScrapperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.kyntsevichvova.articleaggregator.repository")
public class ArticleAggregatorApplication implements ApplicationRunner {

    @Autowired
    private ScrapperService scrapperService;

    public static void main(String[] args) {
        SpringApplication.run(ArticleAggregatorApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        scrapperService.fullScrap();
    }
}