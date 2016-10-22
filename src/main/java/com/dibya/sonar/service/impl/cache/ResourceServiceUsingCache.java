package com.dibya.sonar.service.impl.cache;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dibya.sonar.cache.BlameDetailCache;
import com.dibya.sonar.entity.SeverityType;
import com.dibya.sonar.entity.vo.ViolationDetail;
import com.dibya.sonar.entity.vo.Resource;
import com.dibya.sonar.service.ResourceService;

@Service
public class ResourceServiceUsingCache implements ResourceService {
    @Autowired
    private BlameDetailCache cache;

    @Override
    public List<ViolationDetail> getIssuesForResource(int resourceId) {
        Map<String, List<ViolationDetail>> allBlameDetailsFromCache = cache.getAllBlameDetailsFromCache();
        Set<Entry<String, List<ViolationDetail>>> entrySet = allBlameDetailsFromCache.entrySet();

        List<ViolationDetail> blameDetails = new LinkedList<>();

        for (Entry<String, List<ViolationDetail>> entry : entrySet) {
            List<ViolationDetail> value = entry.getValue();
            for (ViolationDetail blameDetail : value) {
                if (blameDetail.getResource().getResourceId() == resourceId) {
                    blameDetails.add(blameDetail);
                }
            }
        }

        return blameDetails;
    }

    @Override
    public List<ViolationDetail> getIssuesForResource(int resourceId, String severity) {
        SeverityType selectedSeverity = SeverityType.getSeverity(severity);

        List<ViolationDetail> issuesForResource = getIssuesForResource(resourceId);

        List<ViolationDetail> filteredIssue = new LinkedList<>();

        for (ViolationDetail blameDetail : issuesForResource) {
            if (selectedSeverity.equals(blameDetail.getSeverity())) {
                filteredIssue.add(blameDetail);
            }
        }
        return filteredIssue;
    }

    @Override
    public Set<Resource> getAllResources() {
        Map<String, List<ViolationDetail>> allBlameDetailsFromCache = cache.getAllBlameDetailsFromCache();
        Set<Entry<String, List<ViolationDetail>>> entrySet = allBlameDetailsFromCache.entrySet();

        Set<Resource> resources = new LinkedHashSet<>();

        for (Entry<String, List<ViolationDetail>> entry : entrySet) {
            List<ViolationDetail> value = entry.getValue();
            for (ViolationDetail blameDetail : value) {
                resources.add(blameDetail.getResource());
            }
        }

        return resources;
    }
}
