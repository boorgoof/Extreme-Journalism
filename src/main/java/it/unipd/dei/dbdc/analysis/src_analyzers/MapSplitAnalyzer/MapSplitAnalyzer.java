package it.unipd.dei.dbdc.analysis.src_analyzers.MapSplitAnalyzer;

import it.unipd.dei.dbdc.tools.ThreadPool;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;
import it.unipd.dei.dbdc.analysis.interfaces.Analyzer;

import java.util.*;
import java.util.concurrent.*;

/**
 * This class implements an {@link Analyzer}.
 * It is a Strategy that analyzes a {@link List} of {@link UnitOfSearch} and returns the most important ones.
 * A term is everything that is made only of letters,  and two terms are equals
 * even if they have different upper or lower case letters.
 *
 * @see UnitOfSearch
 * @see Analyzer
 * @see AnalyzerArticleThread
 */
public class MapSplitAnalyzer implements Analyzer {

    /**
     * Default constructor of the class. It is the only constructor, as there are
     * no fields to give value to, so it doesn't do anything.
     * This is required as the interface {@link Analyzer} can only give non-static
     * methods to be implemented by other classes.
     *
     */
    public MapSplitAnalyzer() {}

    /**
     * The main function, which accepts a {@link List} of {@link UnitOfSearch} and returns the most important
     * terms of this list as an {@link List} of {@link OrderedEntryStringInt}.
     * The most important terms are the one that appear in the most number of articles, and if two terms appear
     * in the same amount of articles, the one which is alphabetically precedent is the most important one.
     * Two terms are the same even if they differ because one has an upper case letter.
     *
     * @param articles A {@link List} of {@link UnitOfSearch} to search into.
     * @param tot_words The number of words we want the returned {@link List} to contain. If there are not enough words, it will contain only the possible ones.
     * @param banned A {@link Set} of words that should not be counted. If null or empty, all the words will be counted.
     * @return A {@link List} of {@link OrderedEntryStringInt} containing the most important terms of the articles
     * @throws IllegalArgumentException If the {@link UnitOfSearch} were not initialized.
     */
    @Override
    public List<OrderedEntryStringInt> mostPresent(List<UnitOfSearch> articles, int tot_words, Set<String> banned) throws IllegalArgumentException
    {
        //If there are no articles, the returned ArrayList is empty
        if (articles == null || articles.isEmpty() || tot_words <= 0)
        {
            return new ArrayList<>();
        }
        TreeMap<String, Integer> global_map = new TreeMap<>();

        //To access the global map, we need to guarantee mutual exclusion. This needs a Semaphore
        Semaphore mutex = new Semaphore(1);

        List<Future<?>> futures = new ArrayList<>(articles.size());
        for (UnitOfSearch article : articles) {
            Future<?> f = ThreadPool.submit(new AnalyzerArticleThread(article, global_map, mutex));
            futures.add(f);
        }

        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                //This happens even if the articles to analyze were not initialized
                ThreadPool.shutdown();
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        ThreadPool.shutdown();

        // To order the couples, we need to have an order. That's why we use the OrderedEntries
        ArrayList<OrderedEntryStringInt> max = new ArrayList<>(tot_words);
        boolean null_banned = (banned == null);
        for (Map.Entry<String, Integer> element : global_map.entrySet()) {
            if (null_banned || !banned.contains(element.getKey()))
            {
                addOrdered(max, element, tot_words);
            }
        }
        return max;
    }

    /**
     * A utility function which has the logic to check if it is needed to add the
     * {@link Map.Entry} to the {@link List} of {@link OrderedEntryStringInt}, and in what place.
     *
     * @param vec A {@link List} of {@link OrderedEntryStringInt} which contains the most important terms added till now.
     * @param entry A {@link Map.Entry} that can be added or not to the {@link List}.
     * @param tot_words The number of words we want the returned {@link List} to contain.
     */
    private static void addOrdered(List<OrderedEntryStringInt> vec, Map.Entry<String, Integer> entry, int tot_words) {

        OrderedEntryStringInt el = new OrderedEntryStringInt(entry);
        int vector_size = vec.size();

        //There are two possibilities:
        if (vector_size < tot_words) {
            //A. An element should be added because the vector is not long as it should
            int i = 0;

            //Check in what position the element should be added
            while (i < vector_size && vec.get(i).isMajorThan(el)) {
                i++;
            }

            //If it is major to at least one of the other elements
            if (i != vector_size) {
                OrderedEntryStringInt old = vec.get(i);
                vec.set(i, el);
                i++;
                while (i < vector_size) {
                    OrderedEntryStringInt new_old = vec.get(i);
                    vec.set(i, old);
                    old = new_old;
                    i++;
                }
                //The new el that should be added is the one that is called old
                el = old;
            }

            vec.add(el);
        }
        else {
            //B. No element should be added, but only replaced. In this case, it can be more efficient to check from the end of the List
            int i = tot_words - 1;
            while (i >= 0 && el.isMajorThan(vec.get(i))) {
                if (i + 1 < tot_words) {
                    vec.set(i + 1, vec.get(i));
                }
                i--;
            }
            if (i != tot_words - 1) {
                vec.set(i + 1, el);
            }
        }
    }
}

