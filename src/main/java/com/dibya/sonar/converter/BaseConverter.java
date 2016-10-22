package com.dibya.sonar.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dibya.sonar.entity.vo.SourceTargetValue;

@Service
public class BaseConverter implements Converter {
    @Autowired
    private ConverterFactory converterFactory;
    
    @Override
    public <T, S> T convert(T target, S source) {
        SourceTargetValue stv = new SourceTargetValue(source.getClass(), target.getClass());

        Converter converter = converterFactory.getConverter(stv);

        if (converter instanceof AbstractConverter) {
            AbstractConverter abstractConverter = (AbstractConverter) converter;
            abstractConverter.setConverter(this);
        }

        return (T) converter.convert(target, source);
    }
}
