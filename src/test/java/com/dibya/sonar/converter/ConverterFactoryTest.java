package com.dibya.sonar.converter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dibya.sonar.converter.adapter.IssueEntityFromIssueConverter;
import com.dibya.sonar.entity.vo.Issue;
import com.dibya.sonar.entity.vo.SourceTargetValue;

public class ConverterFactoryTest {
    private ConverterFactory factory;

    @Before
    public void setUp() {
        factory = new ConverterFactory();
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