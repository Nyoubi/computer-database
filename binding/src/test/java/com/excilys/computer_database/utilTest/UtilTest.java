package com.excilys.computer_database.utilTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;

import java.sql.Timestamp;

import org.junit.jupiter.api.Test;

import com.excilys.computer_database.utils.Util;

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
		assertEquals(Util.stringToTimestamp("2000-01-01").get(),Timestamp.valueOf("2000-01-01 00:00:00"));
		assertNotEquals(Util.stringToTimestamp("2000-01-01").get(),Timestamp.valueOf("2010-01-01 00:00:00"));
		assertFalse(Util.stringToTimestamp("Not a timestamp or bad format").isPresent());
		assertFalse(Util.stringToTimestamp("2010-041-01").isPresent());
	}
	
}
