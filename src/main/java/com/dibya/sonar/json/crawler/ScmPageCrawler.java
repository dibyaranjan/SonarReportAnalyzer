package com.dibya.sonar.json.crawler;

import org.springframework.stereotype.Service;

import com.dibya.sonar.entity.vo.ScmDetails;

@Service("scmCrawler")
public class ScmPageCrawler extends AbstractJsonCrawler<ScmDetails> {
	
    @Override
    protected ScmDetails retriveJsonFromUrl(String url) {
        return restTemplate.getForObject(url, ScmDetails.class);
    }

}
