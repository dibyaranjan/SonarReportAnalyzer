package com.dibya.sonar.converter.adapter;

import org.apache.log4j.Logger;

import com.dibya.sonar.converter.AbstractConverter;
import com.dibya.sonar.entity.Issue;
import com.dibya.sonar.entity.SeverityType;
import com.dibya.sonar.entity.SonarRule;
import com.dibya.sonar.entity.StatusType;
import com.dibya.sonar.entity.vo.Issues;
import com.dibya.sonar.entity.vo.SonarRuleVo;

public class IssueEntityFromIssueVoConverter extends AbstractConverter {
    private static final Logger LOGGER = Logger.getLogger(IssueEntityFromIssueVoConverter.class);
    
    public IssueEntityFromIssueVoConverter() {
        LOGGER.info("Registered " + getClass());
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T, S> T doConvert(S sourceObject) {
        Issues source = (Issues) sourceObject;
        
        Issue target = new Issue();
        SeverityType severity = SeverityType.getSeverity(source.getSeverity());
        StatusType status = StatusType.getStatus(source.getStatus());

        target.setKey(source.getKey());
        target.setMessage(source.getMessage());
        target.setProject(source.getProject().getName());
        target.setSeverity(severity);
        target.setStatus(status);
        target.setLine(source.getLine());
        SonarRuleVo rule = source.getRule();
        SonarRule sonarRule = converter.convert(new SonarRule(), rule);
        target.setRule(sonarRule);

        return (T) target;
    }
}
