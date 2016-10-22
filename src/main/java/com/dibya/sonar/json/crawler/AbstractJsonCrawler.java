package com.dibya.sonar.json.crawler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * 
 * 
 * @author Dibya Ranjan
 */
public abstract class AbstractJsonCrawler<T> implements JsonCrawler<T> {
    private static final Logger LOGGER = Logger.getLogger(AbstractJsonCrawler.class);

    protected final boolean isUrlAccessible(String hostUrl) {
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

    public final T retrieveJsonContentFromUrl(String url) {
        if (StringUtils.isBlank(url)) {
            LOGGER.error("URL can not be null");
            throw new IllegalArgumentException("URL can not be null");
        }

        if (!isUrlAccessible(url)) {
            LOGGER.error("URL is not accessible");
            throw new IllegalArgumentException("URL is not accessible");
        }
        
        return retriveJsonFromUrl(url);
    }

    protected abstract T retriveJsonFromUrl(String url);
}
