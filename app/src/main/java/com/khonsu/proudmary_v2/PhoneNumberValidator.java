package com.khonsu.proudmary_v2;

import android.text.TextUtils;
import android.util.Patterns;

public class PhoneNumberValidator {
	public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
		return !TextUtils.isEmpty(phoneNumber) && Patterns.PHONE.matcher(phoneNumber).matches();
	}
}
