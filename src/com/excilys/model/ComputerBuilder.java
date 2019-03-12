package com.excilys.model;

import java.sql.Timestamp;

public class ComputerBuilder {
	private Integer id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;

	public Computer build() {
		Computer computer = new Computer();
		computer.setId(this.id);
		computer.setName(this.name);
		computer.setIntroduced(this.introduced);
		computer.setDiscontinued(this.discontinued);
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
	
}
