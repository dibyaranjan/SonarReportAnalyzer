package com.dibya.sonar.service.impl.cache;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dibya.sonar.cache.ViolationDetailsCache;
import com.dibya.sonar.entity.StatusType;
import com.dibya.sonar.entity.vo.GenericCountHolder;
import com.dibya.sonar.entity.vo.ViolationDetails;
import com.dibya.sonar.service.UserService;

/**
 *
 * @author Dibya Ranjan
 */
@Service
public class UserServiceUsingCache implements UserService {
    @Autowired
    private ViolationDetailsCache cache;
    
    @Override
    public List<GenericCountHolder> getUsersWithViolationCount() {
        List<ViolationDetails> allBlameDetails = cache.getAllBlameDetails();
        Map<String, Integer> violationCounts = new TreeMap<>();
        for (ViolationDetails violationDetail: allBlameDetails) {
            setViolationCount(violationCounts, violationDetail.getAuthor());
        }
        
        Set<Entry<String, Integer>> entrySet = violationCounts.entrySet();
        List<GenericCountHolder> countHolders = new LinkedList<>();
        for (Entry<String, Integer> entry : entrySet) {
            GenericCountHolder countHolder = new GenericCountHolder();
            countHolder.setCount(entry.getValue());
            countHolder.setLabel(entry.getKey());
            
            countHolders.add(countHolder);
        }
        
        return countHolders;
    }

    private void setViolationCount(Map<String, Integer> violationCounts, String author) {
		if (author == null) {
			return;
		}
        Integer existingCount = violationCounts.get(author);
        if (existingCount == null) {
            existingCount = 0;
        }
        existingCount++;
        violationCounts.put(author, existingCount);
    }

    @Override
    public List<ViolationDetails> getViolationsForUser(String userId) {
        List<ViolationDetails> allViolationDetails = cache.getAllBlameDetails();
        List<ViolationDetails> filteredViolationDetails = new LinkedList<>();
        for (ViolationDetails ViolationDetail : allViolationDetails) {
            if (StringUtils.equals(ViolationDetail.getAuthor(), userId)) {
                filteredViolationDetails.add(ViolationDetail);
            }
        }
        
        return filteredViolationDetails;
    }

    @Override
    public List<ViolationDetails> getViolationsForUser(String userId, String status) {
        StatusType statusType = StatusType.getStatus(status);
        
        List<ViolationDetails> violationDetailsForUser  = getViolationsForUser(userId);
        
        List<ViolationDetails> filteredViolationDetails = new LinkedList<>();
        for (ViolationDetails violationDetail : violationDetailsForUser) {
            if (statusType.equals(violationDetail.getStatus())) {
                filteredViolationDetails.add(violationDetail);
            }
        }
        
        return filteredViolationDetails;
    }
}
