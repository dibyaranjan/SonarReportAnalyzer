package com.dibya.sonar.service.impl.cache;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.collections.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dibya.sonar.cache.ViolationDetailsCache;
import com.dibya.sonar.cache.SourceFileCache;
import com.dibya.sonar.dao.StatisticsPersister;
import com.dibya.sonar.entity.Issue;
import com.dibya.sonar.entity.ScmDetail;
import com.dibya.sonar.entity.SeverityType;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.vo.ViolationDetails;
import com.dibya.sonar.entity.vo.GenericCountHolder;
import com.dibya.sonar.entity.vo.MostViolatedMessage;
import com.dibya.sonar.entity.vo.MostViolatedResource;
import com.dibya.sonar.entity.vo.ViolationsByUser;
import com.dibya.sonar.service.StatasticsService;

@Service
public class StatasticsServiceImpl implements StatasticsService {
    private static final int SINGLE_ELEMENT_SIZE = 1;
    private static final int INVALID_INDEX = -1;

    @Autowired
    private StatisticsPersister persister;

    @Autowired
    private SourceFileCache cache;

    @Autowired
    private ViolationDetailsCache blameDetailsCache;

    public void setPersister(StatisticsPersister persister) {
        this.persister = persister;
    }

    public void setCache(SourceFileCache cache) {
        this.cache = cache;
    }

    public void setBlameDetailsCache(ViolationDetailsCache blameDetailsCache) {
        this.blameDetailsCache = blameDetailsCache;
    }

    @Override
    public List<MostViolatedMessage> getTopViolatedMessages() {
        return persister.getTopViolatedRules();
    }

    @Override
    public List<MostViolatedResource> getTopViolatedResourcesWithLimit(int limit) {
        if (limit == 0) {
            return persister.getTopViolatedResourcesWithoutLimit();
        }

        return persister.getTopViolatedResourcesWithLimit(limit);
    }

    @Override
    public List<ViolationsByUser> getViolationsForAllDevelopers() {
        Map<String, SourceFile> allSourceFilesFromCache = cache.getAllSourceFilesFromCache();
        Set<Entry<String, SourceFile>> entrySet = allSourceFilesFromCache.entrySet();
        Map<String, Integer> violationCount = new TreeMap<>();
        for (Entry<String, SourceFile> entry : entrySet) {
            SourceFile source = entry.getValue();
            updateViolationCount(violationCount, source);
        }

        List<ViolationsByUser> violations = new ArrayList<>();
        for (Entry<String, Integer> entry : violationCount.entrySet()) {
            ViolationsByUser violation = new ViolationsByUser();
            violation.setEmail(entry.getKey());
            violation.setCountOfViolations(entry.getValue());
            violations.add(violation);
        }

        return violations;
    }

    private void updateViolationCount(Map<String, Integer> violationCount, SourceFile source) {
        List<Issue> issues = source.getIssues();
        List<ScmDetail> scmDetails = source.getScmDetails();

        for (Issue issue : issues) {
            int indexOfScm = getIndexOfScmToBeBlamed(scmDetails, issue.getLine());
            if (indexOfScm != INVALID_INDEX) {
                ScmDetail scmDetail = scmDetails.get(indexOfScm);
                String email = scmDetail.getEmail();
                Integer existingCount = violationCount.get(email);
                if (existingCount == null) {
                    existingCount = 0;
                }
                existingCount++;
                violationCount.put(email, existingCount);
            }
        }
    }

    /**
     * Returns the index of the scmDetails which is responsible to the current
     * sonar issue.
     * 
     * The SCM details are marked in single line starting from line number 1.
     * Please refer any sonar violation page and click on SCM tab to learn how
     * it looks.
     * 
     * If the user1 appears in line 1 and user2 in line 13, all violations
     * between 1 to 13 is introduced by user1. Similarly, if user1 appears in
     * line 28 then, all violations from line 13 to line 28 is introduced by
     * user2.
     * 
     * To identify user1's scope, we need to identify the next closest line
     * number from the issueLine. The SCM details would be marking the
     * violations for the other user. So, index - 1 would give the User who is
     * responsible for the violation.
     * 
     * If we have only one SCM details for the file, any violation regardless of
     * the line number are introduced by the same user.
     * 
     * If we have a violation which is present after the last scmDetails, the
     * last user has introduced the sonar violation.
     * 
     * @param scmDetails
     *            The SCM details of the file sorted by line number
     * @param issueLine
     *            The line where violation exists
     * 
     * @return The SCM details of the user who introduced the violation
     */
    private int getIndexOfScmToBeBlamed(List<ScmDetail> scmDetails, Integer issueLine) {
        int index = INVALID_INDEX;
        if (CollectionUtils.isEmpty(scmDetails)) {
            return index;
        }

        if (scmDetails.size() == SINGLE_ELEMENT_SIZE) {
            return 0;
        }

        ScmDetail lastScmDetails = scmDetails.get(scmDetails.size() - 1);
        if (lastScmDetails.getLineNumber() < issueLine) {
            return scmDetails.size() - 1;
        }

        for (ScmDetail scmDetail : scmDetails) {
            if (scmDetail.getLineNumber() == issueLine) {
                index = scmDetails.indexOf(scmDetail);
            } else if (scmDetail.getLineNumber() > issueLine) {
                index = scmDetails.indexOf(scmDetail) - 1;
            }
        }

        return index;
    }

