package com.excilys.computer_database.console_view;

import java.util.Scanner;

public class ShowComputerView {
	
	protected static Scanner input;

	public static String show() {
		input = new Scanner(System.in);
		System.out.println("Choose a computer id you want to see:");
		return input.nextLine();
		
	}
}