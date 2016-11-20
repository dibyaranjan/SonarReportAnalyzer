package com.dibya.sonar.converter.adapter;

import org.apache.log4j.Logger;

import com.dibya.infra.converter.AbstractConverter;
import com.dibya.infra.converter.annotation.Convert;
import com.dibya.sonar.entity.Issue;
import com.dibya.sonar.entity.SeverityType;
import com.dibya.sonar.entity.StatusType;

/**
 * Converts an vo.Issue to normalized Issue which can be saved to the database
 * 
 * @author Dibya Ranjan
 */
@Convert(source = com.dibya.sonar.entity.vo.Issue.class, target = Issue.class)
public class IssueEntityFromIssueConverter extends AbstractConverter {
	private static final Logger LOGGER = Logger.getLogger(IssueEntityFromIssueConverter.class);

	public IssueEntityFromIssueConverter() {
		LOGGER.info("Registered " + getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T, S> T doConvert(S sourceObject) {
		com.dibya.sonar.entity.vo.Issue source = (com.dibya.sonar.entity.vo.Issue) sourceObject;
		SeverityType severity = SeverityType.getSeverity(source.getSeverity());
		StatusType status = StatusType.getStatus(source.getStatus());

		Issue target = new Issue();
		target.setKey(source.getKey());
		target.setMessage(source.getMessage());
		target.setProject(source.getProject());
		target.setSeverity(severity);
		target.setStatus(status);
		target.setLine(source.getLine());

		return (T) target;
	}
}
