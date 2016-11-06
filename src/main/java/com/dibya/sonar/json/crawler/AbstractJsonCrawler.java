package com.dibya.sonar.json.crawler;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * 
 * @author Dibya Ranjan
 */
public abstract class AbstractJsonCrawler<T> implements JsonCrawler<T> {
	private static final Logger LOGGER = Logger.getLogger(AbstractJsonCrawler.class);

	@Autowired
	protected RestTemplate restTemplate;
	
	@Autowired
	private UrlValidator urlValidator;

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public void setUrlValidator(UrlValidator urlValidator) {
		this.urlValidator = urlValidator;
	}

	public final T retrieveJsonContentFromUrl(String url) {
		if (StringUtils.isBlank(url)) {
			LOGGER.error("URL can not be null");
			throw new IllegalArgumentException("URL can not be null");
		}
		
		if (urlValidator == null) {
			LOGGER.error("No UrlValidator found");
			throw new IllegalStateException("No UrlValidator found"); 
		}

		if (!urlValidator.isUrlAccessible(url)) {
			LOGGER.error("URL is not accessible");
			throw new IllegalArgumentException("URL is not accessible");
		}

		return retriveJsonFromUrl(url);
	}

	protected abstract T retriveJsonFromUrl(String url);
}
