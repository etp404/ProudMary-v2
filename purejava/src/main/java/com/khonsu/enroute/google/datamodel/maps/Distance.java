package com.khonsu.enroute.google.datamodel.maps;

public final class Distance {
	@SuppressWarnings("unused")
	private String value;

	public String getValue() throws EstimateUnavailableException {
		if (value == null) {
			throw new EstimateUnavailableException();
		}
		return value;
	}
}
