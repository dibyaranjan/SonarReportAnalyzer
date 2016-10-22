package com.dibya.sonar.json.crawler;

public interface JsonCrawler<T> {
    T retrieveJsonContentFromUrl(String url);
}
