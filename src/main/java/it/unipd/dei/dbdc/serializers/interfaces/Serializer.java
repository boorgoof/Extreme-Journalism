package it.unipd.dei.dbdc.serializers.interfaces;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
/**
 * This interface defines an object capable of serializing a list of {@link Serializable} into a file
 * The class implementing that interface should provide the serialization logic for a specific file format.
 *
 */
public interface Serializer {
    /**
     * Serializes a list of {@link Serializable} objects to the specified file.
     *
     * @param objects The list of objects to serialize
     * @param file The file in which the list to be serialized is saved
     * @throws IOException If an error occurs during the file writing operation. It can be thrown if the file is not accessible
     */
    void serialize(List<Serializable> objects, File file) throws IOException ;

}


