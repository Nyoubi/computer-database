package com.excilys.view;

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class View {
	protected Scanner input;
	protected static Logger logger = LoggerFactory.getLogger(View.class);

	public abstract String show();
}