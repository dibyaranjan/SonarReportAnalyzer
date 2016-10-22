package com.dibya.sonar.dao.impl.hibernate.it;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dibya.sonar.configuration.SourceFilePersisterItConfiguration;
import com.dibya.sonar.dao.SourceFilePersister;
import com.dibya.sonar.dao.helper.SourceFilePersisterTestHelper;
import com.dibya.sonar.dao.impl.hibernate.SourceFilePersisterImpl;
import com.dibya.sonar.entity.Issue;
import com.dibya.sonar.entity.ScmDetail;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.builder.IssueEntityBuilder;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SourceFilePersisterTestHelper.class, SourceFilePersisterItConfiguration.class,
        SourceFilePersisterImpl.class })
public class SourceFilePersisterImplIt {
    @Autowired
    private SourceFilePersister persister;

    @Autowired
    private SourceFilePersisterTestHelper testHelper;

    private SourceFile sourceFile;

    @Before
    public void setUp() {
        testHelper.executeBeforeTest();

        sourceFile = new SourceFile();

        List<Issue> issues = new ArrayList<>();
        Issue issue = (new IssueEntityBuilder()).build();
        issues.add(issue);
        ArrayList<ScmDetail> scmDetails = new ArrayList<>();
        ScmDetail scmDetail = new ScmDetail();
        Date dateIntroduced = new Date();
        scmDetail.setDateIntroduced(dateIntroduced);
        scmDetail.setEmail("dibya.panda@mindtree.com");
        scmDetail.setLineNumber(10);
        scmDetails.add(scmDetail);

        sourceFile.setName("File 1");
        sourceFile.setIssues(issues);
        sourceFile.setScmDetails(scmDetails);
    }

    @After
    public void tearDown() {
        testHelper.executeAfterTest();
        sourceFile = null;
    }

    @Test
    public void testSaveSourceFile() {
        persister.save(sourceFile);

        Assert.assertEquals("Id should be 2", 2, sourceFile.getId());
    }

    @Test
    public void testGetAllSourceFile() {
        List<SourceFile> allSourceFiles = persister.getAllSourceFiles();
        Assert.assertFalse("The sourceFile can not be empty ", allSourceFiles.isEmpty());
        Assert.assertEquals("The size of the list should be 1", 1, allSourceFiles.size());

        Assert.assertFalse("Issues must not be empty", allSourceFiles.get(0).getIssues().isEmpty());
        Assert.assertFalse("SCM details must not be empty", allSourceFiles.get(0).getScmDetails().isEmpty());
    }
    
    @Test
    public void testLoadAllSourceFilesEagerly() {
        List<SourceFile> allSourceFiles = persister.loadAllSourceFilesEagerly();
        Assert.assertFalse("The sourceFile can not be empty ", allSourceFiles.isEmpty());
        Assert.assertEquals("The size of the list should be 1", 1, allSourceFiles.size());

        Assert.assertFalse("Issues must not be empty", allSourceFiles.get(0).getIssues().isEmpty());
        Assert.assertFalse("SCM details must not be empty", allSourceFiles.get(0).getScmDetails().isEmpty());
    }
    
    @Test
    public void testLoadSessionLessSourceFilesEagerly() {
        List<SourceFile> allSourceFiles = persister.loadSessionLessSourceFilesEagerly();
        Assert.assertFalse("The sourceFile can not be empty ", allSourceFiles.isEmpty());
        Assert.assertEquals("The size of the list should be 1", 1, allSourceFiles.size());

        Assert.assertFalse("Issues must not be empty", allSourceFiles.get(0).getIssues().isEmpty());
        Assert.assertFalse("SCM details must not be empty", allSourceFiles.get(0).getScmDetails().isEmpty());
    }
}

