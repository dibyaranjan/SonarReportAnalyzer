package com.dibya.sonar.service;

import java.util.List;
import java.util.Set;

import com.dibya.sonar.entity.vo.ViolationDetails;
import com.dibya.sonar.entity.vo.GenericCountHolder;
import com.dibya.sonar.entity.vo.Resource;

public interface SeverityService {
    List<GenericCountHolder> getSeverityCounts(String status);

    List<ViolationDetails> getAllIssueForSeverity(String severity);

    Set<Resource> getResourcesForSeverity(String severity);
}
