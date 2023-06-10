package it.unipd.dei.dbdc.search.src_strategies.parallelism;

import it.unipd.dei.dbdc.search.interfaces.UnitOfSearch;

import java.util.HashSet;
import java.util.TreeMap;
import java.util.concurrent.Semaphore;

public class AnalyzerArticleThread implements Runnable {
    TreeMap<String, Integer> global_map;
    UnitOfSearch article;
    Semaphore mutex;
    public AnalyzerArticleThread(UnitOfSearch art, TreeMap<String, Integer> map, Semaphore sem)
    {
        article = art;
        global_map = map;
        mutex = sem;
    }

    public void run()
    {
        HashSet<String> local_map = new HashSet<>();
        // Prendo l'articolo e faccio lo split
        String articolo_completo = article.obtainText();

        // TODO: prende ancora il carattere null, non so perche'
        String[] tokens = articolo_completo.split("[^a-zA-Z]+");

        // Inserisco tutti i tokens in una mappa locale.
        for (String tok : tokens) {
            if (!local_map.contains(tok)) {
                local_map.add(tok.toLowerCase());
            }
        }

        // Inserisco i tokens di questo articolo nella mappa globale
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
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
