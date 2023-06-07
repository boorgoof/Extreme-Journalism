package it.unipd.dei.dbdc.Interfaces.Deserializers;

import it.unipd.dei.dbdc.Deserializers.Serializable;

import java.io.IOException;
import java.util.List;

public interface Deserializer {
    List<Serializable> deserialize(String filePath) throws IOException;
}

