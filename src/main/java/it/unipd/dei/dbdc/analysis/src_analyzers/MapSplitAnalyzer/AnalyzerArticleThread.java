package it.unipd.dei.dbdc.analysis.src_analyzers.MapSplitAnalyzer;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.Semaphore;

/**
 * This class implements the interface {@link Runnable}.
 * It represents a thread that can be called to analyze a {@link UnitOfSearch}
 * and extract all the words that are contained inside it and put them in a {@link java.util.Map}
 * that is shared between the threads.
 * A term is everything that is made only of letters, and two terms are equals
 * even if they have different upper or lower case letters.
 * This thread is used in {@link MapSplitAnalyzer}
 *
 * @see Runnable
 * @see MapSplitAnalyzer
 */
public class AnalyzerArticleThread implements Runnable {

    /**
     * The {@link java.util.Map} to insert the terms into.
     *
     */
    private final Map<String, Integer> global_map;

    /**
     * The {@link UnitOfSearch} to extract the terms from.
     *
     */
    private final UnitOfSearch article;

    /**
     * The {@link Semaphore} that is needed to provide mutual exclusion.
     *
     */
    private final Semaphore mutex;

    /**
     * The constructor of the class: it accepts a {@link UnitOfSearch} to extract the terms from,
     * a {@link java.util.Map} to put the terms into and a {@link Semaphore} to provide mutual exclusion.
     *
     * @param art A {@link UnitOfSearch} to extract the terms from
     * @param map A {@link java.util.Map} to put the terms into
     * @param sem A {@link Semaphore} to provide mutual exclusion to the map
     */
    public AnalyzerArticleThread(UnitOfSearch art, Map<String, Integer> map, Semaphore sem)
    {
        article = art;
        global_map = map;
        mutex = sem;
    }

    /**
     * The method that overrides the one of the {@link Runnable} interface:
     * it analyzes the {@link UnitOfSearch} and inserts its terms into the {@link java.util.Map}
     *
     * @throws IllegalArgumentException If any of the parameters passed to the constructor is null
     */
    @Override
    public void run() throws IllegalArgumentException
    {
        if (article == null || global_map == null || mutex == null)
        {
            throw new IllegalArgumentException("Parameters passed to the thread are not right");
        }

        //We take all the words of the article and split them.
        //Everything that has not a letter is not a word.
        String complete_article = article.obtainText();
        String[] tokens = complete_article.split("[^a-zA-Z]+");

        HashSet<String> local_map = new HashSet<>();

        //Split does not delete the leading empty character (it's present if the first character is a delimiter,
        //in our case every character that is not alphabetical).
        //This means we have to check every time if the first character is empty.
        int i = 0;
        if (i < tokens.length && tokens[i].isEmpty())
        {
            i++;
        }
        while (i< tokens.length) {
            local_map.add(tokens[i].toLowerCase());
            i++;
        }

        //Critical section
        mutex.acquireUninterruptibly();
        for (String elem : local_map)
        {
            Integer val = global_map.get(elem);
            if (val == null) {
                global_map.put(elem, 1);
            } else {
                global_map.replace(elem, val + 1);
            }
        }
        mutex.release();
    }
}
