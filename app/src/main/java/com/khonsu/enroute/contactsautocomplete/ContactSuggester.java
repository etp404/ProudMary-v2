package com.khonsu.enroute.contactsautocomplete;

import com.khonsu.enroute.AutocompleteSuggestor;

import java.util.ArrayList;
import java.util.List;

public class ContactSuggester implements AutocompleteSuggestor {
	private final ContactsAccessor contactsAccessor;

	public ContactSuggester(ContactsAccessor contactsAccessor) {
		this.contactsAccessor = contactsAccessor;
	}

	@Override
	public List<String> getSuggestions(String input) {
		List<String> suggestions = new ArrayList<>();

		List<Contact> contacts = contactsAccessor.getContacts();

		for (Contact contact : contacts) {
			String stringContact = contact.toString();
			if (stringContact.toLowerCase().contains(input.toLowerCase())) {
				suggestions.add(stringContact);
			}
		}
		return suggestions;	}
}
