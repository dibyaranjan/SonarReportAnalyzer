package com.dibya.sonar.entity.vo.wrapper;

import java.util.ArrayList;
import java.util.List;

import com.dibya.sonar.entity.SourceFile;

/**
 * A wrapper class to hold list of sourceFiles.
 * 
 * There is a limitation of the converter framework. It doesn't support list
 * conversion directly, refer to javadoc of the Converters for more details.
 * 
 * This wrapper provides an work around for object conversion.
 *
 * @author Dibya Ranjan
 */
public class SourceFileListWrapper {
    private List<SourceFile> sourceFiles = new ArrayList<>();

    public List<SourceFile> getSourceFiles() {
        return sourceFiles;
    }

    public void setSourceFiles(List<SourceFile> sourceFiles) {
        this.sourceFiles = sourceFiles;
    }

    @Override
    public String toString() {
        return "SourceFileListWrapper [sourceFiles=" + sourceFiles + "]";
    }

}
