package it.unipd.dei.dbdc.analysis.interfaces;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * This interface is the one that should be implemented by any class that analyzes a {@link List} of
 * {@link UnitOfSearch} and returns the most important ones.
 * It provides only a function, and is useful as it follows the Strategy design patter.
 * A term is everything that is made only of letters.
 *
 * @see UnitOfSearch
 * @see it.unipd.dei.dbdc.analysis.src_strategies.MapArraySplitAnalyzer.MapSplitAnalyzer
 */
public interface Analyzer {
    /**
     * The main function, which accepts a {@link List} of {@link UnitOfSearch} and returns the most important
     * terms of this list as an {@link ArrayList} of {@link OrderedEntryStringInt}.
     * The most important terms are the one that appear in the most number of articles, and if two terms appear
     * in the same amount of articles, the one which is alphabetically precedent is the most important one.
     *
     * @param articles A {@link List} of {@link UnitOfSearch} to search into.
     * @param tot_words The number of words we want the returned {@link ArrayList} to contain. If there are not enough words, it will contain only the possible ones.
     * @param banned_words A {@link Set} of words that should not be counted. If null or empty, all the words will be counted.
     * @return An {@link ArrayList} of {@link OrderedEntryStringInt} containing the most important terms of the articles
     */
    ArrayList<OrderedEntryStringInt> mostPresent(List<UnitOfSearch> articles, int tot_words, Set<String> banned_words);
}
