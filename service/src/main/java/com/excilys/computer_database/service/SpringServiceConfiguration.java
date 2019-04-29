package com.excilys.computer_database.service;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.computer_database.dao.SpringPersistenceConfiguration;

@Configuration
@Import({SpringPersistenceConfiguration.class})
@ComponentScan({"com.excilys.computer_database.service"})
public class SpringServiceConfiguration {
	
}