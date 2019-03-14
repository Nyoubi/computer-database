package com.excilys.computer_database.view.menu;

import java.util.Optional;
import java.util.Scanner;

import com.excilys.computer_database.model.Computer;
import com.excilys.computer_database.util.Util;
import com.excilys.computer_database.view.View;

public class ComputerView extends View{
	
	public ComputerView() {

	}

	@Override
	public String show() {
		this.input = new Scanner(System.in);
		System.out.println("Choose a computer id you want to see:");
		return input.nextLine();
		
	}
	
	public static void exec(Optional<Computer> computer) {
		System.out.println(Util.boxMessage("Computer details"));
		if(computer.isPresent()) {
		    System.out.println(computer.get());
		} else {
		    System.out.println("Empty");
		}	
	}
}
