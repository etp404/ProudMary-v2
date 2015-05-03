package com.khonsu.enroute.google.datamodel.maps;

public class Leg {

	@SuppressWarnings("unused")
	private Duration duration;
	private Distance distance;

	public String getDurationText() throws EstimateUnavailableException {
		if (duration == null) {
			throw new EstimateUnavailableException();
		}
		return duration.getText();
	}

	public String getDistanceTest() throws EstimateUnavailableException {
		if (distance == null) {
			throw new EstimateUnavailableException();
		}
		return distance.getText();	}
}
