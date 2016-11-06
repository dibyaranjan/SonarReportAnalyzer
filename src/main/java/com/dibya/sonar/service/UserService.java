package com.dibya.sonar.service;

import java.util.List;

import com.dibya.sonar.entity.vo.ViolationDetails;
import com.dibya.sonar.entity.vo.GenericCountHolder;

public interface UserService {
    List<GenericCountHolder> getUsersWithViolationCount();
    List<ViolationDetails> getViolationsForUser(String userId);
    List<ViolationDetails> getViolationsForUser(String userId, String status);
}
