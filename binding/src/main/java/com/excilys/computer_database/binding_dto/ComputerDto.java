package com.excilys.computer_database.binding_dto;

public class ComputerDto {
	private int id = 0;
	private String name;
	private String introduced;
	private String discontinued;
	private int companyId;
	private String companyName;
	
	public ComputerDto(int id, String name, String introduced, String discontinued, int companyId, String companyName) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
		this.companyName = companyName;
	}
	
	public ComputerDto () {}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public int getCompanyId() {
		return companyId;
	}

	public void setCompanyId(int companyId) {
		this.companyId = companyId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String toString() { 
	    return "Id: " + Integer.toString(this.id)
	    		+ ", Name: " + this.name 
	    		+ ", Introduced: " + this.introduced 
	    		+ ", Discontinued: " + this.discontinued 
	    		+ ", Company: (" + Integer.toString(this.companyId) + ", " + this.companyName + ")";
	}
}