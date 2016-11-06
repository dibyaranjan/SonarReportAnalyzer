package com.dibya.sonar.converter;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dibya.sonar.entity.vo.SourceTargetValue;

@Service
public class ConverterFactory {
	
	@Autowired
    private HashMap<SourceTargetValue, Converter> converterRegistry;
    
    public void setConverterRegistry(HashMap<SourceTargetValue, Converter> converterRegistry) {
		this.converterRegistry = converterRegistry;
	}

    public Converter getConverter(SourceTargetValue stv) {
        Converter converter = converterRegistry.get(stv);
        if (converter == null) {
            throw new IllegalArgumentException(
                    "Converter not registered to convert " + stv.getTarget() + " from " + stv.getSource());
        }

        return converter;
    }
}
