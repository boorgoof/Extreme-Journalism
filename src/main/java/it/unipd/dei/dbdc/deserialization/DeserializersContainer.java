package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import it.unipd.dei.dbdc.deserialization.interfaces.DeserializerWithFields;

import java.io.IOException;
import java.util.*;

import static it.unipd.dei.dbdc.deserialization.DeserializationProperties.readDeserializersProperties;

public class DeserializersContainer {
    private Map<String, Deserializer> deserializers;

    public DeserializersContainer(String filePropertiesName) throws IOException {
        deserializers = readDeserializersProperties(filePropertiesName);
    }
    public Deserializer getDeserializer(String format){
        return deserializers.get(format);
    }

    public Set<String> getFormats() {
        return deserializers.keySet();
    }
    public void setSpecificFields(String format, String[] fields) {

        if(deserializers.get(format) instanceof DeserializerWithFields){
            DeserializerWithFields deserializer = (DeserializerWithFields) deserializers.get(format);
            deserializer.setFields(fields);
        }

    }

    public String[] getSpecificFields(String format){

        if(deserializers.get(format) instanceof DeserializerWithFields){
            DeserializerWithFields deserializer = (DeserializerWithFields) deserializers.get(format);
            return deserializer.getFields();
        }
        return null;
    }


    /*
    // E' UN errore che ci siano anche qui. dovrebbero essere solo sull'handler. AL momento ho commentato ma se funziona tutto bene si possono togliere
    public List<UnitOfSearch> deserializeFile(String format, String filePath) throws IOException {

        Deserializer deserializer = deserializers.get(format);
        if (deserializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }
        return deserializer.deserialize(filePath);
    }


    public void deserializeFolder(String format, String folderPath, List<UnitOfSearch> objects) throws IOException {
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
}
