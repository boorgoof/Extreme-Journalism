package it.unipd.dei.dbdc.analysis.src_strategies.MapArraySplitAnalyzer;

import it.unipd.dei.dbdc.tools.ThreadPoolTools;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;
import it.unipd.dei.dbdc.analysis.interfaces.Analyzer;

import java.util.*;
import java.util.concurrent.*;

public class MapSplitAnalyzer implements Analyzer {

    @Override
    public ArrayList<OrderedEntryStringInt> mostPresent(List<UnitOfSearch> articles, int tot_words, Set<String> banned) throws IllegalArgumentException
    {
        if (articles == null)
        {
            throw new IllegalArgumentException("There are no article to analysis");
        }
        TreeMap<String, Integer> global_map = new TreeMap<>();

        //To access the global map, we need to guarantee mutual exclusion
        Semaphore mutex = new Semaphore(1);

        ExecutorService threadPool = ThreadPoolTools.getExecutor();
        List<Future<?>> futures = new ArrayList<>(articles.size());
        for (UnitOfSearch article : articles) {
            Future<?> f = threadPool.submit(new AnalyzerArticleThread(article, global_map, mutex));
            futures.add(f);
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                threadPool.shutdown();
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        threadPool.shutdown();

        // To order the couples, we need to have an order. That's why we use the OrderedEntries
        ArrayList<OrderedEntryStringInt> max = new ArrayList<>(tot_words);
        boolean null_banned = false;
        for (Map.Entry<String, Integer> element : global_map.entrySet()) {
            if (banned == null)
            {
                null_banned = true;
            }
            if (null_banned || !banned.contains(element.getKey()))
            {
                addOrdered(max, element, tot_words);
            }
        }
        return max;
    }

    private void addOrdered(ArrayList<OrderedEntryStringInt> vec, Map.Entry<String, Integer> entry, int tot_words) {

        OrderedEntryStringInt el = new OrderedEntryStringInt(entry);
        int vector_size = vec.size();

        //There are two possibilities:
        if (vector_size < tot_words) {
            //A. An element should be added because the vector is not long as it should
            int i = 0;

            //Check in what position the element should be added
            while (i < vector_size && vec.get(i).isMajorThan(el)) {
                i++;
            }

            //If it is major to at least one of the other elements
            if (i != vector_size) {
                OrderedEntryStringInt old = vec.get(i);
                vec.set(i, el);
                while ((i+1) < vector_size) {
                    OrderedEntryStringInt new_old = vec.get(i+1);
                    vec.set(i, old);
                    old = new_old;
                    i++;
                }
                //The new el that should be added is the one that is called old
                el = old;
            }

            vec.add(el);
        }
        else {
            //B. No element should be added, but only replaced. In this case, it is more efficient to check from the end of the List
            int i = tot_words - 1;
            while (i >= 0 && el.isMajorThan(vec.get(i))) {
                if (i + 1 < tot_words) {
                    vec.set(i + 1, vec.get(i));
                }
                i--;
            }
            if (i != tot_words - 1) {
                vec.set(i + 1, el);
            }
        }
    }
}

