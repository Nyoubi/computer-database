package com.excilys.computer_database.dto;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DtoCompanyBuilder {
	private Integer id;
	private String name;
	private static Logger logger = LoggerFactory.getLogger(DtoCompanyBuilder.class);

	public DtoCompany build() {
		DtoCompany company = new DtoCompany();
		if (this.id == null) {
			logger.warn("Can't set id to null. Company build canceled, return null.");
			return null;
		} 

		company.setId(this.id);
		company.setName(this.name);
		return company;
	}
	
	public DtoCompanyBuilder setId(Integer id) {
		this.id = id;
		return this;
	}
	
	public DtoCompanyBuilder setName(String name) {
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
