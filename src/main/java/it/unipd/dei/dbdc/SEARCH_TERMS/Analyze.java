package it.unipd.dei.dbdc.SEARCH_TERMS;

import it.unipd.dei.dbdc.DESERIALIZERS_FILE_PROPERTIES.Article;

import java.io.*;
import java.util.*;

public class Analyze {
    public static ArrayList<MapEntry> mostPresent(List<Article> articles)
    {
        TreeMap<String, Integer> mappona = new TreeMap<>();

        for (int i = 0; i < articles.size(); i++) {
            // TODO: usa i thread
            // TODO: usa split della classe String
            TreeMap<String, Integer> map = new TreeMap<>();
            Article art = articles.get(i);
            String articolo_completo = art.getTitle() + " " + art.getBody();
            Scanner sc = new Scanner(articolo_completo);

            sc.useDelimiter("[^’'\\-a-zA-Z0-9]+");
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

        String bannedWords = "./src/main/java/it/unipd/dei/dbdc/SEARCH_TERMS/english_stoplist_v1.txt";
        ArrayList<MapEntry> max = new ArrayList<MapEntry>(50);
        for (Map.Entry<String, Integer> el : mappona.entrySet()) {
            addOrdered(max, el,bannedArray(bannedWords));
        }
        return max;
    }

    private static String[] bannedArray(String filePath){

        String[] banned = new String[524];
        try {
            File file = new File(filePath);
            Scanner scanner = new Scanner(file);
            int i = 0;
            while (scanner.hasNext()) {
                String word = scanner.next();
                banned[i] = word;
                i++;
            }

            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("file non trovato");
        }
        return banned;
    }
    private static void addOrdered(ArrayList<it.unipd.dei.dbdc.SEARCH_TERMS.MapEntry> vec, Map.Entry<String, Integer> entry, String[] bannedWords)
    {
        it.unipd.dei.dbdc.SEARCH_TERMS.MapEntry el = new it.unipd.dei.dbdc.SEARCH_TERMS.MapEntry(entry.getKey(), entry.getValue());
        boolean isBanned = false;
        for(int i = 0; i < bannedWords.length-1; i++){
            if(el.getKey().equals(bannedWords[i])){
                isBanned = true;
                i = bannedWords.length;
            }
        }
        if(!isBanned){
            int mapsize = vec.size();
            if (mapsize < 50)
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
                it.unipd.dei.dbdc.SEARCH_TERMS.MapEntry old = vec.get(i - 1);
                vec.set(i - 1, el);
                i++;
                while (i < mapsize)
                {
                    it.unipd.dei.dbdc.SEARCH_TERMS.MapEntry new_old = vec.get(i);
                    vec.set(i - 1, old);
                    old = new_old;
                    i++;
                }
                vec.add(old);
            }
            else
            {
                int i = 49;
                while (i >= 0 && el.isMajorThan(vec.get(i)))
                {
                    if (i == 49) {
                        i--;
                        continue;
                    }
                    vec.set(i + 1, vec.get(i));
                    i--;
                }
                if (i != 49)
                {
                    vec.set(i + 1, el);
                }
            }
        }
    }

    public static void outFile(ArrayList<it.unipd.dei.dbdc.SEARCH_TERMS.MapEntry> max, String outFilePath){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFilePath))) {
            for (int i = 0; i < 50; i++)
            {
                Map.Entry<String, Integer> el = max.get(i);
                if(el.getKey().equals("the")){

                }
                else {
                    writer.write(el.getKey() + " " + el.getValue());
                    if (i < 49){
                        writer.newLine();
                    }
                }
            }
            System.out.println("Scrittura su file completata");
        } catch (IOException e) {
            System.out.println("Si è verificato un errore durante la scrittura del file.");
        }
    }
}