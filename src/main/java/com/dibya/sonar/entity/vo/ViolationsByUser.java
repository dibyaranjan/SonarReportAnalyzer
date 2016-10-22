package com.dibya.sonar.entity.vo;

public class ViolationsByUser {
    private String email;
    private int countOfViolations;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCountOfViolations() {
        return countOfViolations;
    }

    public void setCountOfViolations(int countOfViolations) {
        this.countOfViolations = countOfViolations;
    }

}
