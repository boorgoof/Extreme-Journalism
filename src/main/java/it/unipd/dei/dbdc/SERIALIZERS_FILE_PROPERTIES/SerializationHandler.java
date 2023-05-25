package it.unipd.dei.dbdc.SERIALIZERS_FILE_PROPERTIES;

import java.io.*;
import java.util.List;
import java.util.Properties;


public class SerializationHandler {
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

    public void serializeObjects(List<Serializable> objects, String format, String filePath) throws IOException {
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

}


