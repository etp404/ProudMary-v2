package com.khonsu.enroute;

import java.text.DecimalFormat;

public final class DistanceFormatter {
	private static double METERS_TO_MILES = 0.000621371192;
	private static DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.#");
	private static final String MILES_FORMAT = "%s miles";

	public static String formatMetersAsMiles(String meters) {
		double metersAsDouble = Double.valueOf(meters);
		String miles = DECIMAL_FORMAT.format(METERS_TO_MILES * metersAsDouble);
		return String.format(MILES_FORMAT, miles);
	}
}
