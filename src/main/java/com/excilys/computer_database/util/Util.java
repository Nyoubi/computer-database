package com.excilys.computer_database.util;

import java.sql.Timestamp;
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
		if(stringDate != null && !("".equals(stringDate))) {
			return Optional.of(Timestamp.valueOf(stringDate + " 00:00:00"));
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
