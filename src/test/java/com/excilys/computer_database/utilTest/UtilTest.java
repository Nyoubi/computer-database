package com.excilys.computer_database.utilTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.excilys.computer_database.util.Util;

public class UtilTest {

	@Test
	public void testParseInt() {
		assertFalse(Util.parseInt("null").isPresent());
		assertEquals(Util.parseInt("15").get(),Integer.valueOf(15));
		assertEquals(Util.parseInt("-15").get(),Integer.valueOf(-15));
		assertNotEquals(Util.parseInt("1").get(),Integer.valueOf(2));
		assertFalse(Util.parseInt("NaN").isPresent());
	}
	
	@Test
	public void testStringToTimestamp() {
		assertFalse(Util.stringToTimestamp("null").isPresent());
		assertEquals(Util.stringToTimestamp("2000-01-01 13:12:12").get(),Timestamp.valueOf("2000-01-01 13:12:12"));
		assertNotEquals(Util.stringToTimestamp("2000-01-01 12:12:12").get(),Timestamp.valueOf("2010-01-01 12:12:12"));
		assertFalse(Util.stringToTimestamp("Not a timestamp or bad format").isPresent());
	}
	
}
