package it.unipd.dei.dbdc.serializers;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static it.unipd.dei.dbdc.serializers.SerializationProperties.readSerializersProperties;
/**
 * This class contains all the possible {@link Serializer} as specified in the serializers properties file.
 *
 */
public class SerializersContainer {

    private Map<String, Serializer> serializers;

    public SerializersContainer(String filePropertiesName) throws IOException {

        serializers = readSerializersProperties(filePropertiesName);

    }
    public Serializer getSerializer(String format) {
        if(!serializers.containsKey(format)){
            throw new IllegalArgumentException("The program is not yet able to serialize a file to the requested format");
        }
        return serializers.get(format);
    }
    public Set<String> getFormats() {
        return serializers.keySet();
    }

}
