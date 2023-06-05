package it.unipd.dei.dbdc.Handlers;

import it.unipd.dei.dbdc.ConsoleTextColors;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APICaller;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APIManager;
import it.unipd.dei.dbdc.PropertiesTools;
import it.unipd.dei.dbdc.Search_terms.Analyzer;
import it.unipd.dei.dbdc.Search_terms.MapEntrySI;

import java.util.*;

import java.io.*;
public class AnalyzerHandler<T> {

    private final Analyzer<T> analyzer;
    private final static String analyzer_key = "analyzer";
    private static final String bannedWordsPath = "./src/main/resources/english_stoplist_v1.txt";

    public AnalyzerHandler(String filePropertiesName) throws IOException {
        Properties analyzersProperties = PropertiesTools.getProperties(filePropertiesName);
        analyzer = setAnalyzer(analyzersProperties);
    }

    private Analyzer<T> setAnalyzer(Properties analyzersProperties) throws IOException{
        String analyzer_class_name = analyzersProperties.getProperty(analyzer_key);
        Class<?> analyzer_class;
        try {
            analyzer_class = Class.forName(analyzer_class_name);
        } catch (ClassNotFoundException e) {
            throw new IOException("There is no component analyzer in the properties file");
        }
        try {
            return (Analyzer<T>) analyzer_class.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IOException("File properties is not right, or the class is not available");
        }
    }

    public void analyze(List<T> articles, String file, int tot_count)
    {
        ArrayList<MapEntrySI> max;

        long start = System.currentTimeMillis();
        max = analyzer.mostPresent(articles, tot_count, bannedArray());
        long end = System.currentTimeMillis();
        System.out.println(ConsoleTextColors.YELLOW + "Estrazione termini: "+(end-start)+ConsoleTextColors.RESET);

        try {
            outFile(max, file);
        }
        catch (IOException e)
        {
            ConsoleTextColors.printlnError("Errore nella scritture del file");
            e.printStackTrace();
        }
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

    public void outFile(ArrayList<MapEntrySI> max, String outFilePath) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outFilePath))) {
            for (int i = 0; i <max.size(); i++) {
                MapEntrySI el = max.get(i);
                writer.write(el.getKey() + " " + el.getValue());
                if (i < max.size()-1) {
                    writer.newLine();
                }
            }
        }
    }
}
