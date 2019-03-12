package com.excilys.model;

public class Company {
	private Integer id;
	private String name;
	
	public Company(Integer id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String toString() { 
	    return "Id: '" + this.id 
	    		+ "', Name: '" + this.name  + "'";
	} 
}
