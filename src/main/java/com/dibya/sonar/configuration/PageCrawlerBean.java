package com.dibya.sonar.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dibya.sonar.entity.vo.Page;
import com.dibya.sonar.entity.vo.ScmDetails;
import com.dibya.sonar.json.crawler.IssuePageCrawler;
import com.dibya.sonar.json.crawler.JsonCrawler;
import com.dibya.sonar.json.crawler.ScmPageCrawler;

@Configuration
public class PageCrawlerBean {

    @Bean(name = "issueCrawler")
    public JsonCrawler<Page> issueCrawler() {
        return new IssuePageCrawler();
    }

    @Bean(name = "scmCrawler")
    public JsonCrawler<ScmDetails> scmCrawler() {
        return new ScmPageCrawler();
    }

}
