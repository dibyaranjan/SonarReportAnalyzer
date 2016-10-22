package com.dibya.sonar.entity.vo;

public class MostViolatedResource {
    private String resourcePath;
    private int count;

    public String getResourcePath() {
        return resourcePath;
    }

    public void setResourcePath(String resourcePath) {
        this.resourcePath = resourcePath;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MostViolatedResource [resourcePath=" + resourcePath + ", count=" + count + "]";
    }
}
