package com.dibya.sonar.cache;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.log4j.Logger;

import com.dibya.infra.converter.Converter;
import com.dibya.sonar.dao.SourceFilePersister;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.vo.ViolationDetails;

/**
 * An in-memory cache for the ViolationDetails. This will improve the fetch
 * performance to a great extent. The data of the cache will be valid till user
 * performs a successful sync, post a successful sync the cache should be
 * refreshed.
 * 
 * This class provides an immutable collection of ViolationDetails as to give
 * read only access to the details.
 * 
 * @author Dibya
 */
public class ViolationDetailsCache {
	private static final Logger LOGGER = Logger.getLogger(ViolationDetailsCache.class);
	private SourceFilePersister persister;
	private Converter converter;
	private Map<String, List<ViolationDetails>> blameDetails;
	private List<ViolationDetails> allBlameDetails;
	private SourceFileCache sourceFileCache;

	public void setPersister(SourceFilePersister persister) {
		this.persister = persister;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	public void setSourceFileCache(SourceFileCache sourceFileCache) {
		this.sourceFileCache = sourceFileCache;
	}

	public Map<String, List<ViolationDetails>> getAllBlameDetailsFromCache() {
		if (MapUtils.isEmpty(blameDetails)) {
			initializeCache();
		}
		return blameDetails;
	}

	public List<ViolationDetails> getAllBlameDetails() {
		return allBlameDetails;
	}

	public void initializeCache() {
		List<SourceFile> sourceFiles;
		if (sourceFileCache == null) {
			sourceFiles = persister.loadAllSourceFilesEagerly();
		} else {
			Map<String, SourceFile> allSourceFilesFromCache = sourceFileCache.getAllSourceFilesFromCache();
			sourceFiles = new LinkedList<>(allSourceFilesFromCache.values());
		}
		
		if (CollectionUtils.isEmpty(sourceFiles)) {
            LOGGER.info("Nothing to cache");
            return;
        }

		blameDetails = new LinkedHashMap<>();
		allBlameDetails = new LinkedList<>();
		setBlameDetailsFromSourceFiles(sourceFiles);
		LOGGER.info("Cache initialized at : " + new Date());
	}

	private void setBlameDetailsFromSourceFiles(List<SourceFile> sourceFiles) {
		for (SourceFile sourceFile : sourceFiles) {
			ViolationDetails violationDetails = converter.convert(new ViolationDetails(), sourceFile);
			ViolationDetails next = violationDetails;
			List<ViolationDetails> convertedBlameDetails = new LinkedList<ViolationDetails>();
			while (next!= null){
				convertedBlameDetails.add(next);
				next = next.getNext();
			}
			
			allBlameDetails.addAll(convertedBlameDetails);
			if (CollectionUtils.isNotEmpty(convertedBlameDetails)) {
				List<ViolationDetails> existingBlameDetails = blameDetails.get(sourceFile.getName());
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
