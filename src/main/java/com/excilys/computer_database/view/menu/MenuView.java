package com.excilys.computer_database.view.menu;
import static com.excilys.computer_database.view.menu.MenuViewOptions.*;

import java.util.Scanner;

import com.excilys.computer_database.controller.Controller;
import com.excilys.computer_database.view.View;

public class MenuView extends View{
	
    private static volatile MenuView instance = null;
    
    private MenuView() {}
    
	public static MenuView getInstance()
    {   
		if (instance == null) {
			synchronized(Controller.class) {
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
