package com.dibya.sonar.entity.builder;

import java.util.ArrayList;
import java.util.List;

import com.dibya.sonar.entity.vo.Issue;
import com.dibya.sonar.entity.vo.Page;
import com.dibya.sonar.entity.vo.PageMetaData;

public class PageBuilder {
    private IssueVoBuilder issueBuilder = new IssueVoBuilder();
    private PageMetaDataBuilder pageMetaDataBuilder = new PageMetaDataBuilder();

    public Page build() {
        Page page1 = new Page();

        List<Issue> issues1 = new ArrayList<>();
        issues1.add(issueBuilder.build());
        issues1.add(issueBuilder.build());
        issues1.add(issueBuilder.build());
        page1.setSonarIssues(issues1);
        PageMetaData pageMetaData1 = pageMetaDataBuilder.build();
        page1.setPageMetaData(pageMetaData1);

        Page page2 = new Page();
        List<Issue> issues2 = new ArrayList<>();
        issues2.add(issueBuilder.build());
        issues2.add(issueBuilder.build());
        issues2.add(issueBuilder.build());
        page2.setSonarIssues(issues2);
        PageMetaData pageMetaData2 = pageMetaDataBuilder.build();
        page2.setPageMetaData(pageMetaData2);

        Page page3 = new Page();
        List<Issue> issues3 = new ArrayList<>();
        issues3.add(issueBuilder.build());
        issues3.add(issueBuilder.build());
        issues3.add(issueBuilder.build());
        page3.setSonarIssues(issues3);
        PageMetaData pageMetaData3 = pageMetaDataBuilder.build();
        page3.setPageMetaData(pageMetaData3);

        page2.setNextPage(page3);
        page1.setNextPage(page2);
        return page1;
    }
}
