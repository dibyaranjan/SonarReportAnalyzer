package com.dibya.sonar.cache;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.dibya.sonar.dao.SourceFilePersister;
import com.dibya.sonar.entity.SourceFile;

public class SourceFileCache {
    private static final Logger LOGGER = Logger.getLogger(SourceFileCache.class);
    
    private Map<String, SourceFile> cachedSourceFilesByName;

    private SourceFilePersister persister;
    
    public void setPersister(SourceFilePersister persister) {
        this.persister = persister;
    }

    public Map<String, SourceFile> getAllSourceFilesFromCache() {
        if (MapUtils.isEmpty(cachedSourceFilesByName)) {
            initializeCache();
        }

        return cachedSourceFilesByName;
    }

    public void initializeCache() {
        cachedSourceFilesByName = new LinkedHashMap<>();
        List<SourceFile> allSourceFiles = persister.loadAllSourceFilesEagerly();
        for (SourceFile sourceFile : allSourceFiles) {
            cachedSourceFilesByName.put(sourceFile.getName(), sourceFile);
        }
        LOGGER.info("Cache initialized at : " + new Date());
    }
    
    public void refresh() {
        cachedSourceFilesByName = null;
        initializeCache();
    }
}
