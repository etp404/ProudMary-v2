package com.khonsu.enroute.contactsautocomplete;

import android.text.TextUtils;

import com.khonsu.enroute.PhoneNumberValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Contact {
	private final static Pattern CONTACT_PATTERN = Pattern.compile("(.*) <(.*)>");
	private final static String FORMAT = "%s <%s>";
	private String name;
	private String number;

	public Contact(String name, String number) {
		this.name = name;
		this.number = number;
	}

	public String toString() {
		if (name == null) {
			return number;
		}
		return String.format(FORMAT, name, number);
	}

	public static Contact fromString(String contactString) {
		Matcher contactMatcher = CONTACT_PATTERN.matcher(contactString);
		if (contactMatcher.matches()) {
			return new Contact(contactMatcher.group(1), contactMatcher.group(2));
		}
		return new Contact(null, contactString);
	}

	public String getNumber() {
		return number;
	}

	public static boolean isValidString(String candidateContact) {
		return !TextUtils.isEmpty(candidateContact) && (PhoneNumberValidator.isValidPhoneNumber(candidateContact) || CONTACT_PATTERN.matcher(candidateContact).matches());
	}
}
