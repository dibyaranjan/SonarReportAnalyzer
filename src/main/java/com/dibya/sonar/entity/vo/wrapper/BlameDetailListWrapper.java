package com.dibya.sonar.entity.vo.wrapper;

import java.util.List;

import com.dibya.sonar.entity.vo.ViolationDetail;

public class BlameDetailListWrapper {
    private List<ViolationDetail> blameDetails;

    public List<ViolationDetail> getBlameDetails() {
        return blameDetails;
    }

    public void setBlameDetails(List<ViolationDetail> blameDetails) {
        this.blameDetails = blameDetails;
    }
}
