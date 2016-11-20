package com.dibya.sonar.converter.adapter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.dibya.infra.converter.AbstractConverter;
import com.dibya.infra.converter.annotation.Convert;
import com.dibya.sonar.entity.vo.Issue;
import com.dibya.sonar.entity.vo.Issues;
import com.dibya.sonar.entity.vo.Page;
import com.dibya.sonar.entity.vo.Project;
import com.dibya.sonar.entity.vo.SonarRuleVo;

@Convert(source = com.dibya.sonar.entity.vo.Page.class, target = com.dibya.sonar.entity.vo.Issues.class)
public class IssuesFromPageConverter extends AbstractConverter {
	private static final Logger LOGGER = Logger.getLogger(IssuesFromPageConverter.class);

	public IssuesFromPageConverter() {
		LOGGER.info("Registered " + getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T, S> T doConvert(S sourceObject) {
		Page source = (Page) sourceObject;
		Page head = source;

		Map<String, SonarRuleVo> ruleTable = new LinkedHashMap<>();

		Issues target = null;
		Issues currentIssue = null;

		while (head != null) {
			List<SonarRuleVo> rules = head.getRules();
			for (SonarRuleVo sonarRule : rules) {
				String key = sonarRule.getKey();
				if (ruleTable.get(key) == null) {
					ruleTable.put(key, sonarRule);
				}
			}

			List<Issue> sonarIssues = head.getSonarIssues();
			for (Issue issue : sonarIssues) {
				Issues issueVo = converter.convert(new Issues(), issue);

				Project project = new Project();
				issueVo.setProject(project);

				issueVo.setRule(ruleTable.get(issue.getRuleName()));

				if (currentIssue == null) {
					target = issueVo;
					currentIssue = issueVo;
				} else {
					currentIssue.setIssueVo(issueVo);
					currentIssue = currentIssue.getIssueVo();
				}
			}
			head = head.getNextPage();
		}

		return (T) target;
	}
}
