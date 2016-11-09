package com.dibya.sonar.configuration.reader;

import static com.dibya.sonar.configuration.reader.PropertyKey.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesReader {
	private static final Logger LOGGER = Logger.getLogger(PropertiesReader.class);

	public PropertiesReader() {
		readPropertiesFile();
	}

	public void readPropertiesFile() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("config.properties");
		if (inputStream == null) {
			throw new IllegalStateException("Config Properties file not found");
		}
		load(inputStream);
	}

	private void load(InputStream inputStream) {
		try {
			Properties properties = new Properties();
			properties.load(inputStream);
			PropertiesCache.register(JDBC_DRIVER, (String) properties.get(JDBC_DRIVER.getKey()));
			PropertiesCache.register(JDBC_URL, (String) properties.get(JDBC_URL.getKey()));
			PropertiesCache.register(USER_NAME, (String) properties.get(USER_NAME.getKey()));
			PropertiesCache.register(PASSWORD, (String) properties.get(PASSWORD.getKey()));
			PropertiesCache.register(DIALECT, (String) properties.getProperty(DIALECT.getKey()));
			PropertiesCache.register(HBM_TO_DDL, (String) properties.getProperty(HBM_TO_DDL.getKey()));
			PropertiesCache.register(SHOW_SQL, (String) properties.getProperty(SHOW_SQL.getKey()));
			PropertiesCache.register(FORMAT_SQL, (String) properties.getProperty(FORMAT_SQL.getKey()));
		} catch (IOException e) {
			LOGGER.error(e);
		}
	}
}
