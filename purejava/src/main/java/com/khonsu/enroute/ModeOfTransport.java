package com.khonsu.enroute;

public enum ModeOfTransport {
	WALKING, BIKE, CAR;

	private static ModeOfTransport DEFAULT = ModeOfTransport.CAR;

	public static ModeOfTransport getEnum(String string) {
		for (ModeOfTransport value : ModeOfTransport.values()) {
			if (value.toString().equals(string)) {
				return value;
			}
		}
		return DEFAULT;
	}
}
