package com.excilys.computer_database.view.menu;

import java.util.ArrayList;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.util.Util;
import com.excilys.computer_database.view.View;

public class CompanyListView extends View{
	private ArrayList<Company> array = new ArrayList<Company>();
	
	public CompanyListView(ArrayList<Company> array) {
		this.array = array;
	}

	@Override
	public String show() {
		
		System.out.println(Util.boxMessage("Database's companies list"));
		
		array.forEach((company) -> System.out.println(company.toString()));
		
		return null;
	}
}
