package com.dibya.sonar.converter.adapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.dibya.infra.converter.AbstractConverter;
import com.dibya.infra.converter.annotation.Convert;
import com.dibya.sonar.entity.ScmDetail;
import com.dibya.sonar.entity.vo.ScmDetails;
import com.dibya.sonar.entity.vo.wrapper.ScmDetailListWrapper;

/**
 * 
 * 
 * @author Dibya Ranjan
 */
@Convert(source = com.dibya.sonar.entity.vo.ScmDetails.class, target = com.dibya.sonar.entity.vo.wrapper.ScmDetailListWrapper.class)
public class ScmDetailListWrapperFromScmDetailsConverter extends AbstractConverter {
	private static final Logger LOGGER = Logger.getLogger(ScmDetailListWrapperFromScmDetailsConverter.class);

	public ScmDetailListWrapperFromScmDetailsConverter() {
		LOGGER.info("Registered " + getClass());
	}

	@SuppressWarnings("unchecked")
	@Override
	protected <T, S> T doConvert(S sourceObject) {
		ScmDetails source = (ScmDetails) sourceObject;
		List<ScmDetail> scmDetails = extractScmDetails(source);

		ScmDetailListWrapper target = new ScmDetailListWrapper();
		target.setScmDetails(scmDetails);

		return (T) target;
	}

	private List<ScmDetail> extractScmDetails(ScmDetails source) {
		List<List<String>> scm = source.getScm();
		DateTimeFormatter fomatter = DateTimeFormat.forPattern("yyyy-MM-dd");

		List<ScmDetail> scms = new ArrayList<>();
		for (List<String> list : scm) {
			ScmDetail scmDetail = new ScmDetail();
			Integer lineNumber = Integer.valueOf(list.get(0));
			String email = list.get(1);
			String introducedDate = list.get(2);

			Date dateIntroduced = fomatter.parseDateTime(introducedDate).toDate();

			scmDetail.setDateIntroduced(dateIntroduced);
			scmDetail.setEmail(email);
			scmDetail.setLineNumber(lineNumber);

			scms.add(scmDetail);
		}

		return scms;
	}

}
