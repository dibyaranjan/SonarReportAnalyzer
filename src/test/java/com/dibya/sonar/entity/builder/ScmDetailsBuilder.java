package com.dibya.sonar.entity.builder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.dibya.sonar.entity.vo.ScmDetails;

public class ScmDetailsBuilder {
    public ScmDetails build() {
        ScmDetails scmDetails = new ScmDetails();
        List<List<String>> scm = new ArrayList<>();
        List<String> e = Arrays.asList("1", "kuppili.patnaik@mindtree.com", "2016-01-13");
        scm.add(e);
        
        e = Arrays.asList("2", "kuppili.patnaik@mindtree.com", "2016-01-13");
        scm.add(e);
        
        e = Arrays.asList("3", "kuppili.patnaik@mindtree.com", "2016-01-13");
        scm.add(e);
        
        scmDetails.setScm(scm);
        
        return scmDetails;
    }
}
