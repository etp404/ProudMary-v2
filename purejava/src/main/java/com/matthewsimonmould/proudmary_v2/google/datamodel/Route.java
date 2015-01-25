package com.matthewsimonmould.proudmary_v2.google.datamodel;

import java.util.List;

public class Route {
	@SuppressWarnings("unused")
	private List<Leg> legs;

	public String getDurationText() throws NoDurationInResponseException {
		if (legs == null || legs.isEmpty()) {
			throw new NoDurationInResponseException();
		}
		return legs.get(0).getDurationText();
	}
}
