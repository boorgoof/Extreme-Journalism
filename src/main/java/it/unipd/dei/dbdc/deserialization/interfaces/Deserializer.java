package it.unipd.dei.dbdc.deserialization.interfaces;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
/**
 * This interface defines an object capable of deserializing a file into a list of {@link Serializable}
 * The class implementing that interface should provide the deserialization logic for a specific file format.
 *
 */
public interface Deserializer {
    /**
     * Deserialize a file into a list of objects {@link Serializable}
     *
     * @param file The file to deserialize into {@link Serializable}
     * @return the list of {@link Serializable} objects obtained from deserialization
     * @throws IOException If an I/O error occurs during the deserialization process.
     */
    List<Serializable> deserialize(File file) throws IOException;
}

