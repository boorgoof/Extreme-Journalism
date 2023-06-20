package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.resources.ResourcesTools;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SerializationProperties {

    private static String serializers_properties = "serializers.properties";

    public static Map<String, Serializer> readSerializersProperties(String filePropertiesName) throws IOException {

        if (filePropertiesName != null)
        {
            serializers_properties = filePropertiesName;
        }
        Properties serializersProperties = ResourcesTools.getProperties(serializers_properties);

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
}
