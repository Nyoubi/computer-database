package com.excilys.computer_database.model;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.exception.ExceptionModel;

public class ComputerBuilder {
	private Integer id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;
	
	private static Logger logger = LoggerFactory.getLogger(ComputerBuilder.class);

	public Computer build() throws ExceptionModel{
		Computer computer = new Computer();
		if (this.id == null) {
			logger.error("Id can't be empty");
			throw new ExceptionModel("Id can't be null.");
		}
		computer.setId(this.id);
		if (this.name == null) {
			logger.error("Name can't be empty");
			throw new ExceptionModel("Can't set name to null.");
		}
		computer.setName(this.name);
		computer.setIntroduced(this.introduced);
		if (this.introduced == null && this.discontinued != null) {
			logger.error("Can't set discontinued when introduced isn't set");
			throw new ExceptionModel("Introduced need to be fill first.");
		} else if (this.introduced != null && this.discontinued != null && this.introduced.compareTo(this.discontinued) >= 0) {
			logger.error("Discontinued date can't be filled before introduced's one");
			throw new ExceptionModel("Discontinued date can't be filled before introduced's one.");
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
