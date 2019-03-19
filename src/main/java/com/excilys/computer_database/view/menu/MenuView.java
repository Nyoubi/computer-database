package com.excilys.computer_database.view.menu;
import static com.excilys.computer_database.view.menu.MenuViewOptions.COMPUTER_DETAILS;
import static com.excilys.computer_database.view.menu.MenuViewOptions.CREATE_COMPUTER;
import static com.excilys.computer_database.view.menu.MenuViewOptions.DELETE_COMPUTER;
import static com.excilys.computer_database.view.menu.MenuViewOptions.EXIT;
import static com.excilys.computer_database.view.menu.MenuViewOptions.LIST_COMPANIES;
import static com.excilys.computer_database.view.menu.MenuViewOptions.LIST_COMPUTERS;
import static com.excilys.computer_database.view.menu.MenuViewOptions.UPDATE_COMPUTER;

import java.util.Scanner;

import com.excilys.computer_database.view.View;

public class MenuView extends View{
	
    private static volatile MenuView instance = null;
    
    private MenuView() {}
    
	public static MenuView getInstance()
    {   
		if (instance == null) {
			synchronized(MenuView.class) {
				if (instance == null) {
					instance = new MenuView();
				}
			}
		}
		return instance;
    }
	
	
	@Override
	public String show() {
		String value;
		this.input = new Scanner(System.in);
		System.out.println("\n==========================");
		System.out.println("Choose an option");
		System.out.println(LIST_COMPUTERS.getId() + " - List all computers");
		System.out.println(LIST_COMPANIES.getId() + " - List all companys");
		System.out.println(COMPUTER_DETAILS.getId() + " - Show computer details");
		System.out.println(CREATE_COMPUTER.getId() + " - Create a computer");
		System.out.println(UPDATE_COMPUTER.getId() + " - Update Computer");
		System.out.println(DELETE_COMPUTER.getId() + " - Delete Computer");
		System.out.println(EXIT.getId() + " - Exit");
		System.out.println("==========================\n");

		value = input.nextLine();
		
		return value;
	}
}
