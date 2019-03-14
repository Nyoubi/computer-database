package com.excilys.view.menu;

import java.util.ArrayList;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.controller.Controller;


public class CreateComputerView{
	private Scanner input;
	private static Logger logger = LoggerFactory.getLogger(Controller.class);

	public CreateComputerView() {

	}
	
	public ArrayList<String> show() {
		ArrayList<String> result= new ArrayList<>();
		this.input = new Scanner(System.in);
		System.out.println("Choose the name of the computer:");
		result.add(input.nextLine());
		System.out.println("Choose the introduced date of the computer (yyyy-mm-dd hh:mm:ss or null):");
		result.add(input.nextLine());
		System.out.println("Choose the discontinued date of the computer (yyyy-mm-dd hh:mm:ss or null):");
		result.add(input.nextLine());
		System.out.println("Choose the company id of the computer (can be null):");
		result.add((input.nextLine()));
		return result;
	}
	
	public void exec (Boolean created) {
		if(created)
			System.out.println("Computer has been created");
		else 
			logger.error("Problem occured when creating the computer");
	}
}
