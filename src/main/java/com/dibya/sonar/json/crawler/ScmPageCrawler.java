package com.dibya.sonar.json.crawler;

import org.springframework.web.client.RestTemplate;

import com.dibya.sonar.entity.vo.ScmDetails;

public class ScmPageCrawler extends AbstractJsonCrawler<ScmDetails> {

    @Override
    protected ScmDetails retriveJsonFromUrl(String url) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(url, ScmDetails.class);
    }

}
