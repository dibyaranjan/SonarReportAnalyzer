package com.dibya.sonar.dao;

import java.util.List;

import com.dibya.sonar.entity.vo.MostViolatedMessage;
import com.dibya.sonar.entity.vo.MostViolatedResource;

public interface StatisticsPersister {
    List<MostViolatedMessage> getTopViolatedRules();
    List<MostViolatedResource> getTopViolatedResourcesWithLimit(int limit);
    List<MostViolatedResource> getTopViolatedResourcesWithoutLimit();
    void getViolationsByAllDevelopers();
}
