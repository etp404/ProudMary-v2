package com.khonsu.enroute;

import java.util.List;

public interface AutocompleteSuggestor {
    public List<String> getSuggestions(String location);
}
