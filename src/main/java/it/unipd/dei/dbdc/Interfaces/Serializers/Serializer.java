package it.unipd.dei.dbdc.Interfaces.Serializers;

import it.unipd.dei.dbdc.Deserializers.Serializable;

import java.io.IOException;
import java.util.List;

public interface Serializer {
    void serialize(List<Serializable> objects, String filePath) throws IOException ;

}


