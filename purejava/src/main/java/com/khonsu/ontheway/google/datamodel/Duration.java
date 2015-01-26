package com.khonsu.ontheway.google.datamodel;

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
