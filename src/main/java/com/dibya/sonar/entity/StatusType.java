package com.dibya.sonar.entity;

import org.apache.commons.lang.StringUtils;

public enum StatusType {
    OPEN("OPEN"), CLOSED("CLOSED");

    private String value;

    StatusType(String value) {
        this.value = value;
    }

    public String getStatus() {
        return value;
    }

    public static StatusType getStatus(String status) {
        StatusType selectedStatusType;
        if (StringUtils.equalsIgnoreCase(OPEN.getStatus(), status)) {
            selectedStatusType = OPEN;
        } else if (StringUtils.equalsIgnoreCase(CLOSED.getStatus(), status)) {
            selectedStatusType = CLOSED;
        } else {
            throw new IllegalArgumentException("Wrong status type passed");
        }

        return selectedStatusType;
    }

}
