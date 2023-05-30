package it.unipd.dei.dbdc.Search_terms;

import it.unipd.dei.dbdc.Deserializers.Article;

import java.util.ArrayList;
import java.util.List;

public interface Analyzer<T> {
    ArrayList<MapEntry> mostPresent(List<T> articles);
}
