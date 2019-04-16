package com.excilys.computer_database.dto;

public class CompanyDtoBuilder {
	private int id;
	private String name;

	public CompanyDto build() {
		CompanyDto company = new CompanyDto();
		company.setId(this.id);
		company.setName(this.name);
		return company;
	}
	
	public CompanyDtoBuilder setId(int id) {
		this.id = id;
		return this;
	}
	
	public CompanyDtoBuilder setName(String name) {
		this.name = name;
		return this;
	}

	public int getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}

}
