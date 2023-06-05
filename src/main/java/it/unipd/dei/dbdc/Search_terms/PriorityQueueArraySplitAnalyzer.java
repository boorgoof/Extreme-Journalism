package it.unipd.dei.dbdc.Search_terms;

import it.unipd.dei.dbdc.Deserializers.Article;

import java.io.*;
import java.util.*;

public class PriorityQueueArraySplitAnalyzer {
    private static final String bannedWordsPath = "./src/main/resources/english_stoplist_v1.txt";

    private final int tot_words ;

    public PriorityQueueArraySplitAnalyzer(int count){
        tot_words = count;
    }
    public ArrayList<MapEntrySI> mostPresent(List<Article> articles)
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

            ArrayList<MapEntrySI> max = new ArrayList<>(pq.size());
            HashMap<String, Integer> banned = bannedArray();

            // Popoliamo l'array di output in ordine inverso (dal più frequente al meno frequente)
            while (!pq.isEmpty()) {
                Map.Entry<String, Integer> entry = pq.poll();
                addOrdered(max, entry, banned);
            }
            return max;
        }

        return null; // In caso di errori o nessun articolo presente
    }

    private static HashMap<String, Integer> bannedArray() {
        // TODO: ci interessa solo che sia presente, cosa uso?

        HashMap<String, Integer> banned = new HashMap<>(524);
        File file = new File(bannedWordsPath);
        try (Scanner scanner = new Scanner(file);)
        {
            while (scanner.hasNext()) {
                banned.put(scanner.next(), Integer.valueOf(1));
            }
        } catch (FileNotFoundException e) {
            System.out.println("File non trovato nel path: "+bannedWordsPath);
        }
        return banned;
    }

    private void addOrdered(ArrayList<MapEntrySI> vec, Map.Entry<String, Integer> entry, HashMap<String, Integer> bannedWords) {
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

    public void outFile(ArrayList<MapEntrySI> max, String outFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFilePath))) {
            for (int i = 0; i < tot_words; i++) {
                MapEntrySI el = max.get(i);
                writer.write(el.getKey() + " " + el.getValue());
                if (i < tot_words-1) {
                    writer.newLine();
                }
            }
        }
    }
}
