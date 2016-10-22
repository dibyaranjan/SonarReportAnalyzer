package com.dibya.sonar.service.impl.cache;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dibya.sonar.cache.BlameDetailCache;
import com.dibya.sonar.entity.StatusType;
import com.dibya.sonar.entity.vo.ViolationDetail;
import com.dibya.sonar.service.ViolationService;

@Service
public class ViolationsServiceUsingCache implements ViolationService {
    @Autowired
    private BlameDetailCache cache;

    @Override
    public List<ViolationDetail> getAllViolations() {
        return cache.getAllBlameDetails();
    }

    @Override
    public List<ViolationDetail> getViolationsWithStatus(String status) {
        StatusType statusType = StatusType.getStatus(status);
        List<ViolationDetail> allBlameDetails = cache.getAllBlameDetails();
        List<ViolationDetail> filterdBlameDetails = new LinkedList<>();
        for (ViolationDetail blameDetail : allBlameDetails) {
            if (statusType.equals(blameDetail.getStatus())) {
                filterdBlameDetails.add(blameDetail);
            }
        }

        return filterdBlameDetails;
    }

    @Override
    public ViolationDetail getIssueByKey(String key) {
        // TODO Auto-generated method stub
        return null;
    }

}
