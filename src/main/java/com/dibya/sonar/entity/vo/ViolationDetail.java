package com.dibya.sonar.entity.vo;

import com.dibya.sonar.entity.SeverityType;
import com.dibya.sonar.entity.SonarRule;
import com.dibya.sonar.entity.StatusType;

public class ViolationDetail {
    private Resource resource;
    private int lineNumber;
    private String dateIntroduced;
    private StatusType status;
    private SeverityType severity;
    private String author;
    private String message;
    private SonarRule rule;

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getDateIntroduced() {
        return dateIntroduced;
    }

    public void setDateIntroduced(String dateIntroduced) {
        this.dateIntroduced = dateIntroduced;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public SeverityType getSeverity() {
        return severity;
    }

    public void setSeverity(SeverityType severity) {
        this.severity = severity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public SonarRule getRule() {
        return rule;
    }

    public void setRule(SonarRule sonarRule) {
        this.rule = sonarRule;
    }

    @Override
    public String toString() {
        return "BlameDetail [resource=" + resource + ", lineNumber=" + lineNumber + ", dateIntroduced=" + dateIntroduced
                + ", status=" + status + ", severity=" + severity + ", author=" + author + ", message=" + message
                + ", sonarRule=" + rule + "]";
    }
}
