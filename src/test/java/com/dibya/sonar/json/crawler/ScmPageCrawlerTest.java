package com.dibya.sonar.json.crawler;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import com.dibya.sonar.entity.vo.ScmDetails;

public class ScmPageCrawlerTest {

	private AbstractJsonCrawler<ScmDetails> crawler;
	private UrlValidator urlValidator;
	private RestTemplate restTemplate;

	private Mockery mockery;

	@Before
	public void setUp() {
		crawler = new ScmPageCrawler();

		mockery = new JUnit4Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
				setThreadingPolicy(new Synchroniser());
			}
		};
		urlValidator = mockery.mock(UrlValidator.class);
		restTemplate = mockery.mock(RestTemplate.class);
	}

	@After
	public void tearDown() {
		crawler = null;
	}

	@Test(expected = IllegalStateException.class)
	public void testWithoutUrlValidator() {
		crawler.retrieveJsonContentFromUrl("VALID-URL");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithEmptyUrl() {
		crawler.retrieveJsonContentFromUrl("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testWithNonAccessibleUrl() {
		crawler.setUrlValidator(urlValidator);
		mockery.checking(new Expectations() {
			{
				oneOf(urlValidator).isUrlAccessible("VALID-URL");
				will(returnValue(false));
			}
		});

		crawler.retrieveJsonContentFromUrl("VALID-URL");
	}

	@Test
	public void testWithValidUrl() {
		crawler.setUrlValidator(urlValidator);
		mockery.checking(new Expectations() {
			{
				oneOf(urlValidator).isUrlAccessible("VALID-URL");
				will(returnValue(true));

				oneOf(restTemplate).getForObject("VALID-URL", ScmDetails.class);
				will(returnValue(null));
			}
		});

		crawler.setRestTemplate(restTemplate);
		ScmDetails result = crawler.retrieveJsonContentFromUrl("VALID-URL");
		Assert.assertNull("Page should be null", result);
	}

}
