package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.serializers.interfaces.Serializer;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import static it.unipd.dei.dbdc.serializers.SerializationProperties.readSerializersProperties;

public class SerializersContainer {

    private Map<String, Serializer> serializers;

    public SerializersContainer(String filePropertiesName) throws IOException {

        serializers = readSerializersProperties(filePropertiesName);

    }
    public Serializer getSerializer(String format) throws IOException { // TODO è giusto IOEXCEPTIoN?
        if(!serializers.containsKey(format)){
            throw new IOException("The program is not yet able to serialize a file to the requested format");
        }
        return serializers.get(format);
    }
    public Set<String> getFormats() {
        return serializers.keySet();
    }

}
