package com.dibya.sonar.service.impl.cache;

import static com.dibya.sonar.entity.StatusType.CLOSED;
import static com.dibya.sonar.entity.StatusType.OPEN;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dibya.sonar.cache.ViolationDetailsCache;
import com.dibya.sonar.entity.StatusType;
import com.dibya.sonar.entity.vo.ViolationDetails;
import com.dibya.sonar.entity.vo.GenericCountHolder;
import com.dibya.sonar.service.StatusService;

@Service
public class StatusServiceUsingCache implements StatusService {

    @Autowired
    private ViolationDetailsCache cache;

    @Override
    public List<ViolationDetails> getIssuesForStatus(String status) {
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
    public List<GenericCountHolder> getAllStatusesCount() {
        Map<String, List<ViolationDetails>> allBlameDetailsFromCache = cache.getAllBlameDetailsFromCache();
        Set<Entry<String, List<ViolationDetails>>> entrySet = allBlameDetailsFromCache.entrySet();

        int openCount = 0;
        int closedCount = 0;

        for (Entry<String, List<ViolationDetails>> entry : entrySet) {
            List<ViolationDetails> blameDetails = entry.getValue();
            for (ViolationDetails blameDetail : blameDetails) {
                if (CLOSED.equals(blameDetail.getStatus())) {
                    closedCount++;
                } else {
                    openCount++;
                }
            }
        }

        GenericCountHolder openCountHolder = new GenericCountHolder();
        openCountHolder.setLabel(OPEN.getStatus());
        openCountHolder.setCount(openCount);

        GenericCountHolder closedCountHolder = new GenericCountHolder();
        closedCountHolder.setLabel(CLOSED.getStatus());
        closedCountHolder.setCount(closedCount);

        return Arrays.asList(openCountHolder, closedCountHolder);
    }
}
