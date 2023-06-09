package it.unipd.dei.dbdc.search.src_strategies;

import it.unipd.dei.dbdc.search.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.search.OrderedEntryStringInt;
import it.unipd.dei.dbdc.search.interfaces.Analyzer;

import java.util.*;

public class PriorityQueueArraySplitAnalyzer implements Analyzer {
    @Override
    public ArrayList<OrderedEntryStringInt> mostPresent(List<UnitOfSearch> articles, int tot_words, HashMap<String, Integer> banned)
    {
        TreeMap<String, Integer> global_map = new TreeMap<>();

        for (int i = 0; i < articles.size(); i++) {
            // TODO: usa i thread
            // ...

            // Inserisco i tokens di questo articolo nella mappa globale
            // ...

            // Utilizzo una PriorityQueue per mantenere i token più frequenti
            PriorityQueue<Map.Entry<String, Integer>> pq = new PriorityQueue<>(Comparator.comparingInt(Map.Entry::getValue));

            for (Map.Entry<String, Integer> entry : global_map.entrySet()) {
                pq.offer(entry);
                if (pq.size() > tot_words) { // Numero massimo di token più frequenti da mantenere
                    pq.poll();
                }
            }

            ArrayList<OrderedEntryStringInt> max = new ArrayList<>(pq.size());

            // Popoliamo l'array di output in ordine inverso (dal più frequente al meno frequente)
            while (!pq.isEmpty()) {
                Map.Entry<String, Integer> entry = pq.poll();
                addOrdered(max, entry, banned, tot_words);
            }
            return max;
        }

        return null; // In caso di errori o nessun articolo presente
    }

    private void addOrdered(ArrayList<OrderedEntryStringInt> vec, Map.Entry<String, Integer> entry, HashMap<String, Integer> bannedWords, int tot_words) {
        if (bannedWords.get(entry.getKey()) != null)
        {
            return;
        }
        int vector_size = vec.size();

        // TODO: gestisci meglio queste entry
        OrderedEntryStringInt el = new OrderedEntryStringInt(entry.getKey(), entry.getValue());

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
