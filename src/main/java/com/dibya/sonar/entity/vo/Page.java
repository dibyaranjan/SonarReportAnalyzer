package com.dibya.sonar.entity.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {
    @JsonProperty(value = "paging")
    private PageMetaData pageMetaData;
    @JsonProperty(value = "issues")
    private List<Issue> sonarIssues;

    @JsonProperty(value = "rules")
    private List<SonarRuleVo> rules;

    private Page nextPage;

    public PageMetaData getPageMetaData() {
        return pageMetaData;
    }

    public void setPageMetaData(PageMetaData pageMetaData) {
        this.pageMetaData = pageMetaData;
    }

    public List<Issue> getSonarIssues() {
        return sonarIssues;
    }

    public void setSonarIssues(List<Issue> sonarIssues) {
        this.sonarIssues = sonarIssues;
    }

    public Page getNextPage() {
        return nextPage;
    }

    public void setNextPage(Page nextPage) {
        this.nextPage = nextPage;
    }

    public List<SonarRuleVo> getRules() {
        return rules;
    }

    public void setRules(List<SonarRuleVo> rules) {
        this.rules = rules;
    }
}
