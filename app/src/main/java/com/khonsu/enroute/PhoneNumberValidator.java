package com.khonsu.enroute;

import android.util.Patterns;

public class PhoneNumberValidator {
	public static boolean isValidPhoneNumber(String recipient) {
		return Patterns.PHONE.matcher(recipient).matches();
	}
}
