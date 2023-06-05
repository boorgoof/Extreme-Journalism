package it.unipd.dei.dbdc.Search_terms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Analyzer<T> {
    ArrayList<MapEntrySI> mostPresent(List<T> articles, int tot_count, HashMap<String, Integer> banned);
}
