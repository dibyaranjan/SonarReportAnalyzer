package com.dibya.sonar.configuration.reader;

import org.junit.Before;
import org.junit.Test;

public class PropertiesReaderTest {
	private PropertiesReader reader;
	@Before
	public void setUp() {
		reader = new PropertiesReader();
	}
	
	@Test
	public void test() {
		reader.readPropertiesFile();
	}
}
