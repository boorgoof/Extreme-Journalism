package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.serializers.interfaces.Serializer;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.io.*;
import java.util.List;

/**
 * Classe per la gestione della serializzazione.
 *
 */
public class SerializationHandler {
    /**
     * Costruttore della classe SerializationHandler.
     *
     * @param filePropertiesName Nome del file delle proprietà per i serializer.
     * @throws IOException
     *
     */
    private final SerializersContainer container;
    public SerializationHandler(String fileProperties) throws IOException {
        container = new SerializersContainer(fileProperties);
    }

    public void serializeObjects(List<UnitOfSearch> objects, String format, String filePath) throws IOException {

        Serializer serializer = container.getSerializer(format);
        if (serializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }
        serializer.serialize(objects, filePath);
    }

}




