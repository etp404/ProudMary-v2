package com.khonsu.enroute.contactsautocomplete;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ContactSuggesterTest {

	private Contact albertContact = new Contact("Albert", "10288484");
	private Contact valjean;

	@Test
	public void testThatExpectedContactsAreReturnedAccordingToInput() {
		ContactsAccessor contactsAccessor = new FakeContactsAccessor();
		ContactSuggester contactSuggester = new ContactSuggester(contactsAccessor);
		List<String> suggestions = contactSuggester.getSuggestions("Al");
		assertEquals(2, suggestions.size());
		assertEquals(albertContact.toString(), suggestions.get(0));
	}

	@Test
	public void testThatExpectedContactsAreReturnedAccordingToInputInNonCaseSensitiveManner() {
		ContactsAccessor contactsAccessor = new FakeContactsAccessor();
		ContactSuggester contactSuggester = new ContactSuggester(contactsAccessor);
		List<String> suggestions = contactSuggester.getSuggestions("al");
		assertEquals(2, suggestions.size());
		assertEquals(albertContact.toString(), suggestions.get(0));
	}

	@Test
	public void testThatExpectedContactsAreReturnedAccordingToInputIByNumber() {
		ContactsAccessor contactsAccessor = new FakeContactsAccessor();
		ContactSuggester contactSuggester = new ContactSuggester(contactsAccessor);
		List<String> suggestions = contactSuggester.getSuggestions("24601");
		assertEquals(1, suggestions.size());
		assertEquals(valjean.toString(), suggestions.get(0));
	}

	private class FakeContactsAccessor extends ContactsAccessor {
		public FakeContactsAccessor() {
			super(null);
		}

		@Override
		public List<Contact> getContacts() {
			return new ArrayList<Contact>(){{
				add(albertContact);
				add(new Contact("Algenon", "1021939484"));
				add(new Contact("Brian", "10288484"));
				valjean = new Contact("Adams", "10246018484");
				add(valjean);
			}};
		}
	}
}
