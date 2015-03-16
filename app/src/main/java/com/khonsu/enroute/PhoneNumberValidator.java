package com.khonsu.enroute;

import android.text.TextUtils;
import android.util.Patterns;

public class PhoneNumberValidator implements FieldValidator {
	public boolean validate(String phoneNumber) {
		return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
	}
}
