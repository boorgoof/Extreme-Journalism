package it.unipd.dei.dbdc.deserialization.interfaces;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.io.File;
import java.io.IOException;
import java.util.List;
/**
 * This interface defines an object capable of deserializing a file into a list of {@link UnitOfSearch}
 * The class implementing that interface should provide the deserialization logic for a specific file format.
 *
 */
public interface Deserializer {
    /**
     * Deserialize a file into a list of objects {@link UnitOfSearch}
     *
     * @param file The file in which the list to be serialized is saved
     * @throws IOException If an error occurs during the file writing operation. It can be thrown if the file is not accessible
     * @return the list of {@link UnitOfSearch} objects obtained from deserialization
     */
    List<UnitOfSearch> deserialize(File file) throws IOException;
}

