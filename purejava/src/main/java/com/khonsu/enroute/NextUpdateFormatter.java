package com.khonsu.enroute;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class NextUpdateFormatter {
	private static String NEXT_UPDATE_FORMAT = "Next update due: %s.";
	private static DateFormat DATE_FORMAT = new SimpleDateFormat("kk:mm");

	public static String format(long timeInMillis) {
		return String.format(NEXT_UPDATE_FORMAT, DATE_FORMAT.format(timeInMillis));
	}
}
