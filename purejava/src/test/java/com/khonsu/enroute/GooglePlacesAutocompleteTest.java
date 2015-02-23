package com.khonsu.enroute;

import org.junit.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class GooglePlacesAutocompleteTest {

	@Test
	public void testThatJsonCanBeParsed() {
		GooglePlacesAutocompleter googlePlacesAutocompleter = new GooglePlacesAutocompleter(new FakeAutoCompleteAccessor());
		List<String> suggestions = googlePlacesAutocompleter.getSuggestions("place");

		assertEquals(5, suggestions.size());
		assertEquals("Filey, United Kingdom", suggestions.get(0));

	}

	private class FakeAutoCompleteAccessor extends UrlAccessor {
		@Override
		public String getResponse(URL url) {
			try {
				if (url.equals(GoogleMapsLinkGenerator.generateLinkForAutocomplete("place"))) {
					return "{\"predictions\" : [{\"description\" : \"Filey, United Kingdom\",\"id\" : \"d7cef65b1eaaad925d504e348c905376c591b5d9\",\"matched_substrings\" : [{\"length\" : 3,\"offset\" : 0}],\"place_id\" : \"ChIJkbm3n8FIf0gRgvuUvHrzMec\",\"reference\" : \"CjQtAAAAYjJL7unpTkyp8uUJBEq5wN8JQ9nj-y7wS1CpmO8lyT6uBdaJzGodMhzchR7XFp4MEhCgz8eirjMyBaf8TihlSAbmGhQJFL-vryiLD5rPt02UH31vISGjcw\",\"terms\" : [{\"offset\" : 0,\"value\" : \"Filey\"},{\"offset\" : 7,\"value\" : \"United Kingdom\"}],\"types\" : [ \"locality\", \"political\", \"geocode\" ]},{\"description\" : \"Filton, United Kingdom\",\"id\" : \"c33d987005eacde528522b0f98c2ae3e0cf8022a\",\"matched_substrings\" : [{\"length\" : 3,\"offset\" : 0}],\"place_id\" : \"ChIJgVaRRJWRcUgRCwfNl5Ms59Y\",\"reference\" : \"CjQuAAAAaR6SXQAo2rGCZYxWmF8ZwjIJz3iavM_MAYpBhq8VAJEvuwVL7Zu3ES1TkupfST8ZEhBgWVKiNKj6U0JorIdIAQ_LGhQtpKbut0zzrq-Zu8dIZEL51UAI3A\",\"terms\" : [{\"offset\" : 0,\"value\" : \"Filton\"},{\"offset\" : 8,\"value\" : \"United Kingdom\"}],\"types\" : [ \"locality\", \"political\", \"geocode\" ]},{\"description\" : \"Fillongley, United Kingdom\",\"id\" : \"f8bb32a566c6334e1af2e7db8b9ea9ff0cef6e31\",\"matched_substrings\" : [{\"length\" : 3,\"offset\" : 0}],\"place_id\" : \"ChIJQyPjJdGycEgRJGvJ1gjDiGc\",\"reference\" : \"CkQyAAAATr9g_WIZBUwNvlsSgmA-OSf6I74G_nKcf03lYEMtSb04pi4ApdI8hvOgELX1yDuva3EZCW-DQ8MzT1neiqTh6xIQp3QEU_qc46HarcUdsV6W_RoUnLlKfIYq9klJMrGkC5NrUEJe6-c\",\"terms\" : [{\"offset\" : 0,\"value\" : \"Fillongley\"},{\"offset\" : 12,\"value\" : \"United Kingdom\"}],\"types\" : [ \"locality\", \"political\", \"geocode\" ]},{\"description\" : \"Filkins, United Kingdom\",\"id\" : \"6384d5e4bb367aef4d3768372424bc64be51010c\",\"matched_substrings\" : [{\"length\" : 3,\"offset\" : 0}],\"place_id\" : \"ChIJOU9xCM82cUgRO8GYpz4CMTg\",\"reference\" : \"CjQvAAAAtGFQ_bTT5ql6SB_ncphriBwzoxWY54Q3l_73lLLnExo1s1NqJo8YVYkD30Lvos8gEhAHIN0tMO79tPT41G4ebXgsGhTfmgFgV1RTLG8GbBexWxhZst9aAw\",\"terms\" : [{\"offset\" : 0,\"value\" : \"Filkins\"},{\"offset\" : 9,\"value\" : \"United Kingdom\"}],\"types\" : [ \"locality\", \"political\", \"geocode\" ]},{\"description\" : \"Filton Avenue, Horfield, Avon, United Kingdom\",\"id\" : \"b9645d7cda1b065632c29fff6da86301bcb5d657\",\"matched_substrings\" : [{\"length\" : 3,\"offset\" : 0}],\"place_id\" : \"Ei1GaWx0b24gQXZlbnVlLCBIb3JmaWVsZCwgQXZvbiwgVW5pdGVkIEtpbmdkb20\",\"reference\" : \"CkQxAAAAfam2tnbLerz2L0yffN8gr890Qh7s58DjRhVDzVXpZB8OIy9gjwoCdPt9en7nq_y3gvy94G-uqLPW2HLd2-F4zBIQLgfquhQO-YmRHVcNJNDapRoU8Gtr4alAb0bKho6F62UmjTkkLow\",\"terms\" : [{\"offset\" : 0,\"value\" : \"Filton Avenue\"},{\"offset\" : 15,\"value\" : \"Horfield\"},{\"offset\" : 25,\"value\" : \"Avon\"},{\"offset\" : 31,\"value\" : \"United Kingdom\"}],\"types\" : [ \"route\", \"geocode\" ]}],\"status\" : \"OK\"}";
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			return null;
		}
	}
}
