package com.dibya.sonar.configuration;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dibya.sonar.converter.Converter;
import com.dibya.sonar.converter.adapter.IssueEntityFromIssueVoConverter;
import com.dibya.sonar.converter.adapter.IssuesFromIssueVoConverter;
import com.dibya.sonar.converter.adapter.IssuesFromPageConverter;
import com.dibya.sonar.converter.adapter.ResourceFromSourceFileConverter;
import com.dibya.sonar.converter.adapter.ScmDetailListWrapperFromScmDetailsConverter;
import com.dibya.sonar.converter.adapter.SonarRuleFromSonarRuleVoConverter;
import com.dibya.sonar.converter.adapter.SourceFileListWrapperFromIssuesConverter;
import com.dibya.sonar.converter.adapter.ViolationDetailsFromSourceFileConverter;
import com.dibya.sonar.entity.vo.SourceTargetValue;

@Configuration
public class ConverterBeans {
    
    @Bean(name="converterRegistry")
    public HashMap<SourceTargetValue, Converter> getConverterRegistry() {
        HashMap<SourceTargetValue, Converter> converterRegistry = new HashMap<>();
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.Issues.class, com.dibya.sonar.entity.vo.wrapper.SourceFileListWrapper.class), new SourceFileListWrapperFromIssuesConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.Issues.class, com.dibya.sonar.entity.Issue.class), new IssueEntityFromIssueVoConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.ScmDetails.class, com.dibya.sonar.entity.vo.wrapper.ScmDetailListWrapper.class), new ScmDetailListWrapperFromScmDetailsConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.SourceFile.class, com.dibya.sonar.entity.vo.ViolationDetails.class), new ViolationDetailsFromSourceFileConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.Issues.class, com.dibya.sonar.entity.vo.Issue.class), new IssueEntityFromIssueVoConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.Page.class, com.dibya.sonar.entity.vo.Issues.class), new IssuesFromPageConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.SonarRuleVo.class, com.dibya.sonar.entity.SonarRule.class), new SonarRuleFromSonarRuleVoConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.SourceFile.class, com.dibya.sonar.entity.vo.Resource.class), new ResourceFromSourceFileConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.Issue.class, com.dibya.sonar.entity.vo.Issues.class), new IssuesFromIssueVoConverter());
        return converterRegistry;
    }
}
