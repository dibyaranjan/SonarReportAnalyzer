package com.dibya.sonar.controller.it;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dibya.sonar.configuration.AspectConfiguration;
import com.dibya.sonar.configuration.BeanHolder;
import com.dibya.sonar.configuration.ConverterBeans;
import com.dibya.sonar.configuration.SourceFilePersisterItConfiguration;
import com.dibya.sonar.controller.SyncIssueController;
import com.dibya.sonar.converter.BaseConverter;
import com.dibya.sonar.converter.ConverterFactory;
import com.dibya.sonar.dao.impl.hibernate.SourceFilePersisterImpl;
import com.dibya.sonar.entity.vo.GenericJsonObject;
import com.dibya.sonar.service.sync.SonarReportSynchronizer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { SyncIssueController.class, SonarReportSynchronizer.class, BaseConverter.class,
		ConverterFactory.class, ConverterFactory.class,
		SourceFilePersisterItConfiguration.class, AspectConfiguration.class, SourceFilePersisterImpl.class,
		ConverterBeans.class, BeanHolder.class })
public class SyncIssueControllerIt {

	@Autowired
	private SyncIssueController controller;
	
	@Test
	public void testSync() {
		GenericJsonObject syncIssues = controller.syncIssues();
		Assert.assertEquals("Sync should be successful", true, syncIssues.isSuccessful());
		Assert.assertTrue("Sync was successful should be contained",
				syncIssues.getMessage().startsWith("Operation was successful "));
	}

	/**
	 * Duplicate sync request should not insert any data to the database unless
	 * updated. If we try to insert duplicate data, as there is a unique
	 * constraint on the file name, sync will not be successful.
	 * 
	 * If this testcase does not throw any exception that means duplicate
	 * records are not inserted to the database.
	 * 
	 * !!This testcase will try to get the JSON from the URL twice hence runs
	 * slow!!
	 */
	@Test
	public void testSyncWithDuplicateData() {
		GenericJsonObject syncIssues = controller.syncIssues();
		Assert.assertEquals("Sync should be successful", true, syncIssues.isSuccessful());
		Assert.assertTrue("Sync was successful should be contained",
				syncIssues.getMessage().startsWith("Operation was successful "));

		syncIssues = controller.syncIssues();
		Assert.assertEquals("Sync should be successful", true, syncIssues.isSuccessful());
		Assert.assertTrue("Sync was successful should be contained",
				syncIssues.getMessage().startsWith("Operation was successful "));
	}
}
