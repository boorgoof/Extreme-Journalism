package it.unipd.dei.dbdc.deserialization.interfaces;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

/**
 * This interface extends the {@link Deserializer} interface and defines additional methods related to fields.
 * This interface defines a more specific deserializer that is capable to deal with files with a complex and variable structure.
 * In particular, It provides the ability to selectively deserialize specific fields with the option to modify them.
 *
 */
public interface DeserializerWithFields extends Deserializer{

    /**
     * Provides the fields taken into account during deserialization
     *
     * @return An array of {@link String} representing the fields.
     */
    String[] getFields();

    /**
     * Provides the number of fields taken into account during deserialization
     *
     * @return An {@link Integer} representing number of fileds
     */
    int numberOfFields();


    /**
     * Sets the new fields to be considered during deserialization
     *
     * @param fields The new fields to be considered during deserialization
     */
    void setFields(String[] fields);
}
