package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.download.APIContainer;
import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.io.*;
import java.util.List;
import java.util.Scanner;

import it.unipd.dei.dbdc.tools.PathManager;
/**
 * Class that manages the serialization of {@link Serializable} into files
 *
 * @see SerializersContainer
 */
public class SerializationHandler {

    /**
     * The {@link SerializersContainer} instance that supplies all the {@link Serializer} we have
     *
     */
    private final SerializersContainer container;

    /**
     * Constructor that initializes the SerializationHandler.
     *
     * @param serializers_properties The file properties specified by the user. If null, the default ones will be used.
     * @throws IOException If the download properties files (the default one and the one specified by the user) are not present or are not correct.
     */
    public SerializationHandler(String serializers_properties) throws IOException {
        container = SerializersContainer.getInstance(serializers_properties);
    }
    /**
     * Serializes a list of {@link Serializable} objects into XML file with indented formatting.
     * The function correctly selects the {@link Serializer} to use starting from the extension of the file passed as a parameter
     *
     * @param objects  The list of {@link Serializable} objects to be serialized.
     * @param file  The file into which the objects will be serialized
     * @throws IOException  If the file passed as a parameter has no associated {@link Serializer}
     * @throws IllegalArgumentException  If either the objects or file parameter is null.
     */
    public void serializeObjects(List<Serializable> objects, File file) throws IOException {

        if (file == null) {
            throw new IllegalArgumentException("The XML file cannot be null");
        }
        if (objects == null) {
            throw new IllegalArgumentException("The object list cannot be null");
        }

        // the file format is stored
        String format = PathManager.getFileFormat(file.getName());
        // a specific serializer instance for the file format. If there is no serilizer for the file passed, an exception is thrown
        Serializer serializer = container.getSerializer(format);
        if (serializer == null) {
            throw new IOException("Objects cannot be serialized in the specified file. The file provided is " + file.getName());
        }

        // if a serializer is actually available for the file then serialization is performed
        serializer.serialize(objects, file);
    }



}




