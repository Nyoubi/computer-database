package com.excilys.computer_database.view.menu;

import java.util.ArrayList;

import com.excilys.computer_database.controller.Controller;
import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.util.Util;
import com.excilys.computer_database.view.View;

public class ComputerListView extends View{
	private ArrayList<Computer> computerList = new ArrayList<>();
	
	private ComputerListView(ArrayList<Computer> computerList) {
		this.computerList = computerList;
	}
	
    private static volatile ComputerListView instance = null;
    
	public static ComputerListView getInstance(ArrayList<Computer> computerList)
    {   
		if (instance == null) {
			synchronized(ComputerListView.class) {
				if (instance == null) {
					instance = new ComputerListView(computerList);
				}
			}
		}
		return instance;
    }

	@Override
	public String show() {
		
		System.out.println(Util.boxMessage("Database's computers list"));
		
		computerList.forEach(System.out::println);
		
		return null;
	}
}
