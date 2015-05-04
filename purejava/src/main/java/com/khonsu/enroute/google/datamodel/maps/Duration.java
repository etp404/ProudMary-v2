package com.khonsu.enroute.google.datamodel.maps;

public class Duration {

	@SuppressWarnings("unused")
	private String text;

	public String getText() throws EstimateUnavailableException {
		if (text == null) {
			throw new EstimateUnavailableException();
		}
		return text;
	}
}
