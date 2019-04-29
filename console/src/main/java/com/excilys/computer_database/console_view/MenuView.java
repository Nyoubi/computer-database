
package com.excilys.computer_database.console_view;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MenuView {
	protected static Scanner input;
	protected static Logger logger = LoggerFactory.getLogger(MenuView.class);
	
	public static String show() {
		String value;
		input = new Scanner(System.in);
		System.out.println("\n==========================");
		System.out.println("Choose an option");
		System.out.println(MenuOptionsView.DELETE_COMPANY.getId() + " - Delete a company");
		System.out.println(MenuOptionsView.EXIT.getId() + " - Exit");
		System.out.println("==========================\n");

		value = input.nextLine();
		
		return value;
	}
}