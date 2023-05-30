package it.unipd.dei.dbdc.Interfaces.Serializers;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public interface Serializer {
    void serialize(List<Serializable> objects, String filePath) throws IOException ;

}


