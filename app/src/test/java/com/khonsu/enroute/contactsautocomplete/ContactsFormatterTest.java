package com.khonsu.enroute.contactsautocomplete;

import com.khonsu.enroute.PhoneNumberValidator;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ContactsFormatterTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testThatFormatPerformsAsExpected() {
        String name = "Brian Singer";
        String number = "0123456";

        assertEquals(name + " <" + number + ">", ContactsFormatter.format(name, number));
    }

    @Test
    public void testThatNumberCanBeExtractedFromContactString() {

        String name = "Brian Singer";
        String number = "0123456";
        String contactString = name + " <" + number + ">";

        PhoneNumberValidator mockPhoneNumberValidator = mock(PhoneNumberValidator.class);
        when(mockPhoneNumberValidator.validate(contactString)).thenReturn(false);

        ContactsFormatter contactsFormatter = new ContactsFormatter(mockPhoneNumberValidator);

        assertEquals(number, contactsFormatter.getNumberFromContact(contactString));
    }

    @Test
    public void testThatNumberIsReturnedIfContactNameIsNotIncludedInString() {
        String number = "0123456";

        PhoneNumberValidator mockPhoneNumberValidator = mock(PhoneNumberValidator.class);
        when(mockPhoneNumberValidator.validate(number)).thenReturn(true);

        ContactsFormatter contactsFormatter = new ContactsFormatter(mockPhoneNumberValidator);
        assertEquals(number, contactsFormatter.getNumberFromContact(number));
    }

}