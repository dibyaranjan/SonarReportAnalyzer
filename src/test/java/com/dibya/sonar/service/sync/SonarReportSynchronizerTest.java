package com.dibya.sonar.service.sync;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dibya.infra.converter.Converter;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.builder.IssueEntityBuilder;
import com.dibya.sonar.entity.builder.PageBuilder;
import com.dibya.sonar.entity.vo.Issue;
import com.dibya.sonar.entity.vo.Page;
import com.dibya.sonar.entity.vo.wrapper.SourceFileListWrapper;
import com.dibya.sonar.json.crawler.JsonCrawler;

public class SonarReportSynchronizerTest {
    private SonarReportSynchronizer synchronizer;
    private JsonCrawler<Page> jsonCrawler;
    private PageBuilder pageBuilder;
    private Converter converter;
    private IssueEntityBuilder issueEntityBuilder;
    private List<com.dibya.sonar.entity.Issue> issues;
    private SourceFileListWrapper sourceFileListWrapper;

    private Mockery mockery;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        mockery = new JUnit4Mockery() {{
            setThreadingPolicy(new Synchroniser());
        }};
        
        pageBuilder = new PageBuilder();
        issueEntityBuilder = new IssueEntityBuilder();

        jsonCrawler = mockery.mock(JsonCrawler.class);
        converter = mockery.mock(Converter.class);

        synchronizer = new SonarReportSynchronizer();
        synchronizer.setConverter(converter);
        synchronizer.setIssueCrawler(jsonCrawler);
        
        issues = new ArrayList<>();
        // Building 9 issue-entities
        for (int i = 0; i < 9; i++) {
            issues.add(issueEntityBuilder.build());
        }
        
        SourceFile sourceFile = new SourceFile();
        sourceFile.setIssues(issues);
        
        List<SourceFile> sourceFiles = new ArrayList<>();
        sourceFiles.add(sourceFile);
        
        sourceFileListWrapper = new SourceFileListWrapper();
        sourceFileListWrapper.setSourceFiles(sourceFiles);
    }

    @After
    public void tearDown() {
        synchronizer = null;
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSyncWithInvalidUrl() {
        mockery.checking(new Expectations() {
            {
                oneOf(jsonCrawler).retrieveJsonContentFromUrl(with(any(String.class)));
                will(throwException(new IllegalArgumentException("Invalid URL!")));
            }
        });
        
        synchronizer.sync();
    }

    @Test
    public void testSyncWithNullPageReturned() {
        mockery.checking(new Expectations() {
            {
                oneOf(jsonCrawler).retrieveJsonContentFromUrl(with(any(String.class)));
                will(returnValue(null));
            }
        });

        boolean synced = synchronizer.sync();

        Assert.assertFalse("Sync should have failed", synced);
    }
    
    @Test
    public void testSyncWithEmptyIssues() {
        mockery.checking(new Expectations() {
            {
                oneOf(jsonCrawler).retrieveJsonContentFromUrl(with(any(String.class)));
                will(returnValue(new Page()));
            }
        });

        boolean synced = synchronizer.sync();

        Assert.assertFalse("Sync should have failed", synced);
    }
    
    
    
    public void testSyncWithNoIssuesPersisted() {
        mockery.checking(new Expectations() {
            {
                oneOf(jsonCrawler).retrieveJsonContentFromUrl(with(any(String.class)));
                will(returnValue(pageBuilder.build()));

                oneOf(jsonCrawler).retrieveJsonContentFromUrl(with(any(String.class)));
                will(returnValue(pageBuilder.build()));

                oneOf(jsonCrawler).retrieveJsonContentFromUrl(with(any(String.class)));
                will(returnValue(pageBuilder.build()));

                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(0)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(1)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(2)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(3)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(4)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(5)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(6)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(7)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(8)));
            }
        });

        boolean synced = synchronizer.sync();
        
        mockery.assertIsSatisfied();

        Assert.assertTrue("Sync should have failed", synced);
    }
    
    public void testSyncWithAllIssuesAlreadyPersistedInDb() {
        mockery.checking(new Expectations() {
            {
                oneOf(jsonCrawler).retrieveJsonContentFromUrl(with(any(String.class)));
                will(returnValue(pageBuilder.build()));

                oneOf(jsonCrawler).retrieveJsonContentFromUrl(with(any(String.class)));
                will(returnValue(pageBuilder.build()));

                oneOf(jsonCrawler).retrieveJsonContentFromUrl(with(any(String.class)));
                will(returnValue(pageBuilder.build()));

                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(0)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(1)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(2)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(3)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(4)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(5)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(6)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(7)));
                
                oneOf(converter).convert(with(any(com.dibya.sonar.entity.Issue.class)), with(any(Issue.class)));
                will(returnValue(issues.get(8)));
            }
        });

        boolean synced = synchronizer.sync();
        
        mockery.assertIsSatisfied();

        Assert.assertTrue("Sync should have failed", synced);
    }

}
