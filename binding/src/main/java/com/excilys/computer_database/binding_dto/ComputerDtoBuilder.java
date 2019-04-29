package com.excilys.computer_database.binding_dto;

public class ComputerDtoBuilder {
	private int id;
	private String name;
	private String introduced;
	private String discontinued;
	private int companyId;
	private String companyName;

	public ComputerDto build() {
		ComputerDto computer = new ComputerDto();

		computer.setId(this.id);
		computer.setName(this.name);
		computer.setIntroduced(this.introduced);
		computer.setDiscontinued(this.discontinued);
		computer.setCompanyId(this.companyId);
		computer.setCompanyName(this.companyName);
		return computer;
	}
	
	public ComputerDtoBuilder setId(int id) {
		this.id = id;
		return this;
	}
	
	public ComputerDtoBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public ComputerDtoBuilder setIntroduced(String introduced) {
		this.introduced = introduced;
		return this;
	}

	public ComputerDtoBuilder setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
		return this;
	}

	public ComputerDtoBuilder setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}
	
	public ComputerDtoBuilder setCompanyId(int companyId) {
		this.companyId = companyId;
		return this;
	}

	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}
	public String getIntroduced() {
		return this.introduced;
	}
	public String getDiscontinued() {
		return this.discontinued;
	}
	public String getCompanyName() {
		return this.companyName;
	}
	public int getCompanyId() {
		return this.companyId;
	}

	
}