package com.khonsu.enroute.google.datamodel.maps;

import java.util.List;

public class Route {
	@SuppressWarnings("unused")
	private List<Leg> legs;

	public String getDurationText() throws EstimateUnavailableException {
		if (legs == null || legs.isEmpty()) {
			throw new EstimateUnavailableException();
		}
		return legs.get(0).getDurationText();
	}

	public String getDistanceText() throws EstimateUnavailableException {
		if (legs == null || legs.isEmpty()) {
			throw new EstimateUnavailableException();
		}
		return legs.get(0).getDistanceTest();
	}
}
