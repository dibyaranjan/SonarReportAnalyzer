package com.dibya.sonar.service.impl.cache;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dibya.sonar.cache.ViolationDetailsCache;
import com.dibya.sonar.entity.SeverityType;
import com.dibya.sonar.entity.vo.ViolationDetails;
import com.dibya.sonar.entity.vo.Resource;
import com.dibya.sonar.service.ResourceService;

@Service
public class ResourceServiceUsingCache implements ResourceService {
    @Autowired
    private ViolationDetailsCache cache;

    @Override
    public List<ViolationDetails> getIssuesForResource(int resourceId) {
        Map<String, List<ViolationDetails>> allBlameDetailsFromCache = cache.getAllBlameDetailsFromCache();
        Set<Entry<String, List<ViolationDetails>>> entrySet = allBlameDetailsFromCache.entrySet();

        List<ViolationDetails> blameDetails = new LinkedList<>();

        for (Entry<String, List<ViolationDetails>> entry : entrySet) {
            List<ViolationDetails> value = entry.getValue();
            for (ViolationDetails blameDetail : value) {
                if (blameDetail.getResource().getResourceId() == resourceId) {
                    blameDetails.add(blameDetail);
                }
            }
        }

        return blameDetails;
    }

    @Override
    public List<ViolationDetails> getIssuesForResource(int resourceId, String severity) {
        SeverityType selectedSeverity = SeverityType.getSeverity(severity);

        List<ViolationDetails> issuesForResource = getIssuesForResource(resourceId);

        List<ViolationDetails> filteredIssue = new LinkedList<>();

        for (ViolationDetails blameDetail : issuesForResource) {
            if (selectedSeverity.equals(blameDetail.getSeverity())) {
                filteredIssue.add(blameDetail);
            }
        }
        return filteredIssue;
    }

    @Override
    public Set<Resource> getAllResources() {
        Map<String, List<ViolationDetails>> allBlameDetailsFromCache = cache.getAllBlameDetailsFromCache();
        Set<Entry<String, List<ViolationDetails>>> entrySet = allBlameDetailsFromCache.entrySet();

        Set<Resource> resources = new LinkedHashSet<>();

        for (Entry<String, List<ViolationDetails>> entry : entrySet) {
            List<ViolationDetails> value = entry.getValue();
            for (ViolationDetails blameDetail : value) {
                resources.add(blameDetail.getResource());
            }
        }

        return resources;
    }
}
