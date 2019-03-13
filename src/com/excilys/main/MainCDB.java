package com.excilys.main;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import com.excilys.exceptions.ExceptionMessage;
import com.excilys.exceptions.ExceptionViewMessage;
import com.excilys.model.Company;
import com.excilys.model.Computer;
import com.excilys.model.ComputerDB;

public class MainCDB  {
	
	private static Scanner scan = new Scanner(System.in);
	private static String menuOption = "Options: \n"
			+ "1. Get computers list\n"
			+ "2. Get companies list\n"
			+ "3. Show computer details\n"
			+ "4. Create computer\n"
			+ "5. Update computer\n"
			+ "6. Delete computer\n"
			+ "7. Exit program";
			
	private static Integer inputInt(String message) {
		System.out.println(message);
		String input = scan.nextLine();
        return parseInt(input);
	}
	
	private static String inputString(String message) {
		System.out.println(message);
        return scan.nextLine();
	}
	
	private static Integer parseInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e){
			return -1;
		}
	}

	public static Timestamp stringToTimestamp(String stringDate) throws ExceptionViewMessage{
		try {
			if(stringDate.equals("null")) {
				return null;
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = dateFormat.parse(stringDate);
				Timestamp timeStampDate = new Timestamp(date.getTime());
				return timeStampDate;
			}
	    } catch (ParseException e) {
	    	throw new ExceptionViewMessage("Format de date incorrect");
	    }
	  }
	
	public static void main(String[] args) {
		try {
			ComputerDB computerDatabase = new ComputerDB();
			ArrayList<Computer> computers;
			ArrayList<Company> companies;
			boolean run = true;
			while (run) {
		        switch (inputInt(menuOption)) {
		            case 1:  computers = computerDatabase.listComputers();
		            		 System.out.println("Done");
		                     break;
		                     
		            case 2:  companies = computerDatabase.listCompanies();
           		 			 System.out.println("Done");
           		 			 break;
           		 			 
		            case 3:  Integer input = inputInt("Which computer(id) do you want to see ?");
		            		 if(input <=0) {
		            			 System.out.println("Invalid input.");
		            		 } else {
		            			 computerDatabase.showDetails(input);
		            		 }
		            		 break;
		            		 
		            case 4:  String name = inputString("Choose a name:");
				             Timestamp introduced = stringToTimestamp(inputString("Choose an introduced date (yyyy-mm-dd hh:mm:ss or null):"));
				             Timestamp discontinued = stringToTimestamp(inputString("Choose an introduced date (yyyy-mm-dd hh:mm:ss or null):"));
				             Integer companyId = inputInt("Choose a company_id (can be null):");
		                     if (companyId <= 0 ) {
		                    	 System.out.println("Invalid input, please follow instructions");
		                     } else {
		                    	 computerDatabase.createComputer(name, introduced, discontinued, companyId);
		                     }
				           	 break;
		            		 
		            case 5:  Integer inputUpdate = inputInt("Which computer(id) do you want to update ?");
				           	 if(inputUpdate <=0) {
				        		 System.out.println("Invalid input.");
				        	 } else {
				        		 Integer status = computerDatabase.showDetails(inputUpdate);
				        		 if (status == 0) {
				        			 String nameUpdate = inputString("Choose a name:");
				                     Timestamp introducedUpdate = stringToTimestamp(inputString("Choose an introduced date (yyyy-mm-dd hh:mm:ss or null):"));
				                     Timestamp discontinuedUpdate = stringToTimestamp(inputString("Choose an introduced date (yyyy-mm-dd hh:mm:ss or null):"));
				                     Integer companyIdUpdate = inputInt("Choose a company_id (can't be null):");
				                     if (companyIdUpdate != null) {
				                    	 computerDatabase.updateComputer(inputUpdate,nameUpdate, introducedUpdate, discontinuedUpdate, companyIdUpdate);
				                     } else {
				                    	 System.out.println("Company id can't be null.");
				                     }
				                 } else {
				        			 System.out.println("This computer doesn't exist.");
				        		 }
				        	 }
				           	 break;
		            case 6:  computerDatabase.deleteComputer(inputInt("Which computer(id) do you want to delete ?")); 
           		 			 break;
		            case 7:  System.out.println("Goodbye.");
		            		 run = false;
		                     break;
		            case -1: System.out.println("Invalid input.");
		            		 break;
		        }			
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();

		}  catch(ExceptionMessage e){
		     System.out.println(e);
		} catch(Exception e){
		      e.printStackTrace();
		} finally {
			if (scan != null)
	        	scan.close();
		}
	}
}
