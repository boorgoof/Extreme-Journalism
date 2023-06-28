package it.unipd.dei.dbdc.analysis.interfaces;

/**
 * This class represents a unit to analyze.
 * It has only a function, which returns the text to analyze.
 *
 * @see it.unipd.dei.dbdc.analysis.Article
 */
public interface UnitOfSearch {
    /**
     * The function that returns the text to analyze.
     *
     * @return A {@link String} representing the text to analyze.
     */
    String obtainText();
}
