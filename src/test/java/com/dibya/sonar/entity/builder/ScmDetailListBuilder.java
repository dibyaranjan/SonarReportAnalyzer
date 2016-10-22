package com.dibya.sonar.entity.builder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dibya.sonar.entity.ScmDetail;

public class ScmDetailListBuilder {
    public List<ScmDetail> build() {
        List<ScmDetail> list = new ArrayList<>();
        ScmDetail scmDetail1 = new ScmDetail();
        scmDetail1.setDateIntroduced(new Date());
        scmDetail1.setEmail("test@mindtree.com");
        scmDetail1.setId(1);
        scmDetail1.setLineNumber(10);

        ScmDetail scmDetail2 = new ScmDetail();
        scmDetail2.setDateIntroduced(new Date());
        scmDetail2.setEmail("test2@mindtree.com");
        scmDetail2.setId(2);
        scmDetail2.setLineNumber(50);

        list.add(scmDetail2);
        list.add(scmDetail1);
        return list;
    }
}
