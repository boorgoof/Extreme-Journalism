package it.unipd.dei.dbdc.analysis;

import it.unipd.dei.dbdc.tools.PathTools;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.analysis.interfaces.Analyzer;

import java.util.*;
import java.io.*;
public class AnalyzerHandler {
    private final static int default_stop_words = 524;

    //Throws IOException se non c'è il default properties o se è scorretto o se non riesce ad aprire o scrivere il file di output
    public static String analyze(String filePropertiesName, List<UnitOfSearch> articles, int tot_count, boolean stop_words) throws IOException, IllegalArgumentException {

        Analyzer analyzer = AnalyzeProperties.readProperties(filePropertiesName);
        Set<String> banned = null;
        if (stop_words)
        {
            banned = bannedArray();
        }
        ArrayList<OrderedEntryStringInt> max = analyzer.mostPresent(articles, tot_count, banned);

        String outFile = PathTools.getOutFile();
        outFile(max, outFile);
        return outFile;
    }

    private static Set<String> bannedArray()
    {
        HashSet<String> banned = null;
        try (InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(PathTools.getBannedWordsFile()))
        {
            if (file == null)
            {
                throw new IOException();
            }
            banned = new HashSet<>(default_stop_words);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                banned.add(scanner.next());
            }
        }
        catch (IOException e) {
            System.out.println("The stop-words won't be used because there was an error in the reading of the file.");
        }
        return banned;
    }

    private static void outFile(ArrayList<OrderedEntryStringInt> max, String outFilePath) throws IOException {
        if (max == null)
        {
            throw new IllegalArgumentException("Vector of most important words is null");
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFilePath))) {
            for (int i = 0; i <max.size(); i++) {
                OrderedEntryStringInt el = max.get(i);
                writer.write(el.getKey() + " " + el.getValue());
                if (i != max.size()-1) {
                    writer.newLine();
                }
            }
        }
    }
}
