package it.unipd.dei.dbdc.Deserialization;

import it.unipd.dei.dbdc.Deserializers.Serializable;
import it.unipd.dei.dbdc.Interfaces.Deserializers.Deserializer;
import it.unipd.dei.dbdc.Interfaces.Deserializers.specificDeserializer;
import it.unipd.dei.dbdc.PropertiesTools;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class DeserializersContainer {
    private Map<String, Deserializer> deserializers;

    public DeserializersContainer(String filePropertiesName) throws IOException {

        Properties deserializersProperties = PropertiesTools.getProperties(filePropertiesName);
        DeserializationProperties reader = new DeserializationProperties();
        deserializers = reader.readDeserializersProperties(deserializersProperties);

    }
    public Deserializer getDeserializer(String format){
        return deserializers.get(format);
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

    public List<Serializable> deserializeFile(String format, String filePath) throws IOException {

        Deserializer deserializer = deserializers.get(format);
        if (deserializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }
        return deserializer.deserialize(filePath);
    }


    public void deserializeFolder(String format, String folderPath, List<Serializable> objects) throws IOException {
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
