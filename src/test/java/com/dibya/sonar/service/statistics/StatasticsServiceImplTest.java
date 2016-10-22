package com.dibya.sonar.service.statistics;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dibya.sonar.cache.SourceFileCache;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.builder.SourceFileListBuilder;
import com.dibya.sonar.entity.vo.ViolationsByUser;
import com.dibya.sonar.service.impl.cache.StatasticsServiceImpl;

public class StatasticsServiceImplTest {
    private Mockery mockery;
    private StatasticsServiceImpl service;
    private SourceFileCache cache;
    private Map<String, SourceFile> sourceFiles;

    @Before
    public void setUp() {
        mockery = new JUnit4Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
                setThreadingPolicy(new Synchroniser());
            }
        };

        cache = mockery.mock(SourceFileCache.class);

        service = new StatasticsServiceImpl();
        service.setCache(cache);

        List<SourceFile> sources = new SourceFileListBuilder().build();
        sourceFiles = new HashMap<>();
        for (SourceFile sourceFile : sources) {
            sourceFiles.put(sourceFile.getName(), sourceFile);
        }

    }

    @After
    public void tearDown() {
        mockery = null;

    }

    @Test
    public void testGetViolationsForAllDevelopers() {
        mockery.checking(new Expectations() {
            {
                oneOf(cache).getAllSourceFilesFromCache();
                will(returnValue(sourceFiles));
            }
        });
        List<ViolationsByUser> violations = service.getViolationsForAllDevelopers();

        Assert.assertNotNull("Violations should not be null", violations);
        Assert.assertFalse("Violations should not be empty", violations.isEmpty());
    }
}
