package com.excilys.computer_database.console_view;

import java.util.Scanner;

public class DeleteCompanyView {
	
	protected static Scanner input;

	public static String show() {
		input = new Scanner(System.in);
		System.out.println("Choose a company id you want to delete:");
		return input.nextLine();
		
	}
	
	public static void exec(Boolean deleted) {
		if (deleted)
			System.out.println("Company has been deleted.");
		else
			System.out.println("This company doesn't exist in the database.");
	}
}