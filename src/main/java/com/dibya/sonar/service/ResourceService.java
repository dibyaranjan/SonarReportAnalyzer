package com.dibya.sonar.service;

import java.util.List;
import java.util.Set;

import com.dibya.sonar.entity.vo.ViolationDetail;
import com.dibya.sonar.entity.vo.Resource;

public interface ResourceService {

    List<ViolationDetail> getIssuesForResource(int resourceId);

    List<ViolationDetail> getIssuesForResource(int resourceId, String severity);

    Set<Resource> getAllResources();

}
