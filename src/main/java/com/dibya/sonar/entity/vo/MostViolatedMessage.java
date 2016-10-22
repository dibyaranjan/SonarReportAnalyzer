package com.dibya.sonar.entity.vo;

public class MostViolatedMessage {
    private String message;
    private Long count;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MostViolatedMessage [message=" + message + ", count=" + count + "]";
    }
}
