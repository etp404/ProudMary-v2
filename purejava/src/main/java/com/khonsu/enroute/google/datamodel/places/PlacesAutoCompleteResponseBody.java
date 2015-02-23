
package com.khonsu.enroute.google.datamodel.places;

import java.util.ArrayList;
import java.util.List;

public class PlacesAutoCompleteResponseBody{
	@SuppressWarnings("unused")
	private List<Prediction> predictions;

	public List<String> getDescriptions() {
		List<String> descriptions = new ArrayList<>();
		for (Prediction prediction : predictions) {
			descriptions.add(prediction.getDescription());
		}
		return descriptions;
	}
}
