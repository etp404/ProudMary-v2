package com.khonsu.enroute;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class AddressValidatorTest {

	private String validAddress = "52 Some Valid Address";
	private String invalidAddress = "52 Some invalid Address";

	@Test
	public void testThatCorrectAddressIsValidatedAsTrue() {
		AddressValidator addressValidator = new AddressValidator(new FakeGooglePlacesAutocompleter());
		assertTrue(addressValidator.validate(validAddress));

		ModeOfTransport.valueOf("BIKE");
	}

	@Test
	public void testThatAddressIsValidatedAsFalseIfNullResultsAreResultsFromTheAutocompleter() {
		AddressValidator addressValidator = new AddressValidator(new FakeGooglePlacesAutocompleter());
		assertFalse(addressValidator.validate("anything"));
	}

	@Test
	public void testThatAddressIsValidatedAsFalseIfZeroResultsAreResultsFromTheAutocompleter() {
		AddressValidator addressValidator = new AddressValidator(new FakeGooglePlacesAutocompleter());
		assertFalse(addressValidator.validate(invalidAddress));
	}

	public class FakeGooglePlacesAutocompleter extends GooglePlacesAutocompleter {
		private final Map<String, List<String>> queryToResults =
				new HashMap<String, List<String>>(){{
					this.put(validAddress, new ArrayList<String>() {{
						this.add(validAddress);
					}});
					this.put(invalidAddress, new ArrayList<String>());
				}};

		public FakeGooglePlacesAutocompleter() {
			super(null);
		}

		@Override
		public List<String> getSuggestions(String location) {
			return queryToResults.get(location);
		}
	}
}
