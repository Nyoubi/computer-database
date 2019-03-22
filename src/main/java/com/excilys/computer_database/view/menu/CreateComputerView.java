package com.excilys.computer_database.view.menu;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CreateComputerView{
	private Scanner input;
	private static Logger logger = LoggerFactory.getLogger(CreateComputerView.class);

	private CreateComputerView() {}
	
    private static volatile CreateComputerView instance = null;
    
	public static CreateComputerView getInstance()
    {   
		if (instance == null) {
			synchronized(CreateComputerView.class) {
				if (instance == null) {
					instance = new CreateComputerView();
				}
			}
		}
		return instance;
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
	
	public void exec (Optional<Integer> id) {
		if(id.isPresent())
			System.out.println("Computer " + id + " has been created.");
		else 
			logger.error("Problem occured when creating the computer.");
	}
}
