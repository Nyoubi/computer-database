package com.excilys.computer_database.app;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
@PropertySource("classpath:dataSource.properties")
@ComponentScan({ "com.excilys.computer_database.service", "com.excilys.computer_database.persistence" })
public class AppConfig {
	
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
	
	  @Bean
	  public JdbcTemplate JdbcTemplate() {
	    JdbcTemplate jdbcTemplate = new JdbcTemplate(DataSource());
	    return jdbcTemplate;
	  }
	
}
