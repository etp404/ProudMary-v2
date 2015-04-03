package com.khonsu.enroute.contactsautocomplete;

import com.khonsu.enroute.AutoCompleterSuggestionsGetter;

import java.util.ArrayList;
import java.util.List;

public class ContactSuggester implements AutoCompleterSuggestionsGetter {
    private final ContactsAccessor contactsAccessor;

    public ContactSuggester(ContactsAccessor contactsAccessor) {
        this.contactsAccessor = contactsAccessor;
    }

    @Override
    public List<String> getSuggestions(String input) {
        List<String> suggestions = new ArrayList<>();

        List<String> contacts = contactsAccessor.getContacts();

        for (String contact : contacts) {
            if (contact.contains(input)) {
                suggestions.add(contact);
            }
        }
        return suggestions;
    }

}
