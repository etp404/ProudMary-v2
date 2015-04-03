package com.khonsu.enroute;

import com.google.gson.Gson;
import com.khonsu.enroute.google.datamodel.places.PlacesAutoCompleteResponseBody;

import java.io.IOException;
import java.util.List;

public class GooglePlacesAutocompleter implements AutoCompleterSuggestionsGetter {
	private UrlAccessor urlAccessor;

	public GooglePlacesAutocompleter(UrlAccessor urlAccessor) {
		this.urlAccessor = urlAccessor;
	}

	public List<String> getSuggestions(String location) {
		String responseBodyAsJsonString = null;
		try {
			responseBodyAsJsonString = urlAccessor.getResponse(GoogleMapsLinkGenerator.generateLinkForAutocomplete(location));
		} catch (IOException e) {
			e.printStackTrace();
		}
		Gson gson = new Gson();
		PlacesAutoCompleteResponseBody placesAutoCompleteResponseBody = gson.fromJson(responseBodyAsJsonString, PlacesAutoCompleteResponseBody.class);

		return placesAutoCompleteResponseBody.getDescriptions();
	}
}
