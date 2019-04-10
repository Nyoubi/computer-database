package com.excilys.computer_database.app;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import com.excilys.computer_database.persistence.DaoComputer;
import com.excilys.computer_database.persistence.DaoCompany;
import com.excilys.computer_database.service.CompanyService;
import com.excilys.computer_database.service.ComputerService;

@Configuration
public class AppConfigTest {
	
	@Bean
	public HikariConfig HikariConfigTest() {
		return new HikariConfig("/dataSourceTest.properties");
	}

	
	@Bean
	public HikariDataSource HikariDataSourceTest() {
		return new HikariDataSource(HikariConfigTest());
	}
	
	@Bean
	public DaoComputer DaoComputerTest() {
		return new DaoComputer(HikariDataSourceTest());
	}
	
	@Bean
	public DaoCompany DaoCompanyTest() {
		return new DaoCompany(HikariDataSourceTest());
	}
	
	@Bean
	public ComputerService ComputerServiceTest() {
		return new ComputerService(DaoComputerTest(),CompanyServiceTest());
	}
	
	@Bean
	public CompanyService CompanyServiceTest() {
		return new CompanyService(DaoCompanyTest());
	}
}
