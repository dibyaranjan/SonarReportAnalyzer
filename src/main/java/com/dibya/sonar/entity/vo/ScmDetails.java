package com.dibya.sonar.entity.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import com.dibya.sonar.entity.SourceFile;

@JsonIgnoreProperties
public class ScmDetails {
    private List<List<String>> scm;
    private SourceFile sourceFile;

    public List<List<String>> getScm() {
        return scm;
    }

    public void setScm(List<List<String>> scm) {
        this.scm = scm;
    }

    public SourceFile getSourceFile() {
        return sourceFile;
    }

    public void setSourceFile(SourceFile sourceFile) {
        this.sourceFile = sourceFile;
    }
}
