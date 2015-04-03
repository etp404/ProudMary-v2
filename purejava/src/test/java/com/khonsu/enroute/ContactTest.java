package com.khonsu.enroute;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ContactTest {
	@Test
	public void testThatFormatPerformsAsExpected() {
		String name = "Brian Singer";
		String number = "0123456";

		Contact contact = new Contact(name, number);
		assertEquals(name + " <" + number + ">", contact.toString());
	}
}
