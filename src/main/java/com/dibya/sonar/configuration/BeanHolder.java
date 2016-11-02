package com.dibya.sonar.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.dibya.sonar.cache.ViolationDetailsCache;
import com.dibya.sonar.cache.SourceFileCache;
import com.dibya.sonar.converter.BaseConverter;
import com.dibya.sonar.converter.ConverterFactory;
import com.dibya.sonar.dao.SourceFilePersister;
import com.dibya.sonar.dao.impl.hibernate.StatisticsPersisterImpl;
import com.dibya.sonar.service.sync.SonarReportSynchronizer;

@Configuration
@ComponentScan(basePackages = { "com.dibya.sonar.controller",
        "com.dibya.sonar.service.impl.cache" }, basePackageClasses = { SonarReportSynchronizer.class,
                StatisticsPersisterImpl.class, BaseConverter.class, ConverterFactory.class })
public class BeanHolder {
    @Autowired
    private SourceFilePersister persister;

    @Autowired
    private BaseConverter baseConverter;

    @Bean
    @Scope(value = "singleton")
    public SourceFileCache getSonarReportCache() {
        SourceFileCache sonarReportCache = new SourceFileCache();
        sonarReportCache.setPersister(persister);
        sonarReportCache.initializeCache();
        return sonarReportCache;
    }

    @Bean
    @Scope(value = "singleton")
    public ViolationDetailsCache getBlameDetailCache() {
        ViolationDetailsCache blameDetailCache = new ViolationDetailsCache();
        blameDetailCache.setPersister(persister);
        blameDetailCache.setConverter(baseConverter);
        blameDetailCache.setSourceFileCache(getSonarReportCache());
        blameDetailCache.initializeCache();

        return blameDetailCache;
    }
}
