package com.dibya.sonar.service;

import java.util.List;

import com.dibya.sonar.entity.vo.ViolationDetails;

public interface ViolationService {
    public List<ViolationDetails> getAllViolations();
    public List<ViolationDetails> getViolationsWithStatus(String orderBy);
    public ViolationDetails getIssueByKey(String key);
}
