package it.unipd.dei.dbdc.Search_terms;

import it.unipd.dei.dbdc.Deserializers.Article;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APICaller;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

public class AnalyzeArticleThread implements Runnable {
    TreeMap<String, Integer> global_map;
    Article article;
    Semaphore mutex;
    public AnalyzeArticleThread(Article art, TreeMap<String, Integer> map, Semaphore sem)
    {
        article = art;
        global_map = map;
        mutex = sem;
    }
    public void run()
    {
        TreeMap<String, Integer> local_map = new TreeMap<>();
        // Prendo l'articolo e faccio lo split
        String articolo_completo = article.getTitle() + " " + article.getBody();

        // FIXME: prende ancora il carattere null, non so perche'
        String[] tokens = articolo_completo.split("[^a-zA-Z]+");

        // Inserisco tutti i tokens in una mappa locale. TODO: in realtà mi interessa solo se esiste o meno, cosa potresti usare?
        for (String tok : tokens) {
            if (!local_map.containsKey(tok)) {
                local_map.put(tok.toLowerCase(), Integer.valueOf(1));
            }
        }

        // Inserisco i tokens di questo articolo nella mappa globale
        try {
            mutex.acquire();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        for (Map.Entry<String, Integer> elem : local_map.entrySet())
        {
            Integer val = global_map.get(elem.getKey());
            if (val == null) {
                global_map.put(elem.getKey(), Integer.valueOf(1));
            } else {
                global_map.replace(elem.getKey(), Integer.valueOf(val + 1));
            }
        }
        mutex.release();
    }
}
