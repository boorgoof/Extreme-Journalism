package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.serializers.interfaces.Serializer;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static it.unipd.dei.dbdc.serializers.SerializationProperties.readSerializersProperties;
/**
 * This class contains all the possible {@link Serializer} as specified in the serializers properties file.
 *  It uses the Singleton design pattern to read only one time the properties.
 *
 */
public class SerializersContainer {

    /**
     * A {@link Map} containing all the possible {@link Serializer}, representing all the possible serializers that we can use.
     *
     */
    private final Map<String, Serializer> serializers;

    /**
     * This is the only instance of the class that is possible to obtain.
     *
     */
    private static SerializersContainer instance;

    /**
     * This returns the only instance of this class, and initializes it if it is not.
     * Once initialized without exceptions, this function returns always the same Container.
     *
     * @param serializers_properties The properties specified by the user where are specified all the possible {@link Serializer}.
     *                               If it is null, the default properties file will be used.
     * @return The {@link SerializersContainer} object instantiate from serializers_properties
     * @throws IOException If both the default and specified by the user properties files are not present, or they are incorrect.
     */
    public static SerializersContainer getInstance(String serializers_properties) throws IOException
    {
        if (instance == null)
        {
            instance = new SerializersContainer(serializers_properties);
        }
        return instance;
    }

    /**
     * The constructor, which calls the {@link SerializationProperties#readSerializersProperties(String)} function.
     *
     * @param serializers_properties The properties specified by the user where are specified all the possible {@link Serializer}.
     *                               If it is null, the default properties file will be used.
     * @throws IOException If both the default and specified by the user properties files are not present, or they are incorrect.
     */
    private SerializersContainer(String serializers_properties) throws IOException {

        serializers = readSerializersProperties(serializers_properties);

    }

    /**
     * The function which return an instance of the {@link Serializer} whose name is passed as a parameter
     *
     * @param format The format associated with the {@link Serializer}.
     * @return An instance of the {@link Serializer} for the specified format.
     * @throws IllegalArgumentException If there is no {@link Serializer} available for the specified format, or the parameters passed are not valid.
     *
     */
    public Serializer getSerializer(String format) {

        if(!serializers.containsKey(format)){
            throw new IllegalArgumentException("The program is not yet able to serialize a file to the requested format");
        }
        return serializers.get(format);
    }
    /**
     * A function that returns a {@link Set} of the formats of all {@link Serializer}s in the container.
     *
     * @return A {@link Set} of strings representing the supported formats.
     */
    public Set<String> getFormats() {
        return serializers.keySet();
    }

}
