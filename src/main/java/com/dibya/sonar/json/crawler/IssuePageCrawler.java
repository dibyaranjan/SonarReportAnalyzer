package com.dibya.sonar.json.crawler;

import org.springframework.stereotype.Service;

import com.dibya.sonar.entity.vo.Page;

@Service("issueCrawler")
public class IssuePageCrawler extends AbstractJsonCrawler<Page> {
	
    @Override
    protected Page retriveJsonFromUrl(String url) {
        return restTemplate.getForObject(url, Page.class);
    }

}
