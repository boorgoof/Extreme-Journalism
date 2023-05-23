package it.unipd.dei.dbdc.DESERIALIZERS;

import java.io.IOException;
import java.util.List;

interface Deserializer<T> {
    List<T> deserialize(String filePath) throws IOException;
}


