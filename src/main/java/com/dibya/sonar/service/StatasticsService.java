package com.dibya.sonar.service;

import java.util.List;

import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.vo.ViolationDetails;
import com.dibya.sonar.entity.vo.GenericCountHolder;
import com.dibya.sonar.entity.vo.MostViolatedMessage;
import com.dibya.sonar.entity.vo.MostViolatedResource;
import com.dibya.sonar.entity.vo.ViolationsByUser;

public interface StatasticsService {
    public List<MostViolatedMessage> getTopViolatedMessages();
    public List<MostViolatedResource> getTopViolatedResourcesWithLimit(int limit);
    public List<ViolationDetails> getRecentViolations();
    public List<SourceFile> getViolationsByUser(String userName);
    public List<GenericCountHolder> getViolationCountBySeverity();
    List<ViolationsByUser> getViolationsForAllDevelopers();
    public List<ViolationDetails> getViolationsForResource(String resourceName);
    List<ViolationDetails> getAllViolations();
}
