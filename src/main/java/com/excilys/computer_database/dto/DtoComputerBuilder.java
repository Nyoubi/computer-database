package com.excilys.computer_database.dto;

import java.sql.Timestamp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DtoComputerBuilder {
	private Integer id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private DtoCompany company;
	private static Logger logger = LoggerFactory.getLogger(DtoComputerBuilder.class);

	public DtoComputer build() {
		DtoComputer computer = new DtoComputer();

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
	
	public DtoComputerBuilder setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public DtoComputerBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public DtoComputerBuilder setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
		return this;
	}

	public DtoComputerBuilder setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
		return this;
	}

	public DtoComputerBuilder setCompany(DtoCompany company) {
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
	public DtoCompany getCompany() {
		return this.company;
	}


	
}