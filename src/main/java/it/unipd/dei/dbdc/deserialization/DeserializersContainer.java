package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import it.unipd.dei.dbdc.deserialization.interfaces.DeserializerWithFields;
import it.unipd.dei.dbdc.deserialization.src_deserializers.JsonArticleDeserializer;
import it.unipd.dei.dbdc.serializers.SerializationProperties;
import it.unipd.dei.dbdc.serializers.SerializersContainer;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;

import java.io.IOException;
import java.util.*;

import static it.unipd.dei.dbdc.deserialization.DeserializationProperties.readDeserializersProperties;
/**
 *  This class contains all the possible {@link Deserializer} as specified in the deserializers properties file.
 *  It uses the Singleton design pattern to read only one time the properties.
 *
 */
public class DeserializersContainer {
    /**
     * A {@link Map} containing all the possible {@link Deserializer}, representing all the possible serializers that we can use.
     *
     */
    private final Map<String, Deserializer> deserializers;
    /**
     * This is the only instance of the class that is possible to obtain.
     *
     */
    private static DeserializersContainer instance;

    /**
     * This returns the only instance of this class, and initializes it if it is not.
     * Once initialized without exceptions, this function returns always the same Container.
     *
     * @param  deserializers_properties properties specified by the user where are specified all the possible {@link Deserializer}.
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

    /**
     * The constructor, which calls the {@link DeserializationProperties#readDeserializersProperties(String)} function.
     *
     * @param deserializers_properties The properties specified by the user where are specified all the possible {@link Deserializer}.
     *                               If it is null, the default properties file will be used.
     * @throws IOException If both the default and specified by the user properties files are not present, or they are incorrect.
     */
    private DeserializersContainer(String deserializers_properties) throws IOException {
        deserializers = readDeserializersProperties(deserializers_properties);
    }

    /**
     * The function returns an instance of the {@link Deserializer} whose name is passed as a parameter
     *
     * @param format The format associated with the {@link Deserializer}.
     * @return An instance of the {@link Deserializer} for the specified format.
     * @throws IllegalArgumentException If there is no {@link Deserializer} available for the specified format, or the parameters passed are not valid.
     *
     */
    public Deserializer getDeserializer(String format) throws IOException{
        if(!deserializers.containsKey(format)){
            throw new IllegalArgumentException("the program is not yet able to deserialize the requested format");
        }
        return deserializers.get(format);
    }
    /**
     * The function returns a {@link Set} of the formats of all {@link Deserializer}s in the container.
     *
     * @return A {@link Set} of strings representing the supported formats.
     */
    public Set<String> getFormats() {
        return deserializers.keySet();
    }

    /**
     * the function sets the new fields that will be considered when deserializing the specified format
     * The function sets the fields only if the {@link Deserializer} in implements the interface {@link DeserializerWithFields}
     *
     * @param format The format for which the fields will be set.
     * @param fields The new fields that will be considered during deserialization
     * @throws IOException If the selected deserializer does not implement the interface {@link DeserializerWithFields}
     *
     */
    public void setSpecificFields(String format, String[] fields){

        if(deserializers.get(format) instanceof DeserializerWithFields){
            DeserializerWithFields deserializer = (DeserializerWithFields) deserializers.get(format);
            deserializer.setFields(fields);
        } else {
            throw new IllegalArgumentException("The selected deserializer does not implement field specification");

        }

    }

    /**
     * the function provides the fields taken into account during deserialization for the specified format
     * The function sets the fields only if the {@link Deserializer} in implements the interface {@link DeserializerWithFields}
     *
     * @param format The format for which the fields are required
     * @return An array of {@link String} representing the fields.
     * @throws IOException If the selected deserializer does not implement the interface {@link DeserializerWithFields}
     *
     */
    public String[] getSpecificFields(String format){

        if(deserializers.get(format) instanceof DeserializerWithFields){
            DeserializerWithFields deserializer = (DeserializerWithFields) deserializers.get(format);
            return deserializer.getFields();
        }
        return null;
    }

    /**
     * Returns a boolean value indicating whether the {@link DeserializersContainer#deserializers} is empty.
     *
     * @return {@code true} if the {@link DeserializersContainer#deserializers} is empty, {@code false} otherwise.
     */
    public boolean isEmpty(){
        return deserializers.isEmpty();
    }

}
