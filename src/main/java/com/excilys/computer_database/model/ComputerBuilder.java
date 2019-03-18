package com.excilys.computer_database.model;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ComputerBuilder {
	private Integer id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;
	private static Logger logger = LoggerFactory.getLogger(ComputerBuilder.class);

	public Computer build() {
		Computer computer = new Computer();
		if (this.id == null) {
			logger.warn("Can't set id to null. Computer build canceled, return null.");
			return null;
		} 
		computer.setId(this.id);
		if (this.name == null) {
			logger.warn("Can't set name to null. Computer build canceled, return null.");
			return null;
		} 
		computer.setName(this.name);
		computer.setIntroduced(this.introduced);
		if (this.introduced == null && this.discontinued != null) {
			logger.warn("Can't set discontinued with a date when introduced is null. Discontinued has been set to null.");
			computer.setDiscontinued(null);
		} else if (this.introduced != null && this.discontinued != null && this.introduced.compareTo(this.discontinued) >= 0) {
			logger.warn("Can't set discontinued with a date before introduced's one. Discontinued has been set to null.");
			computer.setDiscontinued(null);
		} else {
			computer.setDiscontinued(this.discontinued);
		}
		computer.setCompany(this.company);
		return computer;
	}
	
	public ComputerBuilder setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public ComputerBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public ComputerBuilder setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
		return this;
	}

	public ComputerBuilder setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
		return this;
	}

	public ComputerBuilder setCompany(Company company) {
		this.company = company;
		return this;
	}
	
	public Integer getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public Timestamp getIntroduced() {
		return this.introduced;
	}
	public Timestamp getDiscontinued() {
		return this.discontinued;
	}
	public Company getCompany() {
		return this.company;
	}
	
}
