package com.dibya.sonar.service.impl.cache;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dibya.sonar.cache.BlameDetailCache;
import com.dibya.sonar.entity.SeverityType;
import com.dibya.sonar.entity.StatusType;
import com.dibya.sonar.entity.vo.ViolationDetail;
import com.dibya.sonar.entity.vo.GenericCountHolder;
import com.dibya.sonar.entity.vo.Resource;
import com.dibya.sonar.service.SeverityService;

@Service
public class SeverityServiceUsingCache implements SeverityService {
    @Autowired
    private BlameDetailCache cache;

    @Override
    public List<GenericCountHolder> getSeverityCounts(String status) {
        Map<String, List<ViolationDetail>> allBlameDetailsFromCache = cache.getAllBlameDetailsFromCache();
        Set<Entry<String, List<ViolationDetail>>> entrySet = allBlameDetailsFromCache.entrySet();
        Map<SeverityType, Integer> severityCounts = new TreeMap<>();
        StatusType statusType = StatusType.getStatus(status);
        
        for (Entry<String, List<ViolationDetail>> entry : entrySet) {
            List<ViolationDetail> value = entry.getValue();
            for (ViolationDetail issue : value) {
                if (statusType.equals(issue.getStatus())) {
                    setSeverityCount(severityCounts, issue.getSeverity());
                }
            }
        }

        Set<Entry<SeverityType, Integer>> severityCountEntrySet = severityCounts.entrySet();
        List<GenericCountHolder> countHolders = new ArrayList<>();
        for (Entry<SeverityType, Integer> entry : severityCountEntrySet) {
            GenericCountHolder countHolder = new GenericCountHolder();
            countHolder.setLabel(entry.getKey().getSeverity());
            countHolder.setCount(entry.getValue());
            countHolders.add(countHolder);
        }

        return countHolders;
    }

    private void setSeverityCount(Map<SeverityType, Integer> severityCounts, SeverityType severityType) {
        Integer existingCount = severityCounts.get(severityType);
        if (existingCount == null) {
            existingCount = 0;
        }
        existingCount++;
        severityCounts.put(severityType, existingCount);
    }

    @Override
    public List<ViolationDetail> getAllIssueForSeverity(String severity) {
        SeverityType selectedSeverity = SeverityType.getSeverity(severity);

        Map<String, List<ViolationDetail>> allBlameDetailsFromCache = cache.getAllBlameDetailsFromCache();
        Set<Entry<String, List<ViolationDetail>>> entrySet = allBlameDetailsFromCache.entrySet();

        List<ViolationDetail> filterdBlameDetails = new LinkedList<>();

        for (Entry<String, List<ViolationDetail>> entry : entrySet) {
            List<ViolationDetail> value = entry.getValue();
            for (ViolationDetail issue : value) {
                if (selectedSeverity.equals(issue.getSeverity())) {
                    filterdBlameDetails.add(issue);
                }
            }
        }

        return filterdBlameDetails;
    }

    @Override
    public Set<Resource> getResourcesForSeverity(String severity) {
        SeverityType selectedSeverity = SeverityType.getSeverity(severity);
        Map<String, List<ViolationDetail>> allBlameDetailsFromCache = cache.getAllBlameDetailsFromCache();
        Set<Entry<String, List<ViolationDetail>>> entrySet = allBlameDetailsFromCache.entrySet();
        Set<Resource> uniqueResources = new LinkedHashSet<>();

        for (Entry<String, List<ViolationDetail>> entry : entrySet) {
            List<ViolationDetail> value = entry.getValue();
            for (ViolationDetail issue : value) {
                if (selectedSeverity.equals(issue.getSeverity())) {
                    uniqueResources.add(issue.getResource());
                }
            }
        }

        return uniqueResources;
    }
}
