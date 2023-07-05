package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;
import it.unipd.dei.dbdc.tools.PropertiesTools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Utility class that reads a properties file specifying all the {@link Deserializer} that the program can use
 * @see Properties
 * @see PropertiesTools
 */
public class DeserializationProperties {

    /**
     * The name of the default properties file.
     */
    public final static String default_properties = "deserializers.properties";

    /**
     * The function that reads the properties file and returns a {@link Map} of {@link Deserializer} and their names
     *  specified in the properties file. The names conventionally represent the file format to deserialize
     *
     * @param out_properties The name of the properties file specified by the user. If null, the default properties file will be used.
     * @return A {@link Map} of {@link Deserializer} and their names specified in the properties file.
     * @throws IOException If both the default properties file and the user specified are not present, or if the properties specified are not correct.
     */
    public static Map<String, Deserializer> readDeserializersProperties(String out_properties) throws IOException {

        // load the properties file selected by the user, or by default
        Properties deserializersProperties = PropertiesTools.getProperties(default_properties, out_properties);

        Map<String, Deserializer> deserializerMap = new HashMap<>();

        // for each format name specified in the properties file
        for (String format : deserializersProperties.stringPropertyNames()) {

            String deserializerClassName = deserializersProperties.getProperty(format);
            if (deserializerClassName == null) {
                throw new IOException("No deserializer found for the format: " + format);
            }

            try {
                Class<?> deserializerClass = Class.forName(deserializerClassName);
                // Instantiate the Deserializer
                Deserializer deserializer = (Deserializer) deserializerClass.getDeclaredConstructor().newInstance();
                // the Deserializer is added to the map according to its format
                deserializerMap.put(format, deserializer);
            } catch (Exception e) {
                throw new IOException("Failed to instantiate the deserializer for the format: " + format, e);
            }
        }

        return deserializerMap;
    }
}
