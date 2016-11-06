package com.dibya.sonar.entity.vo;

public class Issues {
	private String key;
	private String component;
	private String status;
	private String severity;
	private String message;
	private int line;
	private SonarRuleVo rule;
	private Project project;

	private Issues issueVo;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public SonarRuleVo getRule() {
		return rule;
	}

	public void setRule(SonarRuleVo rule) {
		this.rule = rule;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Issues getIssueVo() {
		return issueVo;
	}

	public void setIssueVo(Issues issueVo) {
		this.issueVo = issueVo;
	}
}
