package com.khonsu.enroute;

import com.khonsu.enroute.contactsautocomplete.Contact;

public class RecipientValidator implements FieldValidator {
	public boolean validate(String recipient) {
		return Contact.isValidString(recipient);
	}
}
