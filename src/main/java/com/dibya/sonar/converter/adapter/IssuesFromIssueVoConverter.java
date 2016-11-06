package com.dibya.sonar.converter.adapter;

import com.dibya.sonar.converter.AbstractConverter;
import com.dibya.sonar.entity.vo.Issue;
import com.dibya.sonar.entity.vo.Issues;

public class IssuesFromIssueVoConverter extends AbstractConverter {

	@SuppressWarnings("unchecked")
	@Override
	protected <T, S> T doConvert(S sourceObject) {
		Issue issue = (Issue) sourceObject;
		
		Issues target = new Issues();
		target.setComponent(issue.getComponent());
        target.setKey(issue.getKey());
        target.setLine(issue.getLine());
        target.setMessage(issue.getMessage());
        target.setSeverity(issue.getSeverity());
        target.setStatus(issue.getStatus());
		return (T) target;
	}

}
