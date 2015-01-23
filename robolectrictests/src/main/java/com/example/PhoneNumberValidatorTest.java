package com.example;

import com.matthewsimonmould.proudmary_v2.PhoneNumberValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class PhoneNumberValidatorTest {
	@Test
	 public void testThatPhoneNumberValidatorRejectsNullPhoneNumber() {
		assertFalse(PhoneNumberValidator.isValidPhoneNumber(null));
	}

	@Test
	public void testThatPhoneNumberValidatorRejectsEmptyPhoneNumber() {
		assertFalse(PhoneNumberValidator.isValidPhoneNumber(""));
	}

	@Test
	public void testThatPhoneNumberValidatorAcceptsValidPhoneNumber() {
		assertTrue(PhoneNumberValidator.isValidPhoneNumber("07791123456"));
	}

	@Test
	public void testThatPhoneNumberValidatorAcceptsInternationalPhoneNumber() {
		assertTrue(PhoneNumberValidator.isValidPhoneNumber("+447791123456"));
	}

	@Test
	public void testThatPhoneNumberValidatorAcceptsValidPhoneNumberWithDashes() {
		assertTrue(PhoneNumberValidator.isValidPhoneNumber("+44779-112-3456"));
	}

	@Test
	public void testThatPhoneNumberValidatorRejectsPhoneNumberWithLetters() {
		assertFalse(PhoneNumberValidator.isValidPhoneNumber("9779-1A2-3B56"));
	}

	@Test
	public void testThatPhoneNumberValidatorRejectsPhoneNumberWithTooFewNumbers() {
		assertFalse(PhoneNumberValidator.isValidPhoneNumber("42"));
	}
}
