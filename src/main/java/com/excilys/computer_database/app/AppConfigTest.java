package com.excilys.computer_database.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:dataSourceTest.properties")
@ComponentScan({ "com.excilys.computer_database.service", "com.excilys.computer_database.persistence" })
public class AppConfigTest {
	
	@Autowired
    Environment env;
	
	@Bean(destroyMethod = "close")
	public DataSource DataSource() {
		 DataSource dataSource = DataSourceBuilder
				 .create()
				 .url(env.getProperty("jdbcUrl"))
				 .driverClassName(env.getProperty("driverClassName"))
				 .username(env.getProperty("login"))
				 .password(env.getProperty("password"))
				 .build();
		 return dataSource;
	}
}