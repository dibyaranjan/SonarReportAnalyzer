package com.dibya.sonar.entity.vo.wrapper;

import java.util.List;

import com.dibya.sonar.entity.vo.ViolationDetails;

@Deprecated
public class BlameDetailListWrapper {
    private List<ViolationDetails> blameDetails;

    public List<ViolationDetails> getBlameDetails() {
        return blameDetails;
    }

    public void setBlameDetails(List<ViolationDetails> blameDetails) {
        this.blameDetails = blameDetails;
    }
}
