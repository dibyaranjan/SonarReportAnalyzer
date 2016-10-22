package com.dibya.sonar.json.crawler;

import org.springframework.web.client.RestTemplate;

import com.dibya.sonar.entity.vo.Page;

public class IssuePageCrawler extends AbstractJsonCrawler<Page> {

    @Override
    protected Page retriveJsonFromUrl(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, Page.class);
    }

}
