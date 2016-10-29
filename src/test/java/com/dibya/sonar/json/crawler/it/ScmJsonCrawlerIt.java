package com.dibya.sonar.json.crawler.it;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dibya.sonar.configuration.PageCrawlerBean;
import com.dibya.sonar.entity.vo.ScmDetails;
import com.dibya.sonar.json.crawler.JsonCrawler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PageCrawlerBean.class })
public class ScmJsonCrawlerIt {
    @Autowired
    private JsonCrawler<ScmDetails> scmCrawler;
    
    @Test(expected = IllegalArgumentException.class)
    public void testSyncWithMalformedUrl() {
        ScmDetails json = scmCrawler.retrieveJsonContentFromUrl("asdfdsf");

        Assert.assertNull("Invalid port should be null", json);
    }

    
    @Test(expected = IllegalArgumentException.class)
    public void testSyncWithNullUrl() {
        ScmDetails json = scmCrawler.retrieveJsonContentFromUrl(null);

        Assert.assertNull("Invalid port should be null", json);
    }


    @Test(expected = IllegalArgumentException.class)
    public void testSyncWithInvalidPort() {
        ScmDetails json = scmCrawler.retrieveJsonContentFromUrl("http://localhost:123/");

        Assert.assertNull("Invalid port should be null", json);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSyncWithInvalidCharacter() {
        ScmDetails json = scmCrawler.retrieveJsonContentFromUrl("http://localhost:9000/s");

        Assert.assertNull("Invalid port should be null", json);
    }
}
