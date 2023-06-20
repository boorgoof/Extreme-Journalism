package it.unipd.dei.dbdc.search;

import it.unipd.dei.dbdc.resources.PathManager;
import it.unipd.dei.dbdc.search.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.search.interfaces.Analyzer;

import java.util.*;

import java.io.*;
public class AnalyzerHandler {

    public static void analyze(String filePropertiesName, List<UnitOfSearch> articles, int tot_count) throws IOException {

        Analyzer analyzer = AnalyzeProperties.readProperties(filePropertiesName);
        ArrayList<OrderedEntryStringInt> max = analyzer.mostPresent(articles, tot_count, bannedArray());

        try {
            outFile(max, PathManager.getOutFile());
        }
        catch (IOException e)
        {
            System.err.println("Errore nella scritture del file");
            e.printStackTrace();
        }
    }

    private static Set<String> bannedArray() //TODO: permettere di non usare le stopwords
    {

        HashSet<String> banned = new HashSet<>(524);

        try (InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(PathManager.getBannedWordsFile()))
        {
            if (file == null)
            {
                throw new IOException();
            }
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                banned.add(scanner.next());
            }
        }
        catch (IOException e) {
            System.out.println("Non verranno usate le stopwords.");
        }
        return banned;
    }

    public static void outFile(ArrayList<OrderedEntryStringInt> max, String outFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFilePath))) {
            for (int i = 0; i <max.size(); i++) {
                OrderedEntryStringInt el = max.get(i);
                writer.write(el.getKey() + " " + el.getValue());
                if (i < max.size()-1) {
                    writer.newLine();
                }
            }
        }
    }
}
