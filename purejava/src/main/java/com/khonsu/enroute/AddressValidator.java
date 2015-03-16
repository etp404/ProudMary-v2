package com.khonsu.enroute;

import java.util.List;

public class AddressValidator implements FieldValidator {

	private GooglePlacesAutocompleter googlePlacesAutocompleter;

	public AddressValidator(GooglePlacesAutocompleter googlePlacesAutocompleter) {

		this.googlePlacesAutocompleter = googlePlacesAutocompleter;
	}

	public boolean validate(String someAddress) {
		List<String> autcompleterResults = googlePlacesAutocompleter.getSuggestions(someAddress);
		return  autcompleterResults!= null && autcompleterResults.size() > 0;
	}
}
