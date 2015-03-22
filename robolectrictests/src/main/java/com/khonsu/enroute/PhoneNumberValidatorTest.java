package com.khonsu.enroute;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class PhoneNumberValidatorTest {
	@Test
	 public void testThatPhoneNumberValidatorRejectsNullPhoneNumber() {
		assertFalse(new PhoneNumberValidator().validate(null));
	}

	@Test
	public void testThatPhoneNumberValidatorRejectsEmptyPhoneNumber() {
		assertFalse(new PhoneNumberValidator().validate(""));
	}

	@Test
	public void testThatPhoneNumberValidatorAcceptsValidPhoneNumber() {
		assertTrue(new PhoneNumberValidator().validate("07791123456"));
	}

	@Test
	public void testThatPhoneNumberValidatorAcceptsInternationalPhoneNumber() {
		assertTrue(new PhoneNumberValidator().validate("+447791123456"));
	}

	@Test
	public void testThatPhoneNumberValidatorAcceptsValidPhoneNumberWithDashes() {
		assertTrue(new PhoneNumberValidator().validate("+44779-112-3456"));
	}

	@Test
	public void testThatPhoneNumberValidatorRejectsPhoneNumberWithLetters() {
		assertFalse(new PhoneNumberValidator().validate("9779-1A2-3B56"));
	}

	@Test
	public void testThatPhoneNumberValidatorRejectsPhoneNumberWithTooFewNumbers() {
		assertFalse(new PhoneNumberValidator().validate("42"));
	}
}
