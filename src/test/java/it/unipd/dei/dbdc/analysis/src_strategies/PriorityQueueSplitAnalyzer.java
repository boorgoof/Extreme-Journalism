package it.unipd.dei.dbdc.analysis.src_strategies;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.analysis.interfaces.Analyzer;

import java.util.*;

//This is only a utility class that is used for the tests. It is not efficient
public class PriorityQueueSplitAnalyzer implements Analyzer {

    @Override
    public ArrayList<OrderedEntryStringInt> mostPresent(List<UnitOfSearch> articles, int tot_words, Set<String> banned) throws IllegalArgumentException
    {
        if (articles == null)
        {
            throw new IllegalArgumentException("There are no article to analysis");
        }
        TreeMap<String, Integer> global_map = new TreeMap<>();

        for (UnitOfSearch article : articles) {

            String complete_article = article.obtainText();
            String[] tokens = complete_article.split("[^a-zA-Z]+");

            HashSet<String> local_map = new HashSet<>();
            for (String tok : tokens) {
                local_map.add(tok.toLowerCase());
            }

            for (String elem : local_map) {
                Integer val = global_map.get(elem);
                if (val == null) {
                    global_map.put(elem, 1);
                } else {
                    global_map.replace(elem, val + 1);
                }
            }
        }

        //This part can be optimized
        PriorityQueue<OrderedEntryComparable> pq = new PriorityQueue<>();

        for (Map.Entry<String, Integer> entry : global_map.entrySet()) {
            if (banned == null || !banned.contains(entry.getKey()))
            {
                pq.offer(new OrderedEntryComparable(entry));
            }
            if (pq.size() > tot_words) {
                pq.poll();
            }
        }

        ArrayList<OrderedEntryStringInt> max = new ArrayList<>(pq.size());
        while (!pq.isEmpty()) {
            max.add(pq.poll());
        }

        //Reverse the ArrayList
        ArrayList<OrderedEntryStringInt> real = new ArrayList<>(max.size());
        for (int i = 0; i<max.size(); i++)
        {
            real.add(max.get(max.size()-i-1));
        }

        return real;
    }

}
