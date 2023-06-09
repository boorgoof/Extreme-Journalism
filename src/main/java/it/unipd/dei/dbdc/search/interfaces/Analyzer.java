package it.unipd.dei.dbdc.search.interfaces;

import it.unipd.dei.dbdc.deserialization.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.search.OrderedEntryStringInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Analyzer {
    ArrayList<OrderedEntryStringInt> mostPresent(List<UnitOfSearch> articles, int tot_count, HashMap<String, Integer> banned_words);
}
