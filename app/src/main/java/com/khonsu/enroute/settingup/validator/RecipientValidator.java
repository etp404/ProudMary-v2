package com.khonsu.enroute.settingup.validator;

import com.khonsu.enroute.FieldValidator;
import com.khonsu.enroute.settingup.contacts.Contact;

public class RecipientValidator implements FieldValidator {
	public boolean validate(String recipient) {
		return Contact.isValidString(recipient);
	}
}
