package com.excilys.computer_database.view.menu;

import java.util.Scanner;

import com.excilys.computer_database.controller.Controller;
import com.excilys.computer_database.view.View;

public class DeleteComputerView extends View{
	
	private DeleteComputerView() {}
	
    private static volatile DeleteComputerView instance = null;
    
	public static DeleteComputerView getInstance()
    {   
		if (instance == null) {
			synchronized(DeleteComputerView.class) {
				if (instance == null) {
					instance = new DeleteComputerView();
				}
			}
		}
		return instance;
    }

	@Override
	public String show() {
		this.input = new Scanner(System.in);
		System.out.println("Choose a computer id you want to delete:");
		return input.nextLine();
		
	}
	
	public void exec(Boolean deleted) {
		if (deleted)
			System.out.println("Computer has been deleted.");
		else
			System.out.println("This computer doesn't exist in the database.");
	}
}
