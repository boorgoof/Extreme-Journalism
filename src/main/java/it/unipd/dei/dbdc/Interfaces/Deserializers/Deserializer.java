package it.unipd.dei.dbdc.Interfaces.Deserializers;

import java.io.IOException;
import java.util.List;

public interface Deserializer<T> {
    List<T> deserialize(String filePath) throws IOException;
}

