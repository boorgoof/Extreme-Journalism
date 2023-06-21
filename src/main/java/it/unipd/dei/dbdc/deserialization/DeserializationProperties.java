package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import it.unipd.dei.dbdc.resources.PropertiesTools;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class DeserializationProperties {

    public final static String default_properties = "deserializers.properties";

    public static Map<String, Deserializer> readDeserializersProperties(String out_properties) throws IOException {

        Properties deserializersProperties = PropertiesTools.getProperties(default_properties, out_properties);

        Map<String, Deserializer> deserializerMap = new HashMap<>();

        for (String format : deserializersProperties.stringPropertyNames()) {

            String deserializerClassName = deserializersProperties.getProperty(format);
            if (deserializerClassName == null) {
                throw new IOException("No deserializer found for the format: " + format);
            }

            try {
                Class<?> deserializerClass = Class.forName(deserializerClassName);
                Deserializer deserializer = (Deserializer) deserializerClass.getDeclaredConstructor().newInstance();
                deserializerMap.put(format, deserializer);
            } catch (Exception e) {
                throw new IOException("Failed to instantiate the deserializer for the format: " + format, e);
            }

        }
        return deserializerMap;
    }
}
