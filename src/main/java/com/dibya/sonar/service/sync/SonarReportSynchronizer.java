package com.dibya.sonar.service.sync;

import java.text.MessageFormat;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dibya.sonar.cache.SourceFileCache;
import com.dibya.sonar.converter.Converter;
import com.dibya.sonar.dao.SourceFilePersister;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.vo.Issues;
import com.dibya.sonar.entity.vo.Page;
import com.dibya.sonar.entity.vo.PageMetaData;
import com.dibya.sonar.entity.vo.ScmDetails;
import com.dibya.sonar.entity.vo.wrapper.ScmDetailListWrapper;
import com.dibya.sonar.entity.vo.wrapper.SourceFileListWrapper;
import com.dibya.sonar.json.crawler.JsonCrawler;

/**
 * Class to fetch all the sonar reports from the host and save to the database
 * 
 * @author Dibya Ranjan
 */
@Service
public class SonarReportSynchronizer {
	private static final Logger LOGGER = Logger.getLogger(SonarReportSynchronizer.class);
	private static final String URL_FORMAT = "http://localhost:9000/api/issues/search?pageIndex={0}";
	private static final String SCM_URL_FORMAT = "http://localhost:9000/api/sources/scm?key=%s";

	@Autowired
	private SourceFileCache cache;

	@Autowired
	private Converter converter;

	@Autowired
	private JsonCrawler<Page> issueCrawler;

	@Autowired
	private JsonCrawler<ScmDetails> scmCrawler;

	@Autowired
	private SourceFilePersister persister;

	public void setCache(SourceFileCache cache) {
		this.cache = cache;
	}

	public void setConverter(Converter converter) {
		this.converter = converter;
	}

	public void setIssueCrawler(JsonCrawler<Page> issueCrawler) {
		this.issueCrawler = issueCrawler;
	}

	public void setScmCrawler(JsonCrawler<ScmDetails> scmCrawler) {
		this.scmCrawler = scmCrawler;
	}

	public void setPersister(SourceFilePersister persister) {
		this.persister = persister;
	}

	/**
	 * Syncs the sonar reports from the latest URL to the internal database
	 * 
	 * @return
	 */
	public boolean sync() {
		return performSync();
	}

	private boolean performSync() {
		LOGGER.info("Sync started!");

		Issues issues = getAllIssuesFromUrl();

		if (issues == null) {
			LOGGER.warn("No issues found!");
			return false;
		}

		SourceFileListWrapper sourceFileListWrapper = converter.convert(new SourceFileListWrapper(), issues);

		List<SourceFile> sourceFiles = sourceFileListWrapper.getSourceFiles();
		if (CollectionUtils.isEmpty(sourceFiles)) {
			LOGGER.info("No source files to be read");
			return false;
		}

		extractScmDetailsFromUrl(sourceFiles);

		List<SourceFile> uniqueList = getUpdateSourceFiles(sourceFiles);

		saveSourceFiles(uniqueList);
		
		cache.refresh();

		LOGGER.info("Synced!");
		return true;
	}

	private List<SourceFile> getUpdateSourceFiles(List<SourceFile> sourceFiles) {
		Map<String, SourceFile> allSourceFilesFromCache = cache.getAllSourceFilesFromCache();
		Collection<SourceFile> sourceFilesFromDb = allSourceFilesFromCache.values();
		List<SourceFile> uniqueList = new LinkedList<>();

		for (SourceFile sourceFile : sourceFiles) {
			boolean found = false;
			for (SourceFile sourceFileFromDb : sourceFilesFromDb) {
				if (sourceFileFromDb.equals(sourceFile)) {
					found = true;
					break;
				}
			}

			if (!found) {
				String fileName = sourceFile.getName();
				SourceFile existingSourceFile = allSourceFilesFromCache.get(fileName);
				if (existingSourceFile != null) {
					sourceFile.setId(existingSourceFile.getId());
				}
				uniqueList.add(sourceFile);
			}
		}

		return uniqueList;
	}

	private void saveSourceFiles(List<SourceFile> sourceFiles) {
		for (SourceFile sourceFile : sourceFiles) {
			persister.save(sourceFile);
		}
	}

	private void extractScmDetailsFromUrl(List<SourceFile> allSourceFiles) {
		for (SourceFile sourceFile : allSourceFiles) {
			ScmDetails scm = scmCrawler.retrieveJsonContentFromUrl(String.format(SCM_URL_FORMAT, sourceFile.getName()));

			if (scm != null) {
				scm.setSourceFile(sourceFile);
			}

			ScmDetailListWrapper target = converter.convert(new ScmDetailListWrapper(), scm);

			sourceFile.setScmDetails(target.getScmDetails());
		}
	}

	private Issues getAllIssuesFromUrl() {
		Page allPages = getAllPages();

		if (allPages == null || CollectionUtils.isEmpty(allPages.getSonarIssues())) {
			return null;
		}

		return converter.convert(new Issues(), allPages);
	}

	private Page getAllPages() {
		boolean hasNextPage = true;
		int currentPage = 1;
		Page prevPage = null;
		Page head = null;

		while (hasNextPage) {
			String url = MessageFormat.format(URL_FORMAT, currentPage);
			Page page = issueCrawler.retrieveJsonContentFromUrl(url);

			if (page == null) {
				return head;
			}

			if (prevPage == null) {
				head = page;
			} else {
				prevPage.setNextPage(page);
			}

			PageMetaData pageMetaData = page.getPageMetaData();
			if (pageMetaData == null || currentPage == pageMetaData.getPages()) {
				hasNextPage = false;
			}

			prevPage = page;

			currentPage++;
		}

		return head;
	}
}
