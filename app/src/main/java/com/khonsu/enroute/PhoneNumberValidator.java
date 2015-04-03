package com.khonsu.enroute;

import android.util.Patterns;

public class PhoneNumberValidator implements FieldValidator {
	public boolean validate(String phoneNumber) {
		return !phoneNumber.isEmpty() && Patterns.PHONE.matcher(phoneNumber).matches();
	}
}
