package com.khonsu.enroute;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
public class RecipientValidatorTest {
	@Test
	 public void testThatRecipientValidatorRejectsNullPhoneNumber() {
		assertFalse(new RecipientValidator().validate(null));
	}

	@Test
	public void testThatRecipientValidatorRejectsEmptyPhoneNumber() {
		assertFalse(new RecipientValidator().validate(""));
	}

	@Test
	public void testThatRecipientValidatorAcceptsValidPhoneNumber() {
		assertTrue(new RecipientValidator().validate("07791123456"));
	}

	@Test
	public void testThatRecipientValidatorAcceptsInternationalPhoneNumber() {
		assertTrue(new RecipientValidator().validate("+447791123456"));
	}

	@Test
	public void testThatRecipientValidatorAcceptsValidPhoneNumberWithDashes() {
		assertTrue(new RecipientValidator().validate("+44779-112-3456"));
	}

	@Test
	public void testThatRecipientValidatorRejectsPhoneNumberWithLetters() {
		assertFalse(new RecipientValidator().validate("9779-1A2-3B56"));
	}

	@Test
	public void testThatRecipientValidatorRejectsPhoneNumberWithTooFewNumbers() {
		assertFalse(new RecipientValidator().validate("42"));
	}


	@Test
	public void testThatContactWithNameCanBeValidated() {
		assertTrue(new RecipientValidator().validate("Tim Burton <555 5555>"));
	}

	@Test
	public void testThatWeirdStringCanBeValidatedCorrectly() {
		TestCase.assertFalse(new RecipientValidator().validate("Tim Bu 5555>"));
	}
}
