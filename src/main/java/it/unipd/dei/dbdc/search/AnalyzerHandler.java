package it.unipd.dei.dbdc.search;

import it.unipd.dei.dbdc.Console;
import it.unipd.dei.dbdc.search.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.PropertiesTools;
import it.unipd.dei.dbdc.search.interfaces.Analyzer;

import java.util.*;

import java.io.*;
public class AnalyzerHandler {

    private final Analyzer analyzer;
    private final static String analyzer_key = "analyzer";
    private static final String bannedWordsName = "english_stoplist_v1.txt";

    public AnalyzerHandler(String filePropertiesName) throws IOException {
        Properties analyzersProperties = PropertiesTools.getProperties(filePropertiesName);
        analyzer = setAnalyzer(analyzersProperties);
    }

    private Analyzer setAnalyzer(Properties analyzersProperties) throws IOException{
        String analyzer_class_name = analyzersProperties.getProperty(analyzer_key);
        Class<?> analyzer_class;
        try {
            analyzer_class = Class.forName(analyzer_class_name);
        } catch (ClassNotFoundException e) {
            throw new IOException("There is no component analyzer in the properties file");
        }
        try {
            return (Analyzer) analyzer_class.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            throw new IOException("File properties is not right, or the class is not available");
        }
    }

    public void analyze(List<UnitOfSearch> articles, String file, int tot_count)
    {
        Set<String> banned = bannedArray();
        long start = System.currentTimeMillis();
        ArrayList<OrderedEntryStringInt> max = analyzer.mostPresent(articles, tot_count, banned);
        long end = System.currentTimeMillis();
        System.out.println(Console.YELLOW + "Tempo con set: "+(end-start)+Console.RESET);

        try {
            outFile(max, file);
        }
        catch (IOException e)
        {
            Console.printlnError("Errore nella scritture del file");
            e.printStackTrace();
        }
    }

    private static Set<String> bannedArray() {

        HashSet<String> banned = new HashSet<>(524);

        try (InputStream file = Thread.currentThread().getContextClassLoader().getResourceAsStream(bannedWordsName))
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
            System.out.println("File "+bannedWordsName+" non trovato nel path di sistema. Non verranno usate le stopwords.");
        }
        return banned;
    }

    public void outFile(ArrayList<OrderedEntryStringInt> max, String outFilePath) throws IOException {
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
