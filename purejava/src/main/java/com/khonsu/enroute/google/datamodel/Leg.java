package com.khonsu.enroute.google.datamodel;

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
