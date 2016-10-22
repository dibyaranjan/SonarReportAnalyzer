package com.dibya.sonar.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.dibya.sonar.controller.aop.ExceptionHandlerAspect;

@Configuration
@EnableAspectJAutoProxy
public class AspectConfiguration {
    @Bean
    public ExceptionHandlerAspect exceptionAspect() {
        return new ExceptionHandlerAspect();
    }
}
