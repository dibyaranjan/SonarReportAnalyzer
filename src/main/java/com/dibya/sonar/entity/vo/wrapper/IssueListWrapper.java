package com.dibya.sonar.entity.vo.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.dibya.sonar.entity.vo.Issue;

/**
 * A wrapper class to hold list of issues.
 * 
 * There is a limitation of the converter framework. It doesn't support list
 * conversion directly refer to javadoc of the Converters for more details.
 * 
 * This wrapper provides an work around for object conversion.
 *
 * @author Dibya Ranjan
 */
public class IssueListWrapper {
    private List<Issue> issues = new ArrayList<>();

    public List<Issue> getIssues() {
        return issues;
    }

    public void setIssues(List<Issue> issues) {
        this.issues = issues;
    }

    @Override
    public String toString() {
        return "ConvertedIssuesWrapper [issues=" + issues + "]";
    }

}
