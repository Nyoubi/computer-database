package com.excilys.view.menu;

import java.util.ArrayList;

import com.excilys.model.Company;
import com.excilys.util.Util;
import com.excilys.view.View;

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
