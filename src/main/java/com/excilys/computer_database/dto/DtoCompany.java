package com.excilys.computer_database.dto;


public class DtoCompany {
	private Integer id;
	private String name;
	
	public DtoCompany(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public DtoCompany() {}
	
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

	public String toString() { 
	    return "Id: '" + this.id 
	    		+ "', Name: '" + this.name  + "'";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DtoCompany other = (DtoCompany) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
