package it.unipd.dei.dbdc.analysis;

import it.unipd.dei.dbdc.analysis.interfaces.OutPrinter;
import it.unipd.dei.dbdc.tools.PathManager;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.analysis.interfaces.Analyzer;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.*;
/**
 * The handler of the part of the analysis of the articles downloaded.
 *
 */
public class AnalyzerHandler {

    /**
     * The number of stop words that are present inside the file of the stop words.
     *
     */
    private final static int number_stop_words = 524;

    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created, as this is only a utility class.
     *
     */
    private AnalyzerHandler() {}

    /**
     * The main function that analyzes the articles and prints the most important words in a file.
     * It can use banned words specified by the user, the default ones, or no banned words at all.
     *
     * @param filePropertiesName The name of the file of the analysis properties. If it is null, default properties will be used.
     * @param articles The {@link List} of {@link UnitOfSearch} to analyze.
     * @param tot_words The number of words to print out.
     * @param stop_words A boolean that is true if the words in the banned words file should be banned. If true but the default banned words file is not present, they will not be used.
     * @param banned_words_filepath A {@link String} that is the path of the file passed by the user as a file of banned words. If null, the default ones will be used.
     * @return A {@link String} representing the path of the file that was printed.
     * @throws IllegalArgumentException If there are no terms to print or the {@link UnitOfSearch} were not initialized correctly.
     * @throws IOException If the analysis properties file is invalid or the default one is invalid or missing, or if it can't print the output file.
     */
    public static String analyze(String filePropertiesName, List<UnitOfSearch> articles, int tot_words, boolean stop_words, String banned_words_filepath) throws IOException, IllegalArgumentException {

        Object[] props = AnalyzeProperties.readProperties(filePropertiesName);

        Analyzer analyzer = (Analyzer) props[0];
        OutPrinter printer = (OutPrinter) props[1];

        Set<String> banned = null;
        if (stop_words)
        {
            banned = bannedSet(banned_words_filepath);
        }
        List<OrderedEntryStringInt> max = analyzer.mostPresent(articles, tot_words, banned);

        return printer.outFile(max);
    }

    /**
     * The function that gets the terms that are banned, if needed. It uses the file that is specified by the
     * {@link PathManager} class.
     *
     * @return A {@link Set} representing the terms that are banned. It's null if the file is missing or there is an error in it.
     */
    private static Set<String> bannedSet(String banned_out)
    {
        HashSet<String> banned = null;
        try
        {
            InputStream file = null;
            boolean default_ = true;
            if (banned_out != null) {
                try {
                    file = Files.newInputStream(Paths.get(banned_out));
                    default_ = false;
                } catch (IOException e) {
                    //The default one will be used
                }
            }
            if (default_)
            {
                file = Thread.currentThread().getContextClassLoader().getResourceAsStream(PathManager.getBannedWordsFile());
            }
            if (file == null)
            {
                throw new IOException();
            }
            banned = new HashSet<>(number_stop_words);
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                banned.add(scanner.next());
            }
            file.close();
            scanner.close();
        }
        catch (IOException e) {
            System.out.println("The stop-words won't be used because there was an error in the reading of the file.");
        }
        return banned;
    }
}
