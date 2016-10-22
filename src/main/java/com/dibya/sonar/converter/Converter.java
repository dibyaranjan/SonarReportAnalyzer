package com.dibya.sonar.converter;

/**
 * "Converters" are the classes which 
 *
 * @author Dibya Ranjan
 */
public interface Converter {
    public <T, S> T convert(T target, S source);
}
