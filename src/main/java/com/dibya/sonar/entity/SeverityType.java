package com.dibya.sonar.entity;

import org.apache.commons.lang.StringUtils;

public enum SeverityType {
    INFO("INFO"), MINOR("MINOR"), MAJOR("MAJOR"), CRITICAL("CRITICAL"), BLOCKER("BLOCKER");

    private String severity;

    private SeverityType(String severity) {
        this.severity = severity;
    }

    public String getSeverity() {
        return severity;
    }
    
    public static SeverityType getSeverity(String severity) {
        SeverityType selectedSeverityType;
        if (StringUtils.equalsIgnoreCase(BLOCKER.getSeverity(), severity)) {
            selectedSeverityType = BLOCKER;
        } else if (StringUtils.equalsIgnoreCase(CRITICAL.getSeverity(), severity)) {
            selectedSeverityType = CRITICAL;
        } else if (StringUtils.equalsIgnoreCase(MAJOR.getSeverity(), severity)) {
            selectedSeverityType = MAJOR;
        } else if (StringUtils.equalsIgnoreCase(MINOR.getSeverity(), severity)) {
            selectedSeverityType = MINOR;
        } else if (StringUtils.equalsIgnoreCase(INFO.getSeverity(), severity)) {
            selectedSeverityType = INFO;
        } else {
            throw new IllegalArgumentException("Wrong severity type passed");
        }

        return selectedSeverityType;
    }

}
