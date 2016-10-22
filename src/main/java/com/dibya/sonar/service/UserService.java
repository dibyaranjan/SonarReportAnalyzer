package com.dibya.sonar.service;

import java.util.List;

import com.dibya.sonar.entity.vo.ViolationDetail;
import com.dibya.sonar.entity.vo.GenericCountHolder;

public interface UserService {
    List<GenericCountHolder> getUsersWithViolationCount();
    List<ViolationDetail> getViolationsForUser(String userId);
    List<ViolationDetail> getViolationsForUser(String userId, String status);
}
