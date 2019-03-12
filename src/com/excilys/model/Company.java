package com.excilys.model;

public class Company {
	private Integer id;
	private String name;
	
	public Company(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Company() {}
	
	public long getId() {
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
		Company other = (Company) obj;
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
