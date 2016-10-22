package com.dibya.sonar.service;

import java.util.List;

import com.dibya.sonar.entity.vo.ViolationDetail;
import com.dibya.sonar.entity.vo.GenericCountHolder;

public interface StatusService {

    List<ViolationDetail> getIssuesForStatus(String status);

    List<GenericCountHolder> getAllStatusesCount();

}
