package com.khonsu.enroute;

import java.util.List;

public interface AutoCompleterSuggestionsGetter {
    public List<String> getSuggestions(String location);
}
