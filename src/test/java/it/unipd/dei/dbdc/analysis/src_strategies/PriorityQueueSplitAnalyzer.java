package it.unipd.dei.dbdc.analysis.src_strategies;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.analysis.interfaces.Analyzer;

import java.util.*;

//This is only a utility class that is used for the tests. It is not efficient.
public class PriorityQueueSplitAnalyzer implements Analyzer {
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

            //We use a negative number to obtain a max priority queue
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
