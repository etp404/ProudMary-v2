package com.khonsu.enroute;

import com.google.gson.Gson;
import com.khonsu.enroute.google.datamodel.places.PlacesAutoCompleteResponseBody;

import java.util.List;

public class GooglePlacesAutocompleter {
	private AutoCompleteAccessor autoCompleteAccessor;

	public GooglePlacesAutocompleter(AutoCompleteAccessor autoCompleteAccessor) {

		this.autoCompleteAccessor = autoCompleteAccessor;
	}

	public List<String> getSuggestions(String anyString) {
		String responseBodyAsJsonString = autoCompleteAccessor.getAutoCompleteForString(anyString);
		Gson gson = new Gson();
		PlacesAutoCompleteResponseBody placesAutoCompleteResponseBody = gson.fromJson(responseBodyAsJsonString, PlacesAutoCompleteResponseBody.class);

		return placesAutoCompleteResponseBody.getDescriptions();
	}
}