    @Override
    public List<ViolationDetails> getRecentViolations() {
        Map<String, List<ViolationDetails>> allBlameDetailsFromCache = blameDetailsCache.getAllBlameDetailsFromCache();
        Set<Entry<String, List<ViolationDetails>>> entrySet = allBlameDetailsFromCache.entrySet();
        List<ViolationDetails> mostRecentBlameDetails = new LinkedList<>();
        for (Entry<String, List<ViolationDetails>> entry : entrySet) {
            List<ViolationDetails> blameDetails = entry.getValue();
            mostRecentBlameDetails.addAll(blameDetails);
        }

        Collections.sort(mostRecentBlameDetails, new Comparator<ViolationDetails>() {
            @Override
            public int compare(ViolationDetails o1, ViolationDetails o2) {
                DateTimeFormatter formatter = DateTimeFormat.forPattern("dd-MMM-yyyy");
                if (o1.getDateIntroduced() == null) {
                	return 1;
                }
                
                if (o2.getDateIntroduced() == null) {
                	return -1;
                }
                
                DateTime o1DateIntroduced = formatter.parseDateTime(o1.getDateIntroduced());
                DateTime o2DateIntroduced = formatter.parseDateTime(o2.getDateIntroduced());

                return Long.valueOf(o2DateIntroduced.getMillis()).compareTo(Long.valueOf(o1DateIntroduced.getMillis()));
            }

        });

        return mostRecentBlameDetails.subList(0, 50);
    }

    @Override
    public List<SourceFile> getViolationsByUser(String userName) {
        // TODO Auto-generated method stub
        return null;
    }
    
    @Override
    public List<ViolationDetails> getAllViolations() {
        Map<String, List<ViolationDetails>> allBlameDetailsFromCache = blameDetailsCache.getAllBlameDetailsFromCache();
        Set<Entry<String, List<ViolationDetails>>> entrySet = allBlameDetailsFromCache.entrySet();
        List<ViolationDetails> allBlameDetails = new LinkedList<>();
        for (Entry<String, List<ViolationDetails>> entry : entrySet) {
            List<ViolationDetails> blameDetails = entry.getValue();
            allBlameDetails.addAll(blameDetails);
        }
        
        return allBlameDetails;
    }

    @Override
    public List<GenericCountHolder> getViolationCountBySeverity() {
        Map<String, SourceFile> allSourceFilesFromCache = cache.getAllSourceFilesFromCache();
        Set<Entry<String, SourceFile>> entrySet = allSourceFilesFromCache.entrySet();
        Map<SeverityType, Integer> severityCounts = new TreeMap<>();
        for (Entry<String, SourceFile> entry : entrySet) {
            SourceFile sourceFile = entry.getValue();
            List<Issue> issues = sourceFile.getIssues();
            for (Issue issue : issues) {
                setSeverityCount(severityCounts, issue.getSeverity());
            }
        }

        Set<Entry<SeverityType, Integer>> entrySet2 = severityCounts.entrySet();
        List<GenericCountHolder> countHolders = new ArrayList<>();
        for (Entry<SeverityType, Integer> entry : entrySet2) {
            GenericCountHolder countHolder = new GenericCountHolder();
            countHolder.setLabel(entry.getKey().name());
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
    public List<ViolationDetails> getViolationsForResource(String resourceName) {
        Map<String, List<ViolationDetails>> allBlameDetailsFromCache = blameDetailsCache.getAllBlameDetailsFromCache();
        return allBlameDetailsFromCache.get(resourceName);
    }
}
