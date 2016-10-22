package com.dibya.sonar.entity.builder;

import com.dibya.sonar.entity.vo.Issue;

public class IssueVoBuilder {
    private int buildCount;

    public Issue build() {
        buildCount++;
        Issue issue = new Issue();
        issue.setComponent("AuditTool:AuditTool:src/main/java/com/sita/audit/tool/aop/LoginValidator.java");
        issue.setCreationDate("2016-05-12T06:02:48+0530");
        issue.setKey("0f4616c8-7a99-4d2d-80fd-3f45f8bb027" + buildCount);
        issue.setMessage("Replace all tab characters in this file by sequences of white-spaces.");
        issue.setSeverity("INFO");
        issue.setStatus("OPEN");
        issue.setUpdateDate("2016-02-12T06:02:48+0530");

        return issue;
    }
}
