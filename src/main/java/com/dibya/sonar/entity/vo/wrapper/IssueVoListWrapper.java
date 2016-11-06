package com.dibya.sonar.entity.vo.wrapper;

import java.util.List;

import com.dibya.sonar.entity.vo.Issues;

@Deprecated
public class IssueVoListWrapper {
    private List<Issues> issueVoList;

    public List<Issues> getIssueVoList() {
        return issueVoList;
    }

    public void setIssueVoList(List<Issues> issueVoList) {
        this.issueVoList = issueVoList;
    }
}
