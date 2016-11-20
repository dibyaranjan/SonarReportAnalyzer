package com.dibya.sonar.converter.adapter;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dibya.infra.converter.Converter;
import com.dibya.sonar.entity.builder.IssueVoBuilder;
import com.dibya.sonar.entity.vo.Issue;

public class IssueEntityFromIssueVoConverterTest {
    private Converter converter;
    private IssueVoBuilder issueVoBuilder;
    private Issue issueVo;
    
    @Before
    public void setUp() {
        converter = new IssueEntityFromIssueConverter();
        issueVoBuilder = new IssueVoBuilder();
        issueVo = issueVoBuilder.build();
    }
    
    @After
    public void tearDown() {
        converter = null;
    }
    
    @Test
    public void testConvert() {
        com.dibya.sonar.entity.Issue convert = converter.convert(new com.dibya.sonar.entity.Issue(), issueVo);
        System.out.println(convert);
    }
}
