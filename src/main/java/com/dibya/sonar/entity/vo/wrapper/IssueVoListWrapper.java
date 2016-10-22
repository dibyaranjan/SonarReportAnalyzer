package com.dibya.sonar.entity.vo.wrapper;

import java.util.List;

import com.dibya.sonar.entity.vo.IssueVo;

public class IssueVoListWrapper {
    private List<IssueVo> issueVoList;

    public List<IssueVo> getIssueVoList() {
        return issueVoList;
    }

    public void setIssueVoList(List<IssueVo> issueVoList) {
        this.issueVoList = issueVoList;
    }
}
