package com.excilys.computer_database.service_config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.computer_database.persistence_config.SpringPersistenceConfiguration;

@Configuration
@Import({SpringPersistenceConfiguration.class})
@ComponentScan({"com.excilys.computer_database.service"})
public class SpringServiceConfiguration {
	
}