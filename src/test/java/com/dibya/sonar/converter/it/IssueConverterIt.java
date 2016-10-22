package com.dibya.sonar.converter.it;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dibya.sonar.converter.BaseConverter;
import com.dibya.sonar.converter.Converter;
import com.dibya.sonar.converter.ConverterFactory;
import com.dibya.sonar.entity.Issue;
import com.dibya.sonar.entity.builder.IssueEntityBuilder;
import com.dibya.sonar.entity.builder.IssueVoBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { BaseConverter.class, ConverterFactory.class })
public class IssueConverterIt {
    @Autowired
    private Converter converter;

    private IssueVoBuilder issueVoBuilder;
    private com.dibya.sonar.entity.vo.Issue issueVo;

    private IssueEntityBuilder issueEntityBuilder;
    private Issue issue;

    @Before
    public void setUp() {
        issueEntityBuilder = new IssueEntityBuilder();
        issue = issueEntityBuilder.build();

        issueVoBuilder = new IssueVoBuilder();
        issueVo = issueVoBuilder.build();
    }

    @Test
    public void test() {
        issue = converter.convert(issue, issueVo);
    }
}
