package com.excilys.computer_database.view.menu;

import java.util.ArrayList;

import com.excilys.computer_database.model.Company;
import com.excilys.computer_database.view.View;

public class CompanyListView extends View{
	
	private ArrayList<Company> companyList = new ArrayList<Company>();
	
	private CompanyListView(ArrayList<Company> companyList) {
		this.companyList = companyList;
	}
	
    private static volatile CompanyListView instance = null;
    
	public static CompanyListView getInstance(ArrayList<Company> companyList)
    {   
		if (instance == null) {
			synchronized(CompanyListView.class) {
				if (instance == null) {
					instance = new CompanyListView(companyList);
				}
			}
		}
		return instance;
    }
	

	@Override
	public String show() {
		
		System.out.println("Database's companies list");
		
		companyList.forEach(System.out::println);
		
		return null;
	}
}
