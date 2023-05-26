package it.unipd.dei.dbdc.DESERIALIZERS_FILE_PROPERTIES;

import java.io.IOException;
import java.util.List;

interface Deserializer<T> {
    List<T> deserialize(String filePath) throws IOException;
    //List<T> deserialize(String filePath, String[] fields) throws IOException;
}



