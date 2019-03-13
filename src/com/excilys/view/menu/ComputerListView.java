package com.excilys.view.menu;

import java.util.ArrayList;

import com.excilys.model.Computer;
import com.excilys.util.Util;
import com.excilys.view.View;

public class ComputerListView extends View{
	private ArrayList<Computer> computerList = new ArrayList<>();
	
	public ComputerListView(ArrayList<Computer> computerList) {
		this.computerList = computerList;
	}

	@Override
	public String show() {
		
		System.out.println(Util.boxMessage("Database's computers list"));
		
		computerList.forEach((computer) -> System.out.println(computer.toString()));
		
		return null;
	}
}
