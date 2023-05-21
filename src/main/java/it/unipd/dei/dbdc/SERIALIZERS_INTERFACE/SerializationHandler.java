package it.unipd.dei.dbdc.SERIALIZERS_INTERFACE;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SerializationHandler {
    private Map<String, Serializer> serializers; // intellij mi dice che lo vuole final non so il perchè in realtà secondo me non va final

    public SerializationHandler() {
        serializers = new HashMap<>();
    }

    public void registerSerializer(String format, Serializer serializer) {

        serializers.put(format, serializer);
    }

    public void serializeObjects(List<Serializable> objects, String format, String filePath) throws IOException {

        Serializer serializer = serializers.get(format);

        if (serializer == null) {
            throw new UnsupportedOperationException("No serializer found for the specified format: " + format);
        }

        try {
            serializer.serialize(objects, filePath);
        } catch (Exception e) {
            throw new IOException("Serialization failed for format: " + format, e);
        }
    }

}


