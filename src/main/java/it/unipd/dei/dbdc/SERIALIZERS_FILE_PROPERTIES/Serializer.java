package it.unipd.dei.dbdc.SERIALIZERS_FILE_PROPERTIES;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

interface Serializer {
    void serialize(List<Serializable> objects, String filePath) throws IOException ;

}


