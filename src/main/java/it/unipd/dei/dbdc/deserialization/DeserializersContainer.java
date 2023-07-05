package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import it.unipd.dei.dbdc.deserialization.interfaces.DeserializerWithFields;
import it.unipd.dei.dbdc.serializers.SerializersContainer;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;

import java.io.IOException;
import java.util.*;

import static it.unipd.dei.dbdc.deserialization.DeserializationProperties.readDeserializersProperties;

public class DeserializersContainer {
    /**
     * A {@link Map} containing all the possible {@link Deserializer}, representing all the possible serializers that we can use.
     *
     */
    private Map<String, Deserializer> deserializers;
    /**
     * This is the only instance of the class that is possible to obtain.
     *
     */
    private static DeserializersContainer instance;

    /**
     * This returns the only instance of this class, and initializes it if it is not.
     * Once initialized without exceptions, this function returns always the same Container.
     *
     * @param  deserializers_properties properties specified by the user where are specified all the possible {@link Serializer}.
     *                                  If it is null, the default properties file will be used.
     * @throws IOException If both the default and specified by the user properties files are not present, or they are incorrect.
     */
    public static DeserializersContainer getInstance(String deserializers_properties) throws IOException
    {
        if (instance == null)
        {
            instance = new DeserializersContainer(deserializers_properties);
        }
        return instance;
    }

    private DeserializersContainer(String filePropertiesName) throws IOException {
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
