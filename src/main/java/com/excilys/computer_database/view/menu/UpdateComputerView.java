package com.excilys.computer_database.view.menu;

import java.util.ArrayList;
import java.util.Scanner;

import com.excilys.computer_database.controller.Controller;


public class UpdateComputerView{
	private Scanner input;

	private UpdateComputerView() {}
	
    private static volatile UpdateComputerView instance = null;
    
	public static UpdateComputerView getInstance()
    {   
		if (instance == null) {
			synchronized(Controller.class) {
				if (instance == null) {
					instance = new UpdateComputerView();
				}
			}
		}
		return instance;
    }
	
	public ArrayList<String> show() {
		ArrayList<String> result= new ArrayList<>();
		this.input = new Scanner(System.in);
		System.out.println("Choose a computer id you want to update:");
		result.add(input.nextLine());
		System.out.println("Choose the new name of the computer:");
		result.add(input.nextLine());
		System.out.println("Choose the new introduced date of the computer (yyyy-mm-dd hh:mm:ss or null):");
		result.add(input.nextLine());
		System.out.println("Choose the new discontinued date of the computer (yyyy-mm-dd hh:mm:ss or null):");
		result.add(input.nextLine());
		System.out.println("Choose the new company id of the computer (can't be null):");
		result.add(input.nextLine());
		return result;
	}
	
	public void exec (Boolean result) {
		if (result) {
			System.out.println("Computer has been updated");
		} else {
			System.out.println("Computer has been updated");
		}
	}
}