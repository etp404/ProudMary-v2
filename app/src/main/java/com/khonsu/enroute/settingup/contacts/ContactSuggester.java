package com.khonsu.enroute.settingup.contacts;

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

		List<Contact> contacts = contactsAccessor.getCachedContacts();

		for (Contact contact : contacts) {
			String stringContact = contact.toString();
			String sanitisedContactString = stringContact.toLowerCase().replaceAll("[-\\s]", "");
			String sanitisedInputString = input.toLowerCase().replaceAll("[-\\s]", "");
			if (sanitisedContactString.contains(sanitisedInputString)) {
				suggestions.add(stringContact);
			}
		}
		return suggestions;	}
}
