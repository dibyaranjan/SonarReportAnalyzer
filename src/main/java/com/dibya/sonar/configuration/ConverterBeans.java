package com.dibya.sonar.configuration;

import java.util.HashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dibya.sonar.converter.Converter;
import com.dibya.sonar.converter.adapter.BlameDetailFromSourceFileConverter;
import com.dibya.sonar.converter.adapter.BlameDetailListWrapperFromSourceFileConverter;
import com.dibya.sonar.converter.adapter.IssueEntityFromIssueVoConverter;
import com.dibya.sonar.converter.adapter.IssueVoListWrapperFromPageConverter;
import com.dibya.sonar.converter.adapter.ScmDetailListWrapperFromScmDetailsConverter;
import com.dibya.sonar.converter.adapter.SonarRuleFromSonarRuleVoConverter;
import com.dibya.sonar.converter.adapter.SourceFileListWrapperFromIssueListWrapperConverter;
import com.dibya.sonar.entity.vo.SourceTargetValue;

@Configuration
public class ConverterBeans {
    
    @Bean
    public HashMap<SourceTargetValue, Converter> getConverterRegistry() {
        HashMap<SourceTargetValue, Converter> converterRegistry = new HashMap<>();
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.wrapper.IssueVoListWrapper.class, com.dibya.sonar.entity.vo.wrapper.SourceFileListWrapper.class), new SourceFileListWrapperFromIssueListWrapperConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.IssueVo.class, com.dibya.sonar.entity.Issue.class), new IssueEntityFromIssueVoConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.ScmDetails.class, com.dibya.sonar.entity.vo.wrapper.ScmDetailListWrapper.class), new ScmDetailListWrapperFromScmDetailsConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.SourceFile.class, com.dibya.sonar.entity.vo.wrapper.BlameDetailListWrapper.class), new BlameDetailListWrapperFromSourceFileConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.IssueVo.class, com.dibya.sonar.entity.vo.Issue.class), new IssueEntityFromIssueVoConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.Page.class, com.dibya.sonar.entity.vo.wrapper.IssueVoListWrapper.class), new IssueVoListWrapperFromPageConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.vo.SonarRuleVo.class, com.dibya.sonar.entity.SonarRule.class), new SonarRuleFromSonarRuleVoConverter());
        converterRegistry.put(new SourceTargetValue(com.dibya.sonar.entity.SourceFile.class, com.dibya.sonar.entity.vo.ViolationDetail.class), new BlameDetailFromSourceFileConverter());
        return converterRegistry;
    }
}
