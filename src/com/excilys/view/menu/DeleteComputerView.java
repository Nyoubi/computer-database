package com.excilys.view.menu;

import java.util.Scanner;

import com.excilys.view.View;

public class DeleteComputerView extends View{
	
	public DeleteComputerView() {

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