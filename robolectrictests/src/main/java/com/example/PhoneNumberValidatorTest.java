package com.example;

import com.matthewsimonmould.proudmary_v2.PhoneNumberValidator;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.assertFalse;

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
}
