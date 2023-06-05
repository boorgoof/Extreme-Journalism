package it.unipd.dei.dbdc.Search_terms;

import it.unipd.dei.dbdc.Deserializers.Article;

import java.io.*;
import java.util.*;

public class MapArrayScannerAnalyzer implements Analyzer<Article> {

    public ArrayList<MapEntrySI> mostPresent(List<Article> articles, int tot_words, HashMap<String, Integer> banned)
    {
        TreeMap<String, Integer> mappona = new TreeMap<>();

        for (int i = 0; i < articles.size(); i++) {
            // TODO: usa i thread
            // TODO: usa split della classe String
            TreeMap<String, Integer> map = new TreeMap<>();
            Article art = articles.get(i);
            String articolo_completo = art.getTitle() + " " + art.getBody();
            Scanner sc = new Scanner(articolo_completo);

            sc.useDelimiter("[^’'\\-a-zA-Z]+");
            while (sc.hasNext()) {
                String s = sc.next();
                if(!map.containsKey(s)){
                    map.put(s.toLowerCase(), 1);
                }
            }
            for (Map.Entry<String, Integer> el : map.entrySet()) {
                Integer val = mappona.get(el.getKey());
                if (val == null) {
                    mappona.put(el.getKey(), 1);
                    //System.out.println("il termine " + el.getKey() + " è stato inserito nella mappona e il suo valore è: " + mappona.get(el.getKey()) + "\n" + "\n");
                }
                else {
                    mappona.replace(el.getKey(),val+1);
                    //System.out.println("il valore del termine " + el.getKey() + " è stato incrementato a " + mappona.get(el.getKey()) + "\n" + "\n");
                }
            }
            sc.close();
        }

        ArrayList<MapEntrySI> max = new ArrayList<MapEntrySI>(tot_words);
        for (Map.Entry<String, Integer> el : mappona.entrySet()) {
            addOrdered(max, el, banned, tot_words);
        }
        return max;
    }

    private void addOrdered(ArrayList<MapEntrySI> vec, Map.Entry<String, Integer> entry, HashMap<String, Integer> bannedWords, int tot_words)
    {
        MapEntrySI el = new MapEntrySI(entry.getKey(), entry.getValue());
        for(int i = 0; i < bannedWords.size()-1; i++){
            if(bannedWords.containsKey(el.getKey())){
                return;
            }
        }
            int mapsize = vec.size();
            if (mapsize < tot_words)
            {
                // Devo aggiungerlo
                int i = 1;
                while(i < mapsize && vec.get(i-1).isMajorThan(el))
                {
                    i++;
                }
                if (i >= mapsize)
                {
                    vec.add(el);
                    return;
                }
                MapEntrySI old = vec.get(i - 1);
                vec.set(i - 1, el);
                i++;
                while (i < mapsize)
                {
                    MapEntrySI new_old = vec.get(i);
                    vec.set(i - 1, old);
                    old = new_old;
                    i++;
                }
                vec.add(old);
            }
            else
            {
                int i = tot_words-1;
                while (i >= 0 && el.isMajorThan(vec.get(i)))
                {
                    if (i == tot_words-1) {
                        i--;
                        continue;
                    }
                    vec.set(i + 1, vec.get(i));
                    i--;
                }
                if (i != tot_words-1)
                {
                    vec.set(i + 1, el);
                }
            }
    }
}