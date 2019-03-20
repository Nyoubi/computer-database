package com.excilys.computer_database.utilTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNull;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.excilys.computer_database.util.Util;

public class UtilTest {

	@Test
	public void testParseInt() {
		assertNull(Util.parseInt("null"));
		assertEquals(Util.parseInt("15"),Integer.valueOf(15));
		assertEquals(Util.parseInt("-15"),Integer.valueOf(-15));
		assertNotEquals(Util.parseInt("1"),Integer.valueOf(2));
		assertEquals(Util.parseInt("NaN"),Integer.valueOf(-1));
	}
	
	@Test
	public void testStringToTimestamp() {
		assertNull(Util.stringToTimestamp("null"));
		assertEquals(Util.stringToTimestamp("2000-01-01 13:12:12"),Timestamp.valueOf("2000-01-01 13:12:12"));
		assertNotEquals(Util.stringToTimestamp("2000-01-01 12:12:12"),Timestamp.valueOf("2010-01-01 12:12:12"));
		assertNull(Util.stringToTimestamp("Not a timestamp or bad format"));

	}
	
}
