package com.khonsu.enroute;

import com.google.gson.Gson;
import com.khonsu.enroute.google.datamodel.NoDurationInResponseException;
import com.khonsu.enroute.google.datamodel.ResponseBody;

import java.io.IOException;
import java.net.URL;

public class GoogleMapsDurationGetter {
	private UrlAccessor urlAccessor;

	public GoogleMapsDurationGetter(UrlAccessor urlAccessor) {
		this.urlAccessor = urlAccessor;
	}

	public String getEstimatedJourneyTime(String currentLat, String currentLong, String destination) throws UnableToGetEstimatedJourneyTimeException {
		try {
			URL url = GoogleMapsLinkGenerator.generateLinkForDirections(currentLat, currentLong, destination);
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