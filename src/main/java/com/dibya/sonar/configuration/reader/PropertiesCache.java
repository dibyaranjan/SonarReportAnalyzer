package com.dibya.sonar.configuration.reader;

import java.util.LinkedHashMap;
import java.util.Map;

public class PropertiesCache {
	private static final String EMPTY_STRING = "";
	public static Map<String, String> map = new LinkedHashMap<String, String>();
	
	static {
		new PropertiesReader();
	}

	public static void register(PropertyKey propertyKey, String value) {
		if (value == null) {
			value = EMPTY_STRING;
		}
		map.put(propertyKey.getKey(), value);
	}

	public static String getValue(PropertyKey propertyKey) {
		String value = map.get(propertyKey.getKey());

		if (value == null) {
			throw new NullPointerException("Corrupt cache");
		}

		return value;
	}
}
