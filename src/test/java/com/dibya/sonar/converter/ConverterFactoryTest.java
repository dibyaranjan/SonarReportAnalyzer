package com.dibya.sonar.converter;

import java.util.HashMap;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dibya.sonar.converter.adapter.IssueEntityFromIssueConverter;
import com.dibya.sonar.entity.vo.Issue;
import com.dibya.sonar.entity.vo.SourceTargetValue;

public class ConverterFactoryTest {
    private ConverterFactory factory;
    private HashMap<SourceTargetValue, Converter> converterRegistry;

    @Before
    public void setUp() {
    	SourceTargetValue issueFromIssueVo = new SourceTargetValue(Issue.class, com.dibya.sonar.entity.Issue.class);
		Converter value = new IssueEntityFromIssueConverter();
		
		converterRegistry = new HashMap<SourceTargetValue, Converter>();
		converterRegistry.put(issueFromIssueVo, value);
    	
        factory = new ConverterFactory();
		factory.setConverterRegistry(converterRegistry);
    }
    
    @After
    public void tearDown() {
    	converterRegistry = null;
    	factory = null;
    }

    @Test
    public void testGetConverterWithValidStv() {
        SourceTargetValue stv = new SourceTargetValue(Issue.class, com.dibya.sonar.entity.Issue.class);
        AbstractConverter converter = (AbstractConverter) factory.getConverter(stv);

        if (!(converter instanceof IssueEntityFromIssueConverter)) {
            Assert.fail("Should be IssueConverter");
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetConverterWithInvalidStv() {
        SourceTargetValue stv = new SourceTargetValue(Object.class, com.dibya.sonar.entity.Issue.class);
        factory.getConverter(stv);
    }
}