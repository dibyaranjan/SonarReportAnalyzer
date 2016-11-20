package com.dibya.sonar.configuration;

import java.util.Map;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.dibya.infra.converter.Converter;
import com.dibya.infra.converter.scanner.ConverterScanner;
import com.dibya.infra.converter.vo.SourceTargetValue;

/**
 * Configuration call which injects a Map of Source, Target pair and the
 * corresponding converter to the application context by scanning the base
 * package provided.
 * 
 * @author Dibya
 */
@Configuration
public class ConverterBeans {

	@Bean(name = "converterRegistry")
	public Map<SourceTargetValue, Converter> getConverterRegistry() {
		ConverterScanner converterScanner = new ConverterScanner();
		return converterScanner.scanConvertersFromPackage("com.dibya.sonar.converter.adapter");
	}
}
