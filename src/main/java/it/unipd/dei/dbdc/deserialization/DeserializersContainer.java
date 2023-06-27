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

}
