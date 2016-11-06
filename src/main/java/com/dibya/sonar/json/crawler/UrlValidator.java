package com.dibya.sonar.json.crawler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * A class for validating if the URL is accessible. This class tries to make an
 * HTTP connection, if the connection is successful gives 200 status code.
 * 
 * @author Dibya
 */
@Component
public class UrlValidator {
	private static final Logger LOGGER = Logger.getLogger(UrlValidator.class);

	/**
	 * Checks the URL if it is accessible using establishing HttpConnection to
	 * the URL passed.
	 * 
	 * @param hostUrl
	 *            The URL which is to be connected
	 * @return true is the URL can be connected, false otherwise
	 */
	public boolean isUrlAccessible(String hostUrl) {
		try {
			URL url = new URL(hostUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			int responseCode = connection.getResponseCode();
			if (responseCode != 200) {
				return false;
			}
		} catch (MalformedURLException e) {
			LOGGER.error("URL is incorrect");
			LOGGER.error(e);
			return false;
		} catch (IOException e) {
			LOGGER.error("Host is not accessible");
			LOGGER.error(e);
			return false;
		}

		return true;
	}
}
