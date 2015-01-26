package com.khonsu.ontheway.google.datamodel;

import java.util.List;

public class ResponseBody {
	@SuppressWarnings("unused")
	private List<Route> routes;

	public String getDurationText() throws NoDurationInResponseException {
		if (routes == null || routes.isEmpty()) {
			throw new NoDurationInResponseException();
		}
		return routes.get(0).getDurationText();
	}
}