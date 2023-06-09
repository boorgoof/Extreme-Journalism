package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.deserialization.DeserializersContainer;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;
import it.unipd.dei.dbdc.PropertiesTools;
import it.unipd.dei.dbdc.search.interfaces.UnitOfSearch;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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




