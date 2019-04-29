package com.excilys.computer_database.console_config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import com.excilys.computer_database.service_config.SpringServiceConfiguration;

@Configuration
@Import({SpringServiceConfiguration.class})
@ComponentScan({"com.excilys.computer_database.controller"})
public class SpringConsoleConfiguration {

}