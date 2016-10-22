package com.dibya.sonar.dao;

import java.util.List;

import com.dibya.sonar.entity.SourceFile;

public interface SourceFilePersister {
    List<SourceFile> getAllSourceFiles();
    List<SourceFile> loadAllSourceFilesEagerly();
    void save(SourceFile sourceFile);
    List<SourceFile> loadSessionLessSourceFilesEagerly();
}
