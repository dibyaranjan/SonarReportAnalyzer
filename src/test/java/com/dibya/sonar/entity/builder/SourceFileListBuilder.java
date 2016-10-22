package com.dibya.sonar.entity.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dibya.sonar.entity.Issue;
import com.dibya.sonar.entity.SourceFile;

public class SourceFileListBuilder {
    public List<SourceFile> build() {
        SourceFile sourceFile1 = new SourceFile();
        sourceFile1.setId(1);
        sourceFile1.setName("AuditTool:AuditTool:src/main/java/com/sita/audit/tool/aop/LoginValidator.java");
        
        IssueEntityBuilder issueEntityBuilder = new IssueEntityBuilder();
        
        List<Issue> issues1 = new ArrayList<>();
        issues1.add(issueEntityBuilder.build());
        issues1.add(issueEntityBuilder.build());
        issues1.add(issueEntityBuilder.build());
        issues1.add(issueEntityBuilder.build());
        ScmDetailListBuilder scmDetailListBuilder = new ScmDetailListBuilder();

        sourceFile1.setIssues(issues1);
        sourceFile1.setScmDetails(scmDetailListBuilder.build());
        
        SourceFile sourceFile2 = new SourceFile();
        sourceFile2.setId(2);
        sourceFile2.setName("AuditTool:AuditTool:src/main/java/com/sita/audit/file2.java");
        
        List<Issue> issues2 = new ArrayList<>();
        issues2.add(issueEntityBuilder.build());
        issues2.add(issueEntityBuilder.build());
        issues2.add(issueEntityBuilder.build());
        issues2.add(issueEntityBuilder.build());
        
        sourceFile2.setIssues(issues2);
        sourceFile2.setScmDetails(scmDetailListBuilder.build());
        
        return Arrays.asList(sourceFile1, sourceFile2);
    }
}
