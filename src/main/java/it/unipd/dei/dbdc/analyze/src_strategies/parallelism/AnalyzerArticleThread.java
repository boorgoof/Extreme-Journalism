package it.unipd.dei.dbdc.analyze.src_strategies.parallelism;

import it.unipd.dei.dbdc.analyze.interfaces.UnitOfSearch;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

public class AnalyzerArticleThread implements Runnable {
    private final TreeMap<String, Integer> global_map;
    private final UnitOfSearch article;
    private final Semaphore mutex;

    public AnalyzerArticleThread(UnitOfSearch art, TreeMap<String, Integer> map, Semaphore sem)
    {
        article = art;
        global_map = map;
        mutex = sem;
    }

    @Override
    public void run() throws IllegalArgumentException
    {
        //We take all the words of the article and split them.
        //Everything that has not a letter is not a word.
        String complete_article = article.obtainText();
        String[] tokens = complete_article.split("[^a-zA-Z]+");

        HashSet<String> local_map = new HashSet<>();
        for (String tok : tokens) {
            local_map.add(tok.toLowerCase());
        }

        //Critical section
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            throw new IllegalArgumentException("The analysis of the article was interrupted because of an error during the mutual exclusion");
        }
        for (String elem : local_map)
        {
            Integer val = global_map.get(elem);
            if (val == null) {
                global_map.put(elem, 1);
            } else {
                global_map.replace(elem, val + 1);
            }
        }
        mutex.release();
    }
}
