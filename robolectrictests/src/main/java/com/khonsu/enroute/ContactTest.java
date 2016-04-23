package com.khonsu.enroute;

import com.khonsu.enroute.settingup.contacts.Contact;

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
}
