package com.dibya.sonar.cache;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.dibya.sonar.converter.BaseConverter;
import com.dibya.sonar.dao.SourceFilePersister;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.vo.ViolationDetail;
import com.dibya.sonar.entity.vo.wrapper.BlameDetailListWrapper;

public class BlameDetailCache {
    private static final Logger LOGGER = Logger.getLogger(BlameDetailCache.class);
    private SourceFilePersister persister;
    private BaseConverter converter;
    private Map<String, List<ViolationDetail>> blameDetails;
    private List<ViolationDetail> allBlameDetails;
    private SourceFileCache sourceFileCache;

    public void setPersister(SourceFilePersister persister) {
        this.persister = persister;
    }

    public void setConverter(BaseConverter converter) {
        this.converter = converter;
    }
    
    public void setSourceFileCache(SourceFileCache sourceFileCache) {
        this.sourceFileCache = sourceFileCache;
    }

    public Map<String, List<ViolationDetail>> getAllBlameDetailsFromCache() {
        if (MapUtils.isEmpty(blameDetails)) {
            initializeCache();
        }
        return blameDetails;
    }
    
    public List<ViolationDetail> getAllBlameDetails() {
        return allBlameDetails;
    }

    public void initializeCache() {
        LOGGER.info("Cache initialized at : " + new Date());
        List<SourceFile> sourceFiles;
        if (sourceFileCache == null) {
            sourceFiles = persister.loadAllSourceFilesEagerly();
        } else {
            Map<String, SourceFile> allSourceFilesFromCache = sourceFileCache.getAllSourceFilesFromCache();
            sourceFiles = new LinkedList<>(allSourceFilesFromCache.values());
        }
        
        blameDetails = new LinkedHashMap<>();
        allBlameDetails = new LinkedList<>();
        setBlameDetailsFromSourceFiles(sourceFiles);
    }

    private void setBlameDetailsFromSourceFiles(List<SourceFile> sourceFiles) {
        for (SourceFile sourceFile : sourceFiles) {
            BlameDetailListWrapper blameDetailListWrapper = converter.convert(new BlameDetailListWrapper(), sourceFile);
            List<ViolationDetail> convertedBlameDetails = blameDetailListWrapper.getBlameDetails();
            allBlameDetails.addAll(convertedBlameDetails);
            if (CollectionUtils.isNotEmpty(convertedBlameDetails)) {
                List<ViolationDetail> existingBlameDetails = blameDetails.get(sourceFile.getName());
                if (existingBlameDetails == null) {
                    existingBlameDetails = new LinkedList<>();
                }

                existingBlameDetails.addAll(convertedBlameDetails);
                blameDetails.put(sourceFile.getName(), existingBlameDetails);
            }
        }
        
        CollectionUtils.unmodifiableCollection(allBlameDetails);
    }
    
    public void refresh() {
        blameDetails = null;
        initializeCache();
    }
}
