package com.excilys.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Util {
	public static Integer parseInt(String input) {
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e){
			return -1;
		}
	}
	
	public static Timestamp stringToTimestamp(String stringDate){
		Timestamp timeStampDate = null;
		try {
			if(stringDate.equals("null")) {
				return null;
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = dateFormat.parse(stringDate);
				timeStampDate = new Timestamp(date.getTime());
			}
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    }
		return timeStampDate;
	  }
	
	
	
	private static String repeatNTimes(int n, String c) {
	    if (n <= 0) return null;
	    
	    return new String(new char[n]).replace("\0", c);    
	}
	
	/**
	 * 
	 * @author Alexander van Dalen
	 * @param message the string you want to print
	 * @return the boxed string
	 */
	public static String boxMessage(String message) {
	    int length = message.length();
	    String ret = "\n";
	    ret += " _"+repeatNTimes(length, "_")+"_\n";
	    ret += "/ "+repeatNTimes(length, " ")+" \\\n";
	    ret += "| "+message+" |\n";
	    ret += "\\_"+repeatNTimes(length, "_")+"_/\n";
	        
	    return ret;
	}
}
