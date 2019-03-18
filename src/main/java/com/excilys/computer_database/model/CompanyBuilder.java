package com.excilys.computer_database.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CompanyBuilder {
	private Integer id;
	private String name;
	private static Logger logger = LoggerFactory.getLogger(CompanyBuilder.class);

	public Company build() {
		Company company = new Company();
		if (this.id == null) {
			logger.warn("Can't set id to null. Company build canceled, return null.");
			return null;
		} 

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

	public Integer getId() {
		return this.id;
	}
	public String getName() {
		return this.name;
	}

}
