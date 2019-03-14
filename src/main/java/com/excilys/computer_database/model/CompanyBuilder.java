package com.excilys.computer_database.model;

public class CompanyBuilder {
	private Integer id;
	private String name;

	public Company build() {
		Company company = new Company();
		
		company.setId(this.id);
		company.setName(this.name);
		return company;
	}
	
	public CompanyBuilder setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public CompanyBuilder setName(String name) {
		this.name = name;
		return this;
	}

}
