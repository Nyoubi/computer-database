
package com.excilys.model;

import java.sql.*;

public class Computer {
	private int id;
	private String name;
	private Timestamp introduced;
	private Timestamp discontinued;
	private Company company;
	
	public Computer(int id, String name, Timestamp introduced, Timestamp discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
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
