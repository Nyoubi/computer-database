package com.excilys.computer_database.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.persistence.DaoCompany;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;
import com.excilys.computer_database.controler.ControlerCli;

@Configuration
public class AppConfig {
	
	@Bean
	public HikariConfig HikariConfig() {
		return new HikariConfig("/dataSource.properties");
	}

	@Bean
	public HikariDataSource HikariDataSource() {
		return new HikariDataSource(HikariConfig());
	}
	
	@Bean
	public DaoComputer DaoComputer() {
		return new DaoComputer(HikariDataSource());
	}
	
	@Bean
	public DaoCompany DaoCompany() {
		return new DaoCompany(HikariDataSource());
	}
	
	@Bean
	public ComputerService ComputerService() {
		return new ComputerService(DaoComputer(),CompanyService());
	}
	
	@Bean
	public CompanyService CompanyService() {
		return new CompanyService(DaoCompany());
	}
	
	@Bean
	public ControlerCli ControlerCli() {
		return new ControlerCli(CompanyService());
	}
}
