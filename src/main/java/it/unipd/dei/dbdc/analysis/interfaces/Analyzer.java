package it.unipd.dei.dbdc.analysis.interfaces;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;

import java.util.List;
import java.util.Set;

/**
 * This interface is the one that should be implemented by any class that analyzes a {@link List} of
 * {@link UnitOfSearch} and returns the most important ones.
 * It provides only a function, and is useful as it follows the Strategy design pattern.
 * A term is everything that is made only of letters, and is presented in lower case.
 *
 * @see UnitOfSearch
 * @see it.unipd.dei.dbdc.analysis.src_strategies.MapSplitAnalyzer.MapSplitAnalyzer
 */
public interface Analyzer {

    /**
     * The main function, which accepts a {@link List} of {@link UnitOfSearch} and returns the most important
     * terms of this list as a {@link List} of {@link OrderedEntryStringInt}.
     * The most important terms are the one that appear in the most number of articles, and if two terms appear
     * in the same amount of articles, the one which is alphabetically precedent is the most important one.
     * Two terms are the same even if they differ because one has an upper case letter.
     *
     * @param articles A {@link List} of {@link UnitOfSearch} to search into.
     * @param tot_words The number of words we want the returned {@link List} to contain. If there are not enough words, it will contain only the possible ones.
     * @param banned_words A {@link Set} of words that should not be counted. If null or empty, all the words will be counted.
     * @return A {@link List} of {@link OrderedEntryStringInt} containing the most important terms of the articles
     * @throws IllegalArgumentException If something went wrong (it is the only exception that should be thrown).
     */
    List<OrderedEntryStringInt> mostPresent(List<UnitOfSearch> articles, int tot_words, Set<String> banned_words) throws IllegalArgumentException;
}
