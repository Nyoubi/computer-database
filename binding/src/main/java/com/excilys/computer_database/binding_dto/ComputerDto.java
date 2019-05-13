package com.excilys.computer_database.binding_dto;

import java.util.Objects;

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
	
	@Override
	public int hashCode() {
		return Objects.hash(companyId, companyName, discontinued, id, introduced, name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		ComputerDto other = (ComputerDto) obj;
		return companyId == other.companyId && Objects.equals(companyName, other.companyName)
				&& Objects.equals(discontinued, other.discontinued) && id == other.id
				&& Objects.equals(introduced, other.introduced) && Objects.equals(name, other.name);
	}
	
	
}
