package com.excilys.computer_database.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Util {

	private static Logger logger = LoggerFactory.getLogger(Util.class);
	public static Optional<Integer> parseInt(String input) {
		if (input == null || input.equals(""))
		{
			return Optional.empty();
		}
		try {
			return Optional.of(Integer.valueOf(input));
		} catch (NumberFormatException e){
			logger.error("Error when parsing " + input + " to an Integer");
		}
		return Optional.empty();
	}
	
	public static Optional<Timestamp> stringToTimestamp(String stringDate){
		try {
			if(stringDate == null || stringDate.equals("null") || stringDate.equals("")) {
				return Optional.empty();
			} else {
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Date date = dateFormat.parse(stringDate);
				if(dateFormat.format(date).equals(stringDate)) {
					return Optional.of(new Timestamp(date.getTime()));
				}
			}
	    } catch (ParseException e) {
	    	logger.error("Error when parsing " + stringDate + ". Date must be in format yyyy-MM-dd or null");
	    }
		return Optional.empty();
	  }
	
	public static <T> T checkOptional (Optional<T> optional) {
		if (optional.isPresent()) {
			return optional.get();
		} else {
			return null;
		}
	}
}
