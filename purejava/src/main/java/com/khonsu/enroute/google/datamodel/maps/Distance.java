package com.khonsu.enroute.google.datamodel.maps;

public final class Distance {
	@SuppressWarnings("unused")
	private String text;

	public String getText() throws EstimateUnavailableException {
		if (text == null) {
			throw new EstimateUnavailableException();
		}
		return text;
	}
}
