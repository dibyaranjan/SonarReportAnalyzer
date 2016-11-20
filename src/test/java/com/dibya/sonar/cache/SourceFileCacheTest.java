package com.dibya.sonar.cache;

import java.util.ArrayList;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.dibya.sonar.dao.SourceFilePersister;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.builder.SourceFileBuilder;

public class SourceFileCacheTest {
    private SourceFileCache cache;
    private Mockery mockery;
    private SourceFilePersister persister;
    private List<SourceFile> sourceFiles = new ArrayList<SourceFile>();

    @Before
    public void setUp() {
        mockery = new JUnit4Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
                setThreadingPolicy(new Synchroniser());
            }
        };
        persister = mockery.mock(SourceFilePersister.class);

        cache = new SourceFileCache();
        cache.setPersister(persister);
        SourceFileBuilder builder = new SourceFileBuilder();

        sourceFiles.add(builder.build());
        sourceFiles.add(builder.build());
        sourceFiles.add(builder.build());
    }

    @After
    public void tearDown() {
        mockery.assertIsSatisfied();
        cache = null;
        mockery = null;
        sourceFiles = null;
    }

    @Test
    public void testCacheWithEmptyRows() {
        mockery.checking(new Expectations() {
            {
                oneOf(persister).loadAllSourceFilesEagerly();
                will(returnValue(new ArrayList<SourceFile>()));
            }
        });

        cache.getAllSourceFilesFromCache();
    }

    @Test
    public void testCacheWithRows() {
        mockery.checking(new Expectations() {
            {
                oneOf(persister).loadAllSourceFilesEagerly();
                will(returnValue(sourceFiles));
            }
        });

        cache.getAllSourceFilesFromCache();
    }
    
    @Test
    public void testCacheRefresh() {
        mockery.checking(new Expectations() {
            {
                oneOf(persister).loadAllSourceFilesEagerly();
                will(returnValue(sourceFiles));
            }
        });

        cache.getAllSourceFilesFromCache();
    }

}
