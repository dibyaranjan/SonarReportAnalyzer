package com.dibya.sonar.entity.builder;

import com.dibya.sonar.entity.vo.PageMetaData;

public class PageMetaDataBuilder {
    private int buildCount;

    public PageMetaData build() {
        buildCount++;
        PageMetaData pageMetaData = new PageMetaData();
        pageMetaData.setPageIndex(buildCount);
        pageMetaData.setPages(3);
        pageMetaData.setPageSize(3);
        pageMetaData.setTotal(9);
        return pageMetaData;
    }
}
