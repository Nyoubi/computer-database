package com.excilys.computer_database.dto;


import java.sql.Timestamp;

public class DtoComputer {
	private Integer id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private DtoCompany company;
	
	public DtoComputer(Integer id, String name, Timestamp introduced, Timestamp discontinued, DtoCompany company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
	}
	
	public DtoComputer () {}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Timestamp getIntroduced() {
		return introduced;
	}

	public void setIntroduced(Timestamp introduced) {
		this.introduced = introduced;
	}

	public Timestamp getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(Timestamp discontinued) {
		this.discontinued = discontinued;
	}

	public DtoCompany getCompany() {
		return company;
	}

	public void setCompany(DtoCompany company) {
		this.company = company;
	}

	public String toString() { 
	    return "Id: " + this.id 
	    		+ ", Name: " + this.name 
	    		+ ", Introduced: " + this.introduced 
	    		+ ", Discontinued: " + this.discontinued 
	    		+ ", Company: (" + this.company + ")";
	}
}
