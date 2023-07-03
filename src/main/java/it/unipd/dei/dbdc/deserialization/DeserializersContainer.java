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
    public Deserializer getDeserializer(String format) throws IOException{
        if(!deserializers.containsKey(format)){
            throw new IOException("the program is not yet able to deserialize the requested format");
        }
        return deserializers.get(format);
    }

    public Set<String> getFormats() {
        return deserializers.keySet();
    }
    public void setSpecificFields(String format, String[] fields) throws IOException{

        if(deserializers.get(format) instanceof DeserializerWithFields){
            DeserializerWithFields deserializer = (DeserializerWithFields) deserializers.get(format);
            deserializer.setFields(fields);
        } else {
            throw new IOException("the selected deserializer does not implement field specification");

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
