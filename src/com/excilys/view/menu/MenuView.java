package com.excilys.view.menu;

import java.util.Scanner;

import com.excilys.view.View;

public class MenuView extends View{
	@Override
	public String show() {
		String value;
		this.input = new Scanner(System.in);
		System.out.println("==========================");
		System.out.println("Choose an option");
		System.out.println(menuViewOptions.LIST_COMPUTERS.getId() + " - List all computers");
		System.out.println(menuViewOptions.LIST_COMPANIES.getId() + " - List all companys");
		System.out.println(menuViewOptions.COMPUTER_DETAILS.getId() + " - Show computer details");
		System.out.println(menuViewOptions.CREATE_COMPUTER.getId() + " - Create a computer");
		System.out.println(menuViewOptions.UPDATE_COMPUTER.getId() + " - Update Computer");
		System.out.println(menuViewOptions.DELETE_COMPUTER.getId() + " - Delete Computer");
		System.out.println(menuViewOptions.EXIT.getId() + " - Exit");
		System.out.println("==========================");

		value = input.nextLine();
		
		return value;
	}
}
