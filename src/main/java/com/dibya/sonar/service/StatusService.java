package com.dibya.sonar.service;

import java.util.List;

import com.dibya.sonar.entity.vo.ViolationDetails;
import com.dibya.sonar.entity.vo.GenericCountHolder;

public interface StatusService {

    List<ViolationDetails> getIssuesForStatus(String status);

    List<GenericCountHolder> getAllStatusesCount();

}
