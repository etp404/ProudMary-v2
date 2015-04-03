package com.khonsu.enroute;

public class Contact {
	private final static String FORMAT = "%s <%s>";
	private String name;
	private String number;

	public Contact(String name, String number) {
		this.name = name;
		this.number = number;
	}

	public String toString() {
		return String.format(FORMAT, name, number);
	}
}
