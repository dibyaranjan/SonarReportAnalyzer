package com.dibya.sonar.entity.builder;

import com.dibya.sonar.entity.SourceFile;

public class SourceFileBuilder {
    private int buildCount;
    
    public SourceFile build() {
        buildCount++;
        SourceFile sourceFile = new SourceFile();
        sourceFile.setId(buildCount);
        sourceFile.setName("AuditTool:AuditTool:src/main/java/com/sita/audit/tool/aop/LoginValidator.java");
        return sourceFile;
    }
}
