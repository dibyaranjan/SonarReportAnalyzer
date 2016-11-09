package com.dibya.sonar.configuration;

import static com.dibya.sonar.configuration.reader.PropertyKey.DIALECT;
import static com.dibya.sonar.configuration.reader.PropertyKey.FORMAT_SQL;
import static com.dibya.sonar.configuration.reader.PropertyKey.HBM_TO_DDL;
import static com.dibya.sonar.configuration.reader.PropertyKey.JDBC_DRIVER;
import static com.dibya.sonar.configuration.reader.PropertyKey.JDBC_URL;
import static com.dibya.sonar.configuration.reader.PropertyKey.PASSWORD;
import static com.dibya.sonar.configuration.reader.PropertyKey.SHOW_SQL;
import static com.dibya.sonar.configuration.reader.PropertyKey.USER_NAME;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;

import com.dibya.sonar.configuration.reader.PropertiesCache;
import com.dibya.sonar.entity.Issue;
import com.dibya.sonar.entity.ScmDetail;
import com.dibya.sonar.entity.SonarRule;
import com.dibya.sonar.entity.SourceFile;

@Configuration
public class HibernateConfiguration {
	@Bean(name = "sessionFactory")
	public LocalSessionFactoryBean sessionFactory() {
		LocalSessionFactoryBean sessionfactory = new LocalSessionFactoryBean();
		sessionfactory.setDataSource(dataSource());
		sessionfactory.setAnnotatedClasses(Issue.class, SourceFile.class, ScmDetail.class, SonarRule.class);
		sessionfactory.setHibernateProperties(hibernateProperties());
		return sessionfactory;
	}

	protected Properties hibernateProperties() {
		Properties properties = new Properties();
		properties.put("hibernate.dialect", PropertiesCache.getValue(DIALECT));
		properties.put("hibernate.hbm2ddl.auto", PropertiesCache.getValue(HBM_TO_DDL));
		properties.put("hibernate.show_sql", PropertiesCache.getValue(SHOW_SQL));
		properties.put("hibernate.format_sql", PropertiesCache.getValue(FORMAT_SQL));
		return properties;
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName(PropertiesCache.getValue(JDBC_DRIVER));
		dataSource.setUrl(PropertiesCache.getValue(JDBC_URL));
		dataSource.setUsername(PropertiesCache.getValue(USER_NAME));
		dataSource.setPassword(PropertiesCache.getValue(PASSWORD));
		return dataSource;
	}

	@Bean
	public DataSourceInitializer dataSourceInitializer(DataSource dataSource) {
		DataSourceInitializer dataSourceInitializer = new DataSourceInitializer();
		dataSourceInitializer.setDataSource(dataSource);
		ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
		dataSourceInitializer.setDatabasePopulator(databasePopulator);
		dataSourceInitializer.setEnabled(Boolean.parseBoolean("true"));

		return dataSourceInitializer;
	}
}
