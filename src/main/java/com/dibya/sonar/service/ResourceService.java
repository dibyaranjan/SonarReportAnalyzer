package com.dibya.sonar.service;

import java.util.List;
import java.util.Set;

import com.dibya.sonar.entity.vo.ViolationDetails;
import com.dibya.sonar.entity.vo.Resource;

public interface ResourceService {

    List<ViolationDetails> getIssuesForResource(int resourceId);

    List<ViolationDetails> getIssuesForResource(int resourceId, String severity);

    Set<Resource> getAllResources();

}
