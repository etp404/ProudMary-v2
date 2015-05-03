package com.khonsu.enroute.google.datamodel.maps;

import java.util.List;

public class ResponseBody {
	@SuppressWarnings("unused")
	private List<Route> routes;

	public String getDurationText() throws EstimateUnavailableException {
		if (routes == null || routes.isEmpty()) {
			throw new EstimateUnavailableException();
		}
		return routes.get(0).getDurationText();
	}

	public String getDistanceValue() throws EstimateUnavailableException {
		if (routes == null || routes.isEmpty()) {
			throw new EstimateUnavailableException();
		}
		return routes.get(0).getDistanceValue();
	}
}
