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

import com.dibya.sonar.cache.BlameDetailCache;
import com.dibya.sonar.entity.StatusType;
import com.dibya.sonar.entity.vo.ViolationDetail;
import com.dibya.sonar.entity.vo.GenericCountHolder;
import com.dibya.sonar.service.StatusService;

@Service
public class StatusServiceUsingCache implements StatusService {

    @Autowired
    private BlameDetailCache cache;

    @Override
    public List<ViolationDetail> getIssuesForStatus(String status) {
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
    public List<GenericCountHolder> getAllStatusesCount() {
        Map<String, List<ViolationDetail>> allBlameDetailsFromCache = cache.getAllBlameDetailsFromCache();
        Set<Entry<String, List<ViolationDetail>>> entrySet = allBlameDetailsFromCache.entrySet();

        int openCount = 0;
        int closedCount = 0;

        for (Entry<String, List<ViolationDetail>> entry : entrySet) {
            List<ViolationDetail> blameDetails = entry.getValue();
            for (ViolationDetail blameDetail : blameDetails) {
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
