package it.unipd.dei.dbdc.download;

import java.util.AbstractMap;

/**
 * This class provides a simple name to an {@link java.util.AbstractMap.SimpleEntry} with key and value
 * that are both {@link String}
 *
 * @see java.util.AbstractMap.SimpleEntry
 */
public class QueryParam extends AbstractMap.SimpleEntry<String, String> {
    /**
     * The only constructor defined is the one that takes both the key and the value.
     *
     * @param k The key of the entry
     * @param v The value of the entry
     */
    public QueryParam(String k, String v)
    {
        super(k, v);
    }
}
