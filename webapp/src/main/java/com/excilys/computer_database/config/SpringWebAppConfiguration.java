package com.excilys.computer_database.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.computer_database.service.SpringServiceConfiguration;

@Configuration
@Import({SpringServiceConfiguration.class})
@ComponentScan({"com.excilys.computer_database.servlet"})
public class SpringWebAppConfiguration {

}