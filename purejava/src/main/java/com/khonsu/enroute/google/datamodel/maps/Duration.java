package com.khonsu.enroute.google.datamodel.maps;

public class Duration {

	@SuppressWarnings("unused")
	private String text;

	public String getText() throws NoDurationInResponseException {
		if (text == null) {
			throw new NoDurationInResponseException();
		}
		return text;
	}
}
