package com.dibya.sonar.converter.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.jmock.lib.concurrent.Synchroniser;
import org.jmock.lib.legacy.ClassImposteriser;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.dibya.infra.converter.AbstractConverter;
import com.dibya.infra.converter.Converter;
import com.dibya.sonar.entity.Issue;
import com.dibya.sonar.entity.ScmDetail;
import com.dibya.sonar.entity.SeverityType;
import com.dibya.sonar.entity.SonarRule;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.StatusType;
import com.dibya.sonar.entity.vo.Resource;
import com.dibya.sonar.entity.vo.ViolationDetails;

public class ViolationDetailsFromSourceFileConverterTest {
	private AbstractConverter converter;
	private SourceFile source;
	private ViolationDetails target;

	private Mockery mockery;
	private Converter mockConverter;

	@Before
	public void setUp() {
		mockery = new JUnit4Mockery() {
			{
				setImposteriser(ClassImposteriser.INSTANCE);
				setThreadingPolicy(new Synchroniser());
			}
		};

		mockConverter = mockery.mock(Converter.class);

		converter = new ViolationDetailsFromSourceFileConverter();
		converter.setConverter(mockConverter);
	}

	@After
	public void tearDown() {
		mockery.assertIsSatisfied();
		mockery = null;
		source = null;
		target = null;
	}

	@Test
	public void testConvertWithNullSource() {
		target = converter.convert(new ViolationDetails(), null);
		Assert.assertNotNull("Target should not be null", target);
	}

	@Test
	public void testConvertWithNullTarget() {
		target = converter.convert(null, new SourceFile());
		Assert.assertNull("Target should be null", target);
	}

	@Test
	public void testConvertWithNullSourceAndTarget() {
		target = converter.convert(null, null);
		Assert.assertNull("Target should be null", target);
	}

	@Test
	public void testConvertWithNoIssues() {
		source = new SourceFile();
		source.setId(1);
		source.setName("File1");
		List<ScmDetail> scmDetails = new ArrayList<ScmDetail>();
		ScmDetail scmLine1 = new ScmDetail();
		scmLine1.setId(1);
		scmLine1.setLineNumber(1);
		scmLine1.setEmail("test@gmail.com");
		scmLine1.setDateIntroduced(new Date());
		scmDetails.add(scmLine1);
		source.setScmDetails(scmDetails);

		target = converter.convert(new ViolationDetails(), source);

		Assert.assertNull("Target should be null", null);
	}

	@Test
	public void testConverterWithIssuesNoScm() {
		mockery.checking(new Expectations() {
			{
				exactly(2).of(mockConverter).convert(with(any(Resource.class)), with(any(SourceFile.class)));
				will(returnValue(null));
			}
		});

		SonarRule rule = new SonarRule();
		rule.setRuleId("SQUID-007");

		SeverityType severity = SeverityType.MAJOR;
		StatusType status = StatusType.OPEN;

		Issue issue1 = createIssue("KEY1", "Sonar", "Remove commented out code", 2, rule, severity, status);
		Issue issue2 = createIssue("KEY2", "Sonar", "Remove commented out code", 10, rule, severity, status);

		List<Issue> issues = new ArrayList<Issue>();
		issues.add(issue1);
		issues.add(issue2);

		source = new SourceFile();
		source.setId(1);
		source.setIssues(issues);

		target = converter.convert(new ViolationDetails(), source);

		Assert.assertNotNull("Should not be null", target);
	}

	@Test
	public void testConverterWithIssuesSingleScm() {
		mockery.checking(new Expectations() {
			{
				exactly(2).of(mockConverter).convert(with(any(Resource.class)), with(any(SourceFile.class)));
				will(returnValue(null));
			}
		});

		SonarRule rule = new SonarRule();
		rule.setRuleId("SQUID-007");

		SeverityType severity = SeverityType.MAJOR;
		StatusType status = StatusType.OPEN;

		Issue issue1 = createIssue("KEY1", "Sonar", "Remove commented out code", 2, rule, severity, status);
		Issue issue2 = createIssue("KEY2", "Sonar", "Remove commented out code", 10, rule, severity, status);

		List<Issue> issues = new ArrayList<Issue>();
		issues.add(issue1);
		issues.add(issue2);

		ScmDetail scmDetail = createScmDetails("test@gmail.com", 1, 1);

		List<ScmDetail> scmDetails = new ArrayList<ScmDetail>();
		scmDetails.add(scmDetail);

		source = new SourceFile();
		source.setId(1);
		source.setIssues(issues);
		source.setScmDetails(scmDetails);

		target = converter.convert(new ViolationDetails(), source);

		Assert.assertNotNull("Should not be null", target);
		Assert.assertTrue("The size of the issues should be 2", 2 == getSizeOfLinkedList(target));
	}

	@Test
	public void testConverterWithIssuesTwoScm() {
		mockery.checking(new Expectations() {
			{
				exactly(2).of(mockConverter).convert(with(any(Resource.class)), with(any(SourceFile.class)));
				will(returnValue(null));
			}
		});

		SonarRule rule = new SonarRule();
		rule.setRuleId("SQUID-007");

		SeverityType severity = SeverityType.MAJOR;
		StatusType status = StatusType.OPEN;

		Issue issue1 = createIssue("KEY1", "Sonar", "Remove commented out code", 2, rule, severity, status);
		Issue issue2 = createIssue("KEY2", "Sonar", "Remove commented out code", 10, rule, severity, status);

		List<Issue> issues = new ArrayList<Issue>();
		issues.add(issue1);
		issues.add(issue2);

		ScmDetail scmDetail1 = createScmDetails("test@gmail.com", 1, 1);
		ScmDetail scmDetail2 = createScmDetails("test@gmail.com", 2, 10);

		List<ScmDetail> scmDetails = new ArrayList<ScmDetail>();
		scmDetails.add(scmDetail1);
		scmDetails.add(scmDetail2);

		source = new SourceFile();
		source.setId(1);
		source.setIssues(issues);
		source.setScmDetails(scmDetails);

		target = converter.convert(new ViolationDetails(), source);

		Assert.assertNotNull("Should not be null", target);
		Assert.assertTrue("The size of the issues should be 2", 2 == getSizeOfLinkedList(target));
	}

	private Issue createIssue(String key, String project, String message, int line, SonarRule rule,
			SeverityType severity, StatusType status) {
		Issue issue = new Issue();
		issue.setKey(key);
		issue.setLine(line);
		issue.setMessage(message);
		issue.setProject(project);
		issue.setRule(rule);
		issue.setSeverity(severity);
		issue.setStatus(status);
		return issue;
	}

	private ScmDetail createScmDetails(String email, int id, int lineNumber) {
		ScmDetail scmDetail = new ScmDetail();
		scmDetail.setDateIntroduced(new Date());
		scmDetail.setEmail(email);
		scmDetail.setId(id);
		scmDetail.setLineNumber(lineNumber);
		return scmDetail;
	}

	private int getSizeOfLinkedList(ViolationDetails violationDetails) {
		ViolationDetails pointer = violationDetails;
		int size = 0;
		do {
			size++;
			pointer = pointer.getNext();
		} while (pointer != null);

		return size;
	}
}
