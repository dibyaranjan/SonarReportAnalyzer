package com.dibya.sonar.converter.adapter;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;

import com.dibya.sonar.converter.AbstractConverter;
import com.dibya.sonar.entity.Issue;
import com.dibya.sonar.entity.ScmDetail;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.vo.ViolationDetail;
import com.dibya.sonar.entity.vo.Resource;
import com.dibya.sonar.entity.vo.wrapper.BlameDetailListWrapper;

public class BlameDetailListWrapperFromSourceFileConverter extends AbstractConverter {
    private static final Logger LOGGER = Logger.getLogger(BlameDetailListWrapperFromSourceFileConverter.class);
    
    private static final int SINGLE_ELEMENT_SIZE = 1;
    private static final int INVALID_INDEX = -1;
    
    public BlameDetailListWrapperFromSourceFileConverter() {
        LOGGER.info("Registered " + getClass());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T, S> T doConvert(S sourceObject) {
        SourceFile sourceFile = (SourceFile) sourceObject;
        BlameDetailListWrapper target = new BlameDetailListWrapper();

        List<Issue> issues = sourceFile.getIssues();
        List<ScmDetail> scmDetails = sourceFile.getScmDetails();

        List<ViolationDetail> blameDetails = new LinkedList<>();
        for (Issue issue : issues) {
            Resource resource = new Resource();
            resource.setResourceId(sourceFile.getId());
            resource.setResourceName(sourceFile.getName());
            
            int issueLine = issue.getLine();
            int indexOfScmToBeBlamed = getIndexOfScmToBeBlamed(scmDetails, issueLine);
            if (indexOfScmToBeBlamed == INVALID_INDEX) {
                continue;
            }
            
            ScmDetail scmDetail = scmDetails.get(indexOfScmToBeBlamed);
            DateTime dateTime = new DateTime(scmDetail.getDateIntroduced().getTime());
            
            ViolationDetail blameDetail = new ViolationDetail();
            blameDetail.setResource(resource);
            blameDetail.setAuthor(scmDetail.getEmail());
            blameDetail.setDateIntroduced(dateTime.toString("dd-MMM-yyyy"));
            blameDetail.setLineNumber(issueLine);
            blameDetail.setSeverity(issue.getSeverity());
            blameDetail.setStatus(issue.getStatus());
            blameDetail.setMessage(issue.getMessage());
            blameDetail.setRule(issue.getRule());

            blameDetails.add(blameDetail);
        }

        target.setBlameDetails(blameDetails);

        return (T) target;
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
}
