package com.dibya.sonar.converter.adapter;

import org.apache.log4j.Logger;

import com.dibya.sonar.converter.AbstractConverter;

public class BlameDetailFromSourceFileConverter extends AbstractConverter {
    private static final Logger LOGGER = Logger.getLogger(BlameDetailFromSourceFileConverter.class);
    public BlameDetailFromSourceFileConverter() {
        LOGGER.info("Registered " + getClass());
    }

    @Override
    protected <T, S> T doConvert(S sourceObject) {
        
        return null;
    }
    
}
