package it.unipd.dei.dbdc.serializers;

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
     * @throws IOException Eccezione lanciata dato che sono stronzo
     *
     */
    private Map<String, Serializer> serializers;

    public SerializationHandler(String filePropertiesName) throws IOException {

        Properties serializersProperties = PropertiesTools.getProperties(filePropertiesName);
        serializers = setSerializersMap(serializersProperties);

    }

    private Properties loadProperties(String filePropertiesName) throws IOException {

        try (InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePropertiesName)) {
            Properties properties = new Properties();
            properties.load(propertiesFile);
            return properties;
        }

    }

    // istanza i serializers nella mappa cosi che possano essere creati solo una volta ed usati ( se servono ) quante volte si vuole
    private Map<String, Serializer> setSerializersMap(Properties serializersProperties) throws IOException {

        Map<String, Serializer> serializerMap = new HashMap<>();

        for (String format : serializersProperties.stringPropertyNames()) {

            String serializerClassName = serializersProperties.getProperty(format);
            if (serializerClassName == null) {
                throw new IOException("No serializer found for the format: " + format);
            }

            try {
                Class<?> serializerClass = Class.forName(serializerClassName);
                Serializer serializer = (Serializer) serializerClass.getDeclaredConstructor().newInstance();
                serializerMap.put(format, serializer);
            } catch (Exception e) {
                throw new IOException("Failed to instantiate the serializer for the format: " + format, e);
            }

        }
        return serializerMap;
    }

    public void serializeObjects(List<UnitOfSearch> objects, String format, String filePath) throws IOException {

        Serializer serializer = serializers.get(format);
        if (serializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }
        serializer.serialize(objects, filePath);
    }

}



// In questo tipologia instanziava un serializers ogni volta che doveva deserializzare quel formato. Ho evitato questa cosa con la mappa
/*
private final Properties serializers;

    public SerializationHandler() throws IOException {
        serializers = new Properties();
    }


    public SerializationHandler(String filePropertiesName) throws IOException {
        serializers = new Properties();
        setSerializers(filePropertiesName);
    }

    private void setSerializers( String filePropertiesName) throws IOException {
        try (InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePropertiesName)) {
            serializers.load(propertiesFile);
        }
    }

    public void serializeObjects(List<UnitOfSearch> objects, String format, String filePath) throws IOException {
        String serializerClassName = serializers.getProperty(format);
        if (serializerClassName == null) {
            throw new IOException("No serializer found for the specified format: " + format);
        }

        try {
            Class<?> serializerClass = Class.forName(serializerClassName);
            Serializer serializer = (Serializer) serializerClass.getDeclaredConstructor().newInstance();
            serializer.serialize(objects, filePath);
        } catch (Exception e) {
            throw new IOException("Failed to serialize objects using the specified format: " + format, e);
        }
    }
*/

