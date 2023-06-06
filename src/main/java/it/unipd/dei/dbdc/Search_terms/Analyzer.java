package it.unipd.dei.dbdc.Search_terms;

import it.unipd.dei.dbdc.Deserializers.Serializable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public interface Analyzer {
    ArrayList<MapEntrySI> mostPresent(List<Serializable> articles, int tot_count, HashMap<String, Integer> banned_words);
}
