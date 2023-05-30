package it.unipd.dei.dbdc.Deserializers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.*;

public class DeserializationHandler<T> {

    private Map<String, Deserializer<T>> deserializers;

    public DeserializationHandler(String filePropertiesName) throws IOException {

        Properties deserializersProperties = loadProperties(filePropertiesName); //cambiare nome
        deserializers = setDeserializersMap(deserializersProperties);

    }

    private Properties loadProperties(String filePropertiesName) throws IOException {

        try (InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePropertiesName)) {
            Properties properties = new Properties();
            properties.load(propertiesFile);
            return properties;
        }

    }

    private Map<String, Deserializer<T>> setDeserializersMap(Properties deserializersProperties) throws IOException {

        Map<String, Deserializer<T>> deserializerMap = new HashMap<>();

        for (String format : deserializersProperties.stringPropertyNames()) {

            String deserializerClassName = deserializersProperties.getProperty(format);
            if (deserializerClassName == null) {
                throw new IOException("No deserializer found for the format: " + format);
            }

            try {
                Class<?> deserializerClass = Class.forName(deserializerClassName);
                Deserializer<T> deserializer = (Deserializer<T>) deserializerClass.getDeclaredConstructor().newInstance();
                deserializerMap.put(format, deserializer);
            } catch (Exception e) {
                throw new IOException("Failed to instantiate the deserializer for the format: " + format, e);
            }

        }
        return deserializerMap;
    }

    public Set<String> getFormats() {
        return deserializers.keySet();
    }
    public void setSpecificFields(String format, String[] fields) {

        if(deserializers.get(format) instanceof specificDeserializer){
            specificDeserializer deserializer = (specificDeserializer) deserializers.get(format);
            deserializer.setFields(fields);
        }

    }

    public String[] getSpecificFields(String format){

        if(deserializers.get(format) instanceof specificDeserializer){
            specificDeserializer deserializer = (specificDeserializer) deserializers.get(format);
            return deserializer.getFields();
        }
        return null;
    }

    public List<T> deserializeFile(String format, String filePath) throws IOException {

        Deserializer<T> deserializer = deserializers.get(format);
        if (deserializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }
        return deserializer.deserialize(filePath);
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


// In questo tipologia instanziava un deserializers ogni volta che doveva deserializzare quel formato. Ho evitato questa cosa con la mappa
/*
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
            Deserializer<T> deserializer = (Deserializer<T>) deserializerClass.getDeclaredConstructor().newInstance();
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
*/