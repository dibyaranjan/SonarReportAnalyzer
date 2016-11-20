package com.dibya.sonar.converter.adapter;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dibya.infra.converter.AbstractConverter;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.vo.Resource;

public class ResourceFromSourceFileConverterTest {
	private AbstractConverter converter;
	private Resource target;
	private SourceFile source;

	@Before
	public void setUp() {
		converter = new ResourceFromSourceFileConverter();
		source = new SourceFile();
		source.setId(10);
		source.setName("com.dibya.sonar.TestFile.java");
	}

	@After
	public void tearDown() {
		converter = null;
		target = null;
		source = null;
	}

	@Test
	public void testConverter() {
		target = converter.convert(new Resource(), source);

		Assert.assertNotNull("Target should not be null", target);
		Assert.assertEquals("Id should match", 10, target.getResourceId());
		Assert.assertEquals("File name should be unchanged", "com.dibya.sonar.TestFile.java", target.getResourceName());
	}
}
