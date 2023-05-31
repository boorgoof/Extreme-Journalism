package it.unipd.dei.dbdc.Search_terms;

import java.util.ArrayList;
import java.util.List;

public interface Analyzer<T> {
    ArrayList<MapEntrySI> mostPresent(List<T> articles);
    void outFile(ArrayList<MapEntrySI> max, String outFilePath);
}
