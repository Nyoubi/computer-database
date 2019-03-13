package com.excilys.view.menu;

import java.util.Optional;
import java.util.Scanner;

import com.excilys.model.Computer;
import com.excilys.util.Util;
import com.excilys.view.View;

public class ComputerView extends View{
	
	public ComputerView() {

	}

	@Override
	public String show() {
		this.input = new Scanner(System.in);
		System.out.println("Choose a computer id you want to see");
		return input.nextLine();
		
	}
	
	public void exec(Optional<Computer> computer) {
		System.out.println(Util.boxMessage("Computer details"));
		System.out.println(computer);
	}
}
