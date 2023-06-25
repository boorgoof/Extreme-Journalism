package it.unipd.dei.dbdc.analysis.interfaces;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public interface Analyzer {
    ArrayList<OrderedEntryStringInt> mostPresent(List<UnitOfSearch> articles, int tot_count, Set<String> banned_words);
}
