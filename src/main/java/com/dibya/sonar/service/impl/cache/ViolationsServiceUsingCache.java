package com.dibya.sonar.service.impl.cache;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dibya.sonar.cache.ViolationDetailsCache;
import com.dibya.sonar.entity.StatusType;
import com.dibya.sonar.entity.vo.ViolationDetails;
import com.dibya.sonar.service.ViolationService;

@Service
public class ViolationsServiceUsingCache implements ViolationService {
    @Autowired
    private ViolationDetailsCache cache;

    @Override
    public List<ViolationDetails> getAllViolations() {
        return cache.getAllBlameDetails();
    }

    @Override
    public List<ViolationDetails> getViolationsWithStatus(String status) {
        StatusType statusType = StatusType.getStatus(status);
        List<ViolationDetails> allBlameDetails = cache.getAllBlameDetails();
        List<ViolationDetails> filterdBlameDetails = new LinkedList<>();
        for (ViolationDetails blameDetail : allBlameDetails) {
            if (statusType.equals(blameDetail.getStatus())) {
                filterdBlameDetails.add(blameDetail);
            }
        }

        return filterdBlameDetails;
    }

    @Override
    public ViolationDetails getIssueByKey(String key) {
        // TODO Auto-generated method stub
        return null;
    }

}
