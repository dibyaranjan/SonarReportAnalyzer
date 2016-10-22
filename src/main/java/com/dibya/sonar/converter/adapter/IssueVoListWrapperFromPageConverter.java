package com.dibya.sonar.converter.adapter;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dibya.sonar.converter.AbstractConverter;
import com.dibya.sonar.entity.vo.Issue;
import com.dibya.sonar.entity.vo.IssueVo;
import com.dibya.sonar.entity.vo.Page;
import com.dibya.sonar.entity.vo.Project;
import com.dibya.sonar.entity.vo.SonarRuleVo;
import com.dibya.sonar.entity.vo.wrapper.IssueVoListWrapper;

public class IssueVoListWrapperFromPageConverter extends AbstractConverter {
    private static final Logger LOGGER = Logger.getLogger(IssueVoListWrapperFromPageConverter.class);
    
    public IssueVoListWrapperFromPageConverter() {
        LOGGER.info("Registered " + getClass());
    }
    
    @SuppressWarnings("unchecked")
    @Override
    protected <T, S> T doConvert(S sourceObject) {
        Page source = (Page) sourceObject;
        Page head = source;


        Map<String, SonarRuleVo> ruleTable = new LinkedHashMap<>();
        List<IssueVo> issueVoList = new LinkedList<>();

        while (head != null) {
            List<SonarRuleVo> rules = head.getRules();
            for (SonarRuleVo sonarRule : rules) {
                String key = sonarRule.getKey();
                if (ruleTable.get(key) == null) {
                    ruleTable.put(key, sonarRule);
                }
            }

            List<Issue> sonarIssues = head.getSonarIssues();
            for (Issue issue : sonarIssues) {
                IssueVo issueVo = new IssueVo();
                convertIssue(issueVo, issue);
                
                Project project = new Project();
                issueVo.setProject(project);
                
                issueVo.setRule(ruleTable.get(issue.getRuleName()));
                
                issueVoList.add(issueVo);
            }

            head = head.getNextPage();
        }

        IssueVoListWrapper target = new IssueVoListWrapper();
        target.setIssueVoList(issueVoList);
        return (T) target;
    }

    private void convertIssue(IssueVo target, Issue issue) {
        target.setComponent(issue.getComponent());
        target.setKey(issue.getKey());
        target.setLine(issue.getLine());
        target.setMessage(issue.getMessage());
        target.setSeverity(issue.getSeverity());
        target.setStatus(issue.getStatus());
    }

}
