package com.khonsu.enroute;

import android.text.TextUtils;
import android.util.Patterns;

public class PhoneNumberValidator {
	public boolean isValidPhoneNumber(CharSequence phoneNumber) {
		return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
	}
}
