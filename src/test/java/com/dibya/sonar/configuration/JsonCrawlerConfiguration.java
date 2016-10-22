package com.dibya.sonar.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.dibya.sonar.entity.vo.Page;
import com.dibya.sonar.entity.vo.ScmDetails;
import com.dibya.sonar.json.crawler.AbstractJsonCrawler;
import com.dibya.sonar.json.crawler.JsonCrawler;

@Configuration
public class JsonCrawlerConfiguration {
    @Bean(name = "issueCrawler")
    public JsonCrawler<Page> issueCrawler() {
        return new AbstractJsonCrawler<Page>() {

            @Override
            protected Page retriveJsonFromUrl(String url) {
                RestTemplate restTemplate = new RestTemplate();
                return restTemplate.getForObject(url, Page.class);
            }
        };

    }
    
    @Bean(name = "scmCrawler")
    public JsonCrawler<ScmDetails> scmCrawler() {
        return new AbstractJsonCrawler<ScmDetails>() {

            @Override
            protected ScmDetails retriveJsonFromUrl(String url) {
                RestTemplate restTemplate = new RestTemplate();
                return restTemplate.getForObject(url, ScmDetails.class);
            }
        };
    }
}
