package com.dibya.sonar.converter.adapter;

import org.apache.log4j.Logger;

import com.dibya.infra.converter.AbstractConverter;
import com.dibya.infra.converter.annotation.Convert;
import com.dibya.sonar.entity.SonarRule;
import com.dibya.sonar.entity.vo.SonarRuleVo;

@Convert(source = com.dibya.sonar.entity.vo.SonarRuleVo.class, target = com.dibya.sonar.entity.SonarRule.class)
public class SonarRuleFromSonarRuleVoConverter extends AbstractConverter {
	private static final Logger LOGGER = Logger.getLogger(SonarRuleFromSonarRuleVoConverter.class);

	public SonarRuleFromSonarRuleVoConverter() {
		LOGGER.info("Registered " + getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T, S> T doConvert(S sourceObject) {
		SonarRuleVo source = (SonarRuleVo) sourceObject;
		SonarRule target = new SonarRule();
		target.setDescription(source.getDesc());
		target.setRuleId(source.getKey());
		target.setRuleName(source.getName());
		return (T) target;
	}

}
