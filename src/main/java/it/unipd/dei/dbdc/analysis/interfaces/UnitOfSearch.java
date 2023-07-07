package it.unipd.dei.dbdc.analysis.interfaces;

import java.io.Serializable;

/**
 * This class represents a unit to analyze.
 * It has only a function, which returns the text to analyze.
 * It extends {@link Serializable} to be used in the serialization and deserialization part.
 *
 * @see it.unipd.dei.dbdc.analysis.Article
 */
public interface UnitOfSearch extends Serializable {

    /**
     * The function that returns the text to analyze.
     *
     * @return A {@link String} representing the text to analyze.
     */
    String obtainText();
}

