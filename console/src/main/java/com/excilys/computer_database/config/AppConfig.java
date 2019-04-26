package com.excilys.computer_database.app;

import java.util.TimeZone;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.excilys.computer_database.persistence")
@PropertySource("classpath:dataSource.properties")
@ComponentScan({"com.excilys.computer_database.controller", 
				"com.excilys.computer_database.service"})
public class AppConfig {
	
	@Autowired
	Environment env;

	static {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	@Bean(destroyMethod = "close")
	public DataSource DataSource() {
		DataSource dataSource = DataSourceBuilder
				.create()
				.url(env.getProperty("jdbcUrl"))
				.driverClassName(env.getProperty("driverClassName"))
				.username(env.getProperty("db.username"))
				.password(env.getProperty("db.password"))
				.build();
		return dataSource;
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);
		LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
		entityManager.setDataSource(dataSource);
		entityManager.setPackagesToScan(new String[] { "com.excilys.computer_database.model" });
		entityManager.setJpaVendorAdapter(vendorAdapter);
		return entityManager;
	}
}
