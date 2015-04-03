package com.khonsu.enroute;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ContactTest {
	@Test
	public void testThatFormatPerformsAsExpected() {
		String name = "Brian Singer";
		String number = "0123456";

		Contact contact = new Contact(name, number);
		assertEquals(name + " <" + number + ">", contact.toString());
	}

	@Test
	public void testThatCanParseStringWithoutNameAndOutputNumber() {
		String number = "0123456";

		Contact contact = Contact.fromString(number);
		assertEquals(number, contact.toString());
		assertEquals(number, contact.getNumber());
	}

	@Test
	public void testThatCanParseStringWithNameAndOutputNumber() {
		String name = "Brian Singer";
		String number = "0123456";
		String contactString = name + " <" + number + ">";

		Contact contact = Contact.fromString(contactString);
		assertEquals(contactString, contact.toString());
		assertEquals(number, contact.getNumber());
	}

	@Test
	public void testThatContactWithNameCanBeValidated() {
		assertTrue(Contact.isValidString("Tim Burton <555 5555>"));
	}

	@Test
	public void testThatWeirdStringCanBeValidatedCorrectly() {
		assertFalse(Contact.isValidString("Tim Bu 5555>"));
	}
}
