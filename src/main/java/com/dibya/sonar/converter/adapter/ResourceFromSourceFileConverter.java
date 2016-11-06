package com.dibya.sonar.converter.adapter;

import com.dibya.sonar.converter.AbstractConverter;
import com.dibya.sonar.entity.SourceFile;
import com.dibya.sonar.entity.vo.Resource;

/**
 * Converter to convert a SourceFile object to Resource Entity.
 * 
 * @author Dibya
 */
public class ResourceFromSourceFileConverter extends AbstractConverter {

	@SuppressWarnings("unchecked")
	@Override
	protected <T, S> T doConvert(S sourceObject) {
		SourceFile source = (SourceFile) sourceObject;
		Resource target = new Resource();
		target.setResourceId(source.getId());
		target.setResourceName(source.getName());
		return (T) target;
	}

}
