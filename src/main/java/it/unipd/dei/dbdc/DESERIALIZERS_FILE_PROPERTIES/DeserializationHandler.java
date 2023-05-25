package it.unipd.dei.dbdc.DESERIALIZERS_FILE_PROPERTIES;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.List;
import java.util.Properties;
import java.util.Set;

public class DeserializationHandler<T> {
    private final Properties deserializers;

    public DeserializationHandler() throws IOException {
        deserializers = new Properties();
    }

    public DeserializationHandler(String filePropertiesName) throws IOException {
        deserializers = new Properties();
        setDeserializers(filePropertiesName);
    }

    private void setDeserializers( String filePropertiesName) throws IOException {
        try (InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePropertiesName)) {
            deserializers.load(propertiesFile);
        }
    }
    public Set<String> getFormats(){
        return deserializers.stringPropertyNames();
    }

    public List<T> deserializeFile(String format, String filePath) throws IOException {

        String serializerClassName = deserializers.getProperty(format);
        if (serializerClassName == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }

        try {

            Class<?> deserializerClass = Class.forName(serializerClassName);
            Deserializer<T> deserializer = (Deserializer<T>) deserializerClass.getDeclaredConstructor().newInstance(); // come dovrei aggiustarla? in realta T non è necessaria
            return deserializer.deserialize(filePath);

        } catch (Exception e) {
            throw new IOException("Failed to deserialize objects using the specified format: " + format, e);
        }
    }

    public void deserializeFolder(String format, String folderPath, List<T> objects) throws IOException {
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(format)) {
                    objects.addAll(deserializeFile(format, file.getAbsolutePath()));
                } else if (file.isDirectory()) {
                    deserializeFolder(format, file.getAbsolutePath(), objects);
                }
            }
        }
    }




}
