package com.khonsu.enroute;

import android.util.SparseArray;

import com.google.gson.Gson;
import com.khonsu.enroute.google.datamodel.maps.NoDurationInResponseException;
import com.khonsu.enroute.google.datamodel.maps.ResponseBody;

import java.io.IOException;
import java.net.URL;

public class GoogleMapsDurationGetter {
	private final static SparseArray<String> MODE_OF_TRANSPORT_ID_TO_GOOGLE_STRING = new SparseArray<String>(){{
		put(R.id.mode_of_travel_car, "driving");
		put(R.id.mode_of_travel_bike, "bicycling");
		put(R.id.mode_of_travel_walking, "walking");
	}};

	private UrlAccessor urlAccessor;

	public GoogleMapsDurationGetter(UrlAccessor urlAccessor) {
		this.urlAccessor = urlAccessor;
	}

	public String getEstimatedJourneyTime(String currentLat, String currentLong, String destination, int mode) throws UnableToGetEstimatedJourneyTimeException {
		try {
			String modeAsGoogleString = MODE_OF_TRANSPORT_ID_TO_GOOGLE_STRING.get(mode);
			URL url = GoogleMapsLinkGenerator.generateLinkForDirections(currentLat, currentLong, destination, modeAsGoogleString);
			Gson gson = new Gson();
			String responseBodyAsJsonString = urlAccessor.getResponse(url);
			ResponseBody responseBody = gson.fromJson(responseBodyAsJsonString, ResponseBody.class);
			return responseBody.getDurationText();
		} catch (NoDurationInResponseException | IOException e) {
			e.printStackTrace();
		}
		throw new UnableToGetEstimatedJourneyTimeException();
	}
}
