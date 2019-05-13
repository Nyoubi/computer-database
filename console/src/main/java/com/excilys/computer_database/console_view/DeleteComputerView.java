package com.excilys.computer_database.console_view;

import java.util.Optional;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.computer_database.utils.Util;

public class DeleteComputerView {

	protected static Scanner input;
	protected static Logger logger = LoggerFactory.getLogger(MenuView.class);

	//Private constructor
	private DeleteComputerView () {}

	public static Optional<Integer> show () {
		input = new Scanner(System.in);
		logger.info("Choose a computer id you want to delete:");
		return Util.parseInt(input.nextLine());
	}
}