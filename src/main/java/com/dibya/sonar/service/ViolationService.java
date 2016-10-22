package com.dibya.sonar.service;

import java.util.List;

import com.dibya.sonar.entity.vo.ViolationDetail;

public interface ViolationService {
    public List<ViolationDetail> getAllViolations();
    public List<ViolationDetail> getViolationsWithStatus(String orderBy);
    public ViolationDetail getIssueByKey(String key);
}
