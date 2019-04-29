package com.excilys.computer_database.persistence_config_test;

import java.util.TimeZone;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableJpaRepositories(basePackages = "com.excilys.computer_database.dao")
@PropertySource(value = { "classpath:dataSourceTest.properties" })
public class SpringPersistenceConfigurationTest {
	
	static {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}

	@Bean
	public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
		JpaTransactionManager txManager = new JpaTransactionManager();
		txManager.setEntityManagerFactory(entityManagerFactory);
		return txManager;
	}
	
	@Bean
	public LocalContainerEntityManagerFactoryBean entityManagerFactory(HikariDataSource dataSource) {
		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	    vendorAdapter.setGenerateDdl(true);
		LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
		em.setDataSource(dataSource);
		em.setPackagesToScan(new String[] { "com.excilys.computer_database" });
		em.setJpaVendorAdapter(vendorAdapter);
		return em;
	}
	
	@Bean
    public HikariDataSource dataSource(Environment environement) {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setJdbcUrl(environement.getRequiredProperty("jdbcUrl"));
		dataSource.setUsername(environement.getRequiredProperty("db.username"));
		dataSource.setPassword(environement.getRequiredProperty("db.password"));
		dataSource.setDriverClassName(environement.getRequiredProperty("driverClassName"));
        return dataSource;
    }
}
