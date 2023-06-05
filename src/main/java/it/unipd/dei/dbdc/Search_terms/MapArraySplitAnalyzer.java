package it.unipd.dei.dbdc.Search_terms;

import it.unipd.dei.dbdc.Deserialization.Deserializers.Article;

import java.util.*;

public class MapArraySplitAnalyzer implements Analyzer<Article> {
    // TODO: passalo da sopra
    public ArrayList<MapEntrySI> mostPresent(List<Article> articles, int tot_words, HashMap<String, Integer> banned)
    {
        TreeMap<String, Integer> global_map = new TreeMap<>();
        TreeMap<String, Integer> local_map;
        for (int i = 0; i < articles.size(); i++)
        {
            // TODO: usa i thread
            local_map = new TreeMap<>();

            // Prendo l'articolo e faccio lo split
            Article art = articles.get(i);
            String articolo_completo = art.getTitle() + " " + art.getBody();
            // FIXME: prende ancora il carattere null, non so perche'
            String[] tokens = articolo_completo.split("[^a-zA-Z]+");

            // Inserisco tutti i tokens in una mappa locale. TODO: in realtà mi interessa solo se esiste o meno, cosa potresti usare?
            for (String tok : tokens) {
                if (!local_map.containsKey(tok)) {
                    local_map.put(tok.toLowerCase(), Integer.valueOf(1));
                }
            }

            // Inserisco i tokens di questo articolo nella mappa globale
            for (Map.Entry<String, Integer> elem : local_map.entrySet())
            {
                Integer val = global_map.get(elem.getKey());
                if (val == null) {
                    global_map.put(elem.getKey(), Integer.valueOf(1));
                } else {
                    global_map.replace(elem.getKey(), Integer.valueOf(val + 1));
                }
            }
        }

        ArrayList<MapEntrySI> max = new ArrayList<MapEntrySI>(tot_words);

        for (Map.Entry<String, Integer> el : global_map.entrySet()) {
            addOrdered(max, el, banned, tot_words);
        }
        return max;
    }

    private void addOrdered(ArrayList<MapEntrySI> vec, Map.Entry<String, Integer> entry, HashMap<String, Integer> bannedWords, int tot_words) {
        if (bannedWords.get(entry.getKey()) != null)
        {
            return;
        }
        int vector_size = vec.size();

        // TODO: gestisci meglio queste entry
        MapEntrySI el = new MapEntrySI(entry.getKey(), entry.getValue());

        // Devo aggiungerlo per forza, si tratta solo di capire in che posizione
        if (vector_size < tot_words) {
            int i = 1;

            // Finche' ci sono elementi e sono maggiori
            while (i < vector_size && vec.get(i - 1).isMajorThan(el)) {
                i++;
            }

            // Se sono arrivato alla fine del vettore
            if (i >= vector_size) {
                vec.add(el);
                return;
            }

            // Altrimenti rimpiazzo uno alla volta, con InsertionSort
            MapEntrySI old = vec.get(i - 1);
            vec.set(i - 1, el);
            i++;
            while (i < vector_size) {
                MapEntrySI new_old = vec.get(i);
                vec.set(i - 1, old);
                old = new_old;
                i++;
            }
            vec.add(old);

        }
        else // Altrimenti non e' detto che io la debba aggiungere
        {
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
}
