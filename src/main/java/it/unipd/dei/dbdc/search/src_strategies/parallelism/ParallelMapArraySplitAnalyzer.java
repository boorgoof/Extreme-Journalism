package it.unipd.dei.dbdc.search.src_strategies.parallelism;

import it.unipd.dei.dbdc.search.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.search.OrderedEntryStringInt;
import it.unipd.dei.dbdc.search.interfaces.Analyzer;

import java.util.*;
import java.util.concurrent.*;

public class ParallelMapArraySplitAnalyzer implements Analyzer {

    @Override
    public ArrayList<OrderedEntryStringInt> mostPresent(List<UnitOfSearch> articles, int tot_words, Set<String> banned)
    {
        TreeMap<String, Integer> global_map = new TreeMap<>();

        // To access the global map, we need to guarantee mutual exclusion
        Semaphore mutex = new Semaphore(1);

        // Parallel part
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Future<?>> futures = new ArrayList<>(articles.size());

        // Chiamiamo e mandiamo nella thread pool:
        for (UnitOfSearch article : articles) {
            Future<?> f = threadPool.submit(new AnalyzerArticleThread(article, global_map, mutex));
            futures.add(f);
        }

        // Aspettiamo che queste task finiscano. In questo caso non possono mandare eccezioni, in teoria:
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                // Avviene se è stato interrotto mentre aspettava o ha lanciato un'eccezione
                threadPool.shutdown();
                throw new IllegalStateException("Error in parallelism");
            }
        }
        threadPool.shutdown();

        // To order the couples, we need to have an Order
        ArrayList<OrderedEntryStringInt> max = new ArrayList<>(tot_words);
        for (Map.Entry<String, Integer> el : global_map.entrySet()) {
            addOrdered(max, el, banned, tot_words);
        }
        return max;
    }

    private void addOrdered(ArrayList<OrderedEntryStringInt> vec, Map.Entry<String, Integer> entry, Set<String> bannedWords, int tot_words) {
        if (bannedWords.contains(entry.getKey()))
        {
            return;
        }
        int vector_size = vec.size();

        OrderedEntryStringInt el = new OrderedEntryStringInt(entry);

        // Devo aggiungerlo per forza, si tratta solo di capire in che posizione
        if (vector_size < tot_words) {
            int i = 1;

            // Finché ci sono elementi e sono maggiori
            while (i < vector_size && vec.get(i - 1).isMajorThan(el)) {
                i++;
            }

            // Se sono arrivato alla fine del vettore
            if (i >= vector_size) {
                vec.add(el);
                return;
            }

            // Altrimenti rimpiazzo uno alla volta, con InsertionSort
            OrderedEntryStringInt old = vec.get(i - 1);
            vec.set(i - 1, el);
            i++;
            while (i < vector_size) {
                OrderedEntryStringInt new_old = vec.get(i);
                vec.set(i - 1, old);
                old = new_old;
                i++;
            }
            vec.add(old);
            return;
        }
        // Se non devo aggiungerlo per forza
        int i = tot_words-1;
        while (i >= 0 && el.isMajorThan(vec.get(i))) {
            if (i == tot_words-1) {
                i--;
                continue;
            }
            vec.set(i + 1, vec.get(i));
            i--;
        }
        if (i != tot_words-1) {
            vec.set(i + 1, el);
        }
    }
}

