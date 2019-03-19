package com.excilys.computer_database.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Util {

	private static Logger logger = LoggerFactory.getLogger(Util.class);
	public static Integer parseInt(String input) {
		if (input.equals("null"))
		{
			return null;
		}
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
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = dateFormat.parse(stringDate);
				timeStampDate = new Timestamp(date.getTime());
			}
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    	logger.error("Error when parsing " + stringDate + ". Date must be in format yyyy-MM-dd hh:mm:ss or null");
	    }
		return timeStampDate;
	  }
}
