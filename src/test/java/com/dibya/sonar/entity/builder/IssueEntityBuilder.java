package com.dibya.sonar.entity.builder;

import com.dibya.sonar.entity.Issue;
import com.dibya.sonar.entity.SeverityType;
import com.dibya.sonar.entity.StatusType;

public class IssueEntityBuilder {
    private int buildCount;
    private int lineCount;

    public Issue build() {
        buildCount++;
        lineCount = lineCount + 3;

        Issue issue = new Issue();
        issue.setKey("0f4616c8-7a99-4d2d-80fd-3f45f8bb027" + buildCount);
        issue.setMessage("Replace all tab characters in this file by sequences of white-spaces.");
        issue.setSeverity(SeverityType.INFO);
        issue.setStatus(StatusType.OPEN);
        issue.setLine(lineCount);

        return issue;
    }

}
