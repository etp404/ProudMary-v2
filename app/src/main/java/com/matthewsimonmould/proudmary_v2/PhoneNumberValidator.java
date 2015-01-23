package com.matthewsimonmould.proudmary_v2;

import android.text.TextUtils;
import android.util.Patterns;

public class PhoneNumberValidator {
	public static boolean isValidPhoneNumber(CharSequence phoneNumber) {
		if (!TextUtils.isEmpty(phoneNumber)) {
			return Patterns.PHONE.matcher(phoneNumber).matches();
		}
		return false;
	}
}
