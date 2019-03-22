package com.excilys.computer_database.dto;

public class DtoCompanyBuilder {
	private int id;
	private String name;

	public DtoCompany build() {
		DtoCompany company = new DtoCompany();
		company.setId(this.id);
		company.setName(this.name);
		return company;
	}
	
	public DtoCompanyBuilder setId(int id) {
		this.id = id;
		return this;
	}
	
	public DtoCompanyBuilder setName(String name) {
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
