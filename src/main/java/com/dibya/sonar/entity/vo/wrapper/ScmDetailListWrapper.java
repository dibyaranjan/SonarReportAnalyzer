package com.dibya.sonar.entity.vo.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.dibya.sonar.entity.ScmDetail;

/**
 * A wrapper class to hold list of sourceFiles.
 * 
 * There is a limitation of the converter framework. It doesn't support list
 * conversion directly refer to javadoc of the Converters for more details.
 * 
 * This wrapper provides an work around for object conversion.
 *
 * @author Dibya Ranjan
 */
public class ScmDetailListWrapper {
    private List<ScmDetail> scmDetails = new ArrayList<>();

    public List<ScmDetail> getScmDetails() {
        return scmDetails;
    }

    public void setScmDetails(List<ScmDetail> scmDetails) {
        this.scmDetails = scmDetails;
    }

    @Override
    public String toString() {
        return "ScmDetailListWrapper [scmDetails=" + scmDetails + "]";
    }
}
