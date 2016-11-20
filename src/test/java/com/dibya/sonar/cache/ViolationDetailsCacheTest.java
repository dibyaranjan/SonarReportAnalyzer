package com.dibya.sonar.cache;

import static org.jmock.AbstractExpectations.any;
import static org.jmock.AbstractExpectations.returnValue;

import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dibya.infra.converter.BaseConverter;
import com.dibya.sonar.dao.SourceFilePersister;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.builder.SourceFileBuilder;
import com.dibya.sonar.entity.vo.ViolationDetails;

public class ViolationDetailsCacheTest {
    private ViolationDetailsCache cache;
    private Mockery mockery;
    private SourceFileCache sourceFileCache;
    private SourceFilePersister persister;
    private List<SourceFile> sourceFiles;
    private BaseConverter converter;

    @Before
    public void setUp() {
        mockery = new JUnit4Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
                setThreadingPolicy(new Synchroniser());
            }
        };

        sourceFileCache = mockery.mock(SourceFileCache.class);
        persister = mockery.mock(SourceFilePersister.class);
        converter = mockery.mock(BaseConverter.class);

        SourceFileBuilder builder = new SourceFileBuilder();

        sourceFiles = new LinkedList<SourceFile>();
        sourceFiles.add(builder.build());
        sourceFiles.add(builder.build());
        sourceFiles.add(builder.build());

        cache = new ViolationDetailsCache();
        cache.setPersister(persister);
        cache.setSourceFileCache(sourceFileCache);
        cache.setConverter(converter);
    }

    @After
    public void tearDown() {
        cache = null;
    }

    @Test
    public void testWithNullMap() {
        cache.setSourceFileCache(null);
        Expectations expectations = new Expectations();
        expectations.atLeast(1).of(persister).loadAllSourceFilesEagerly();
        expectations.will(returnValue(null));
        mockery.checking(expectations);

        cache.initializeCache();
        List<ViolationDetails> allBlameDetails = cache.getAllBlameDetails();
        Assert.assertTrue("List must be empty", CollectionUtils.isEmpty(allBlameDetails));
    }

    @Test
    public void testWithEmptyList() {
        cache.setSourceFileCache(null);
        Expectations expectations = new Expectations();
        expectations.atLeast(1).of(persister).loadAllSourceFilesEagerly();
        expectations.will(returnValue(new LinkedList<SourceFile>()));
        mockery.checking(expectations);

        cache.initializeCache();
        List<ViolationDetails> allBlameDetails = cache.getAllBlameDetails();
        Assert.assertTrue("List must be empty", CollectionUtils.isEmpty(allBlameDetails));
    }

    @Test
    public void testWithNonEmptyList() {
        cache.setSourceFileCache(null);
        Expectations expectations = new Expectations();
        expectations.atLeast(1).of(persister).loadAllSourceFilesEagerly();
        expectations.will(returnValue(sourceFiles));

        ViolationDetails violationDetails1 = new ViolationDetails();
        ViolationDetails violationDetails2 = new ViolationDetails();
        ViolationDetails violationDetails3 = new ViolationDetails();

        expectations.oneOf(converter).convert(expectations.with(any(ViolationDetails.class)),
                expectations.with(any(SourceFile.class)));
        expectations.will(returnValue(violationDetails1));

        expectations.oneOf(converter).convert(expectations.with(any(ViolationDetails.class)),
                expectations.with(any(SourceFile.class)));
        expectations.will(returnValue(violationDetails2));

        expectations.oneOf(converter).convert(expectations.with(any(ViolationDetails.class)),
                expectations.with(any(SourceFile.class)));
        expectations.will(returnValue(violationDetails3));

        mockery.checking(expectations);

        cache.initializeCache();
        List<ViolationDetails> allBlameDetails = cache.getAllBlameDetails();
        Assert.assertTrue("List must not be empty", CollectionUtils.isNotEmpty(allBlameDetails));
        Assert.assertEquals("Size of the list should be 3", 3, allBlameDetails.size());
    }

}
