package com.dibya.sonar.json.crawler;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { UrlValidator.class })
public class UrlValidatorIt {
	@Autowired
	private UrlValidator validator;
	
	@Test
	public void testValidatorWithInvalidHost() {
		boolean result = validator.isUrlAccessible("dibya-ranjan");
		Assert.assertFalse("Should be false", result);
	}
	
	@Test
	public void testValidatorWithInvalidUrl() {
		boolean result = validator.isUrlAccessible("localhost:9000/s");
		Assert.assertFalse("Should be false", result);
	}
	
	@Test
	@Ignore
	public void testValidatorWithValidUrl() {
		boolean result = validator.isUrlAccessible("https://en.wikipedia.org");
		Assert.assertTrue("Should be true", result);
	}
}
