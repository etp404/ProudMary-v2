package com.khonsu.enroute;

import com.google.gson.Gson;
import com.khonsu.enroute.google.datamodel.maps.NoDurationInResponseException;
import com.khonsu.enroute.google.datamodel.maps.ResponseBody;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class GoogleMapsDurationGetter {
	private final static Map<ModeOfTransport, String> MODE_OF_TRANSPORT_ID_TO_GOOGLE_STRING = new HashMap<ModeOfTransport, String>(){{
		put(ModeOfTransport.CAR, "driving");
		put(ModeOfTransport.BIKE, "bicycling");
		put(ModeOfTransport.WALKING, "walking");
	}};

	private UrlAccessor urlAccessor;

	public GoogleMapsDurationGetter(UrlAccessor urlAccessor) {
		this.urlAccessor = urlAccessor;
	}

	public String getEstimatedJourneyTime(String currentLat, String currentLong, String destination, ModeOfTransport mode) throws UnableToGetEstimatedJourneyTimeException {
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

	public Estimate getJourneyEstimate(String currentLat, String currentLong, String destination, ModeOfTransport mode) throws UnableToGetEstimatedJourneyTimeException {
		try {
			String modeAsGoogleString = MODE_OF_TRANSPORT_ID_TO_GOOGLE_STRING.get(mode);
			URL url = GoogleMapsLinkGenerator.generateLinkForDirections(currentLat, currentLong, destination, modeAsGoogleString);
			Gson gson = new Gson();
			String responseBodyAsJsonString = urlAccessor.getResponse(url);
			ResponseBody responseBody = gson.fromJson(responseBodyAsJsonString, ResponseBody.class);
			return new Estimate(responseBody.getDurationText(), null);
		} catch (NoDurationInResponseException | IOException e) {
			e.printStackTrace();
		}
		throw new UnableToGetEstimatedJourneyTimeException();
	}
}
