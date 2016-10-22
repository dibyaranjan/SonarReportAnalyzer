package com.dibya.sonar.converter.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dibya.sonar.converter.AbstractConverter;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.vo.IssueVo;
import com.dibya.sonar.entity.vo.wrapper.IssueVoListWrapper;
import com.dibya.sonar.entity.vo.wrapper.SourceFileListWrapper;

public class SourceFileListWrapperFromIssueListWrapperConverter extends AbstractConverter {
    private static final Logger LOGGER = Logger.getLogger(SourceFileListWrapperFromIssueListWrapperConverter.class);
    
    public SourceFileListWrapperFromIssueListWrapperConverter() {
        LOGGER.info("Registered " + getClass());
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T, S> T doConvert(S sourceObject) {
        IssueVoListWrapper source = (IssueVoListWrapper) sourceObject;
        List<IssueVo> issueVoList = source.getIssueVoList();
        Map<String, SourceFile> consolidatedIssuesByFile = new LinkedHashMap<>();
        for (IssueVo sourceIssue : issueVoList) {
            com.dibya.sonar.entity.Issue convertedIssue = converter.convert(new com.dibya.sonar.entity.Issue(),
                    sourceIssue);
            
            String fileName = sourceIssue.getComponent();

            SourceFile sourceFile = getExistingFile(consolidatedIssuesByFile, fileName);
            
            sourceFile.getIssues().add(convertedIssue);

            consolidatedIssuesByFile.put(fileName, sourceFile);
        }

        List<SourceFile> consolidatedSourceFile = new ArrayList<>(consolidatedIssuesByFile.values());

        SourceFileListWrapper target = new SourceFileListWrapper();
        target.setSourceFiles(consolidatedSourceFile);

        return (T) target;
    }

    private SourceFile getExistingFile(Map<String, SourceFile> consolidatedIssuesByFile, String fileName) {
        SourceFile sourceFile = consolidatedIssuesByFile.get(fileName);
        if (sourceFile == null) {
            sourceFile = new SourceFile();
            sourceFile.setName(fileName);
        }
        return sourceFile;
    }

}
