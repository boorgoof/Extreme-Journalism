package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.tools.PropertiesTools;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
/**
 * Utility class that reads a properties file specifying all the {@link Serializer} that the program can use
 * @see Properties
 * @see PropertiesTools
 */
public class SerializationProperties {

    /**
     * The name of the default properties file.
     */
    private final static String default_properties = "serializers.properties"; // TODO è il nome o il path, meglio specificare?

    /**
     * The function that reads the properties file and returns a {@link Map} of {@link Serializer} and their names
     *  specified in the properties file. The names conventionally represent the file format into which the objects will be serialized.
     *
     * @param out_properties The name of the properties file specified by the user. If null, the default properties file will be used.
     * @return A {@link Map} of {@link Serializer} and their names specified in the properties file.
     * @throws IOException If both the default properties file and the user specified are not present, or if the properties specified are not correct.
     * @see PropertiesTools
     */
    public static Map<String, Serializer> readSerializersProperties(String out_properties) throws IOException {

        // load the properties file selected by the user, or by default
        Properties serializersProperties = PropertiesTools.getProperties(default_properties, out_properties);

        Map<String, Serializer> serializerMap = new HashMap<>();

        // for each format name specified in the properties file
        for (String format : serializersProperties.stringPropertyNames()) {

            String serializerClassName = serializersProperties.getProperty(format);
            if (serializerClassName == null) {
                throw new IOException("No serializer found for the format: " + format);
            }

            try {
                Class<?> serializerClass = Class.forName(serializerClassName);
                // Instantiate the Serializer
                Serializer serializer = (Serializer) serializerClass.getDeclaredConstructor().newInstance();
                // the serializer is added to the map according to its format
                serializerMap.put(format, serializer);
            } catch (Exception e) {
                throw new IOException("Failed to instantiate the serializer for the format: " + format, e);
            }

        }
        return serializerMap;
    }
}
