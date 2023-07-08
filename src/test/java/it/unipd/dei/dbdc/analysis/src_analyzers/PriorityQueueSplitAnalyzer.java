package it.unipd.dei.dbdc.analysis.src_analyzers;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;
import it.unipd.dei.dbdc.analysis.interfaces.Analyzer;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.analysis.src_analyzers.MapSplitAnalyzer.MapSplitAnalyzer;
import it.unipd.dei.dbdc.analysis.src_analyzers.MapSplitAnalyzer.MapSplitAnalyzerTest;

import java.util.*;

/**
 * This is only a utility class that is used for the tests. It is not intended to be efficient,
 * but to be right. It uses a {@link PriorityQueue} filled with {@link OrderedEntryComparable},
 * so the order is done by the standard library of java.
 * It is also an example of how to implement a different strategy to analyze articles.
 * It is tested, along with {@link MapSplitAnalyzer},
 * in {@link MapSplitAnalyzerTest}.
 */
public class PriorityQueueSplitAnalyzer implements Analyzer {

    /**
     * Default constructor of the class. It is the only constructor, as there are
     * no fields to give value to, so it doesn't do anything.
     * This is required as the interface {@link Analyzer} can only give non-static
     * methods to be implemented by other classes.
     *
     */
    public PriorityQueueSplitAnalyzer() {}

    /**
     * The main function, which accepts a {@link List} of {@link UnitOfSearch} and returns the most important
     * terms of this list as an {@link List} of {@link OrderedEntryStringInt}.
     * The most important terms are the one that appear in the most number of articles, and if two terms appear
     * in the same amount of articles, the one which is alphabetically precedent is the most important one.
     * It uses the order defined in {@link OrderedEntryComparable}
     *
     * @param articles A {@link List} of {@link UnitOfSearch} to search into.
     * @param tot_words The number of words we want the returned {@link List} to contain. If there are not enough words, it will contain only the possible ones.
     * @param banned A {@link Set} of words that should not be counted. If null or empty, all the words will be counted.
     * @return An {@link List} of {@link OrderedEntryStringInt} containing the most important terms of the articles
     * @throws IllegalArgumentException If the {@link UnitOfSearch} were not initialized.
     */
    @Override
    public ArrayList<OrderedEntryStringInt> mostPresent(List<UnitOfSearch> articles, int tot_words, Set<String> banned) throws IllegalArgumentException
    {
        if (articles == null || articles.isEmpty())
        {
            throw new IllegalArgumentException("There are no article to analysis");
        }

        TreeMap<String, Integer> global_map = new TreeMap<>();

        for (UnitOfSearch article : articles) {

            String complete_article = article.obtainText();
            String[] tokens = complete_article.split("[^a-zA-Z]+");

            HashSet<String> local_map = new HashSet<>();
            for (String tok : tokens) {
                if (!tok.isEmpty()) {
                    local_map.add(tok.toLowerCase());
                }
            }

            //The order is defined in OrderedEntryComparable
            for (String elem : local_map) {
                Integer val = global_map.get(elem);
                if (val == null) {
                    global_map.put(elem, 1);
                } else {
                    global_map.replace(elem, (val + 1));
                }
            }
        }

        PriorityQueue<OrderedEntryComparable> pq = new PriorityQueue<>();
        for (Map.Entry<String, Integer> entry : global_map.entrySet()) {
            if (banned == null || !banned.contains(entry.getKey()))
            {
                pq.offer(new OrderedEntryComparable(entry));
            }
        }

        ArrayList<OrderedEntryStringInt> max = new ArrayList<>(tot_words);
        for (int i = 0; i<tot_words && !pq.isEmpty(); i++) {
            max.add(new OrderedEntryStringInt(pq.poll()));
        }

        return max;
    }
}