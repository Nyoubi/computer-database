package com.excilys.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerDB;

public class MainCDB  {
	public static void main(String[] args) {
		try {
			ComputerDB db = new ComputerDB();
			ArrayList<Computer> computers;
			ArrayList<Company> companies;
			Computer c;
			while (true) {
				Scanner in = new Scanner(System. in);
				System.out.println("Options:");
		        System.out.println("1. Get computers list");
		        System.out.println("2. Get companies list");
		        System.out.println("3. Show computer details");
		        System.out.println("4. Create computer");
		        System.out.println("5. Update computer");
		        System.out.println("6. Delete computer");
		        System.out.println("7. Exit program");
		        int input = in.nextInt();
		        switch (input) {
		            case 1:  computers = db.listComputers();
		            		 System.out.println("Done");
		                     break;
		            case 2:  companies = db.listCompanies();
           		 			 System.out.println("Done");
           		 			 break;
		            case 3:  System.out.println("Which computer(id) do you want to see ?");
		            		 db.showDetails(Integer.parseInt(in.nextLine())); 
		            		 break;
		            case 4:  System.out.println("Choose a name");
		                     String name =  in.nextLine();
		                     
		            		 break;
		            case 5:  monthString = "May";
		                     break;
		            case 6:  monthString = "June";
		                     break;
		            case 7:  monthString = "July";
		                     break;
		        }
				Scanner in = new Scanner(System. in);
				ArrayList<Computer> computers = db.listComputers();
				computers.forEach((c) -> System.out.println(c));
				
				ArrayList<Company> companies = db.listCompanies();
				companies.forEach((c) -> System.out.println(c));
				
				db.showDetails(2);
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		} catch(Exception e){
		      e.printStackTrace();
		} catch(IOException e){
		      e.printStackTrace();
	}
}
