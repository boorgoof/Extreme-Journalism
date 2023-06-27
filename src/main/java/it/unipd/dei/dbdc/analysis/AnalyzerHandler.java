package it.unipd.dei.dbdc.analysis;

import it.unipd.dei.dbdc.tools.PathTools;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.analysis.interfaces.Analyzer;

import java.util.*;
import java.io.*;
/**
 * The handler of the part of the analysis of the articles downloaded.
 *
 */
public class AnalyzerHandler {
    private final static int default_stop_words = 524;

    /**
     * The main function that analyzes the articles and prints the most important words in a file.
     *
     * @param filePropertiesName The name of the file of the analysis properties. If it is null, default properties will be used.
     * @param articles The {@link List} of {@link UnitOfSearch} to analyze.
     * @param tot_words The number of words to print out.
     * @param stop_words A boolean that is true if the words in the banned words file should be banned.
     * @return A {@link String} representing the path of the file that was printed.
     * @throws IllegalArgumentException If there are no terms to print.
     * @throws IOException If the analysis properties file is invalid or the default one is invalid or missing, or if it can't print the output file.
     */
    public static String analyze(String filePropertiesName, List<UnitOfSearch> articles, int tot_words, boolean stop_words) throws IOException, IllegalArgumentException {

        Analyzer analyzer = AnalyzeProperties.readProperties(filePropertiesName);
        Set<String> banned = null;
        if (stop_words)
        {
            banned = bannedArray();
        }
        ArrayList<OrderedEntryStringInt> max = analyzer.mostPresent(articles, tot_words, banned);

        String outFile = PathTools.getOutFile();
        outFile(max, outFile);
        return outFile;
    }

    /**
     * The function that gets the terms that are banned, if needed. It uses the file that is specified by the
     * {@link PathTools} class.
     *
     * @return A {@link Set} representing the terms that are banned. It's null if the file is missing or there is an error in it.
     */
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

    /**
     * The function that prints the most important terms into a file.
     *
     * @param max The {@link ArrayList} of terms to print, that are already in order.
     * @param outFilePath The path of the file to print.
     * @throws IOException If it can't print the file
     * @throws IllegalArgumentException If the {@link ArrayList} of terms to print is empty or null.
     */
    private static void outFile(ArrayList<OrderedEntryStringInt> max, String outFilePath) throws IOException, IllegalArgumentException {
        if (max == null || max.isEmpty())
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
