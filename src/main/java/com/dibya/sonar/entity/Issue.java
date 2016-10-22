package com.dibya.sonar.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;

@Entity
@Table(name = "SONAR_ISSUES")
public class Issue implements Comparable<Issue> {
    @Id
    @Column(name = "ISSUE_KEY")
    private String key;
    private StatusType status;
    private SeverityType severity;
    private String message;
    private int line;
    private String project;
    @OneToOne(cascade = CascadeType.ALL)
    private SonarRule rule;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public SonarRule getRule() {
        return rule;
    }

    public void setRule(SonarRule rule) {
        this.rule = rule;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((key == null) ? 0 : key.hashCode());
        result = prime * result + ((message == null) ? 0 : message.hashCode());
        result = prime * result + ((severity == null) ? 0 : severity.hashCode());
        result = prime * result + ((status == null) ? 0 : status.hashCode());
        result = prime * result + ((project == null) ? 0 : project.hashCode());
        result = prime * result + line;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        Issue other = (Issue) obj;

        if (!StringUtils.equals(project, other.project)) {
            return false;
        }

        if (!StringUtils.equals(key, other.key)) {
            return false;
        }

        if (!StringUtils.equals(message, other.message)) {
            return false;
        }

        if (severity != other.severity) {
            return false;
        }

        if (status != other.status) {
            return false;
        }

        if (line != other.line) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        return "Issue [key=" + key + ", status=" + status + ", severity=" + severity + ", message=" + message
                + ", project=" + project + "]";
    }

    @Override
    public int compareTo(Issue o) {
        return this.key.compareTo(o.key);
    }

}
