package com.khonsu.enroute.google.datamodel.maps;

public class Leg {

	@SuppressWarnings("unused")
	private Duration duration;

	public String getDurationText() throws NoDurationInResponseException {
		if (duration == null) {
			throw new NoDurationInResponseException();
		}
		return duration.getText();
	}
}
