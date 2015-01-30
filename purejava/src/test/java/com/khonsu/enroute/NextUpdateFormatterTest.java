package com.khonsu.enroute;

import junit.framework.TestCase;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class NextUpdateFormatterTest extends TestCase {

	public static void testFormatter() {
		Calendar cal = GregorianCalendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 14);
		cal.set(Calendar.MINUTE, 19);

		assertEquals("Next update will be sent at: 14:19.", NextUpdateFormatter.format(cal.getTimeInMillis()));
	}
}