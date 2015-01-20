package com.matthewsimonmould.proudmary_v2;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;

public class GoogleMapsDurationGetter {
	private UrlAccessor urlAccessor;

	public GoogleMapsDurationGetter(UrlAccessor urlAccessor) {
		this.urlAccessor = urlAccessor;
	}

	public String getEstimatedJourneyTime(String currentLat, String currentLong, String destination) {

		URL url = GoogleMapsLinkGenerator.generateLinkForDirections(currentLong, currentLat, destination);
		try {
			JSONObject json = new JSONObject(urlAccessor.getResponse(url)); //TODO: should handle null pointers here.
			return json.getJSONArray("routes")
					.getJSONObject(0)
					.getJSONArray("legs")
					.getJSONObject(0)
					.getJSONObject("duration")
					.get("text").toString();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null; //TODO: Handle this.
	}
}
