package com.khonsu.enroute;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class AddressValidatorTest {
	@Test
	public void testThatCorrectAddressIsValidatedAsTrue() {
		String someAddress = "52 Some Valid Address";
		AddressValidator addressValidator = new AddressValidator(new FakeGooglePlacesAutocompleter(someAddress));
		assertTrue(addressValidator.validate(someAddress));
	}

	public class FakeGooglePlacesAutocompleter extends GooglePlacesAutocompleter {
		private final List<String> results;

		public FakeGooglePlacesAutocompleter(final String someAddress) {
			super(null);
			results = new ArrayList<String>(){{this.add(someAddress);}};
		}

		@Override
		public List<String> getSuggestions(String location) {
			return results;
		}
	}
}
