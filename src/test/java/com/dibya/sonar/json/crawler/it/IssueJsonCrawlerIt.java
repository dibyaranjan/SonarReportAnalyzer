package com.dibya.sonar.json.crawler.it;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dibya.sonar.configuration.PageCrawlerBean;
import com.dibya.sonar.entity.vo.Page;
import com.dibya.sonar.json.crawler.JsonCrawler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PageCrawlerBean.class })
public class IssueJsonCrawlerIt {
    @Autowired
    private JsonCrawler<Page> issueCrawler;
    
    @Test(expected = IllegalArgumentException.class)
    public void testSyncWithMalformedUrl() {
        Page json = issueCrawler.retrieveJsonContentFromUrl("asdfdsf");

        Assert.assertNull("Invalid port should be null", json);
    }

    
    @Test(expected = IllegalArgumentException.class)
    public void testSyncWithNullUrl() {
        Page json = issueCrawler.retrieveJsonContentFromUrl(null);

        Assert.assertNull("Invalid port should be null", json);
    }

    
    @Test(expected = IllegalArgumentException.class)
    public void testSyncWithInvalidPort() {
        Page sync = issueCrawler.retrieveJsonContentFromUrl("http://localhost:123/");

        Assert.assertNull("Invalid port should be null", sync);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSyncWithInvalidCharacter() {
        Page sync = issueCrawler.retrieveJsonContentFromUrl("http://localhost:9000/s");

        Assert.assertNull("Should be 404 response! Should return null", sync);
    }

    @Test
    public void testSyncWithValidUrl() {
        Page sync = issueCrawler.retrieveJsonContentFromUrl("http://localhost:9000/api/issues/search?pageIndex=1");

        Assert.assertNotNull("Sync should be successful", sync);
    }

}
