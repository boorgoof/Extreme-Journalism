package it.unipd.dei.dbdc.deserialization.interfaces;

import java.io.IOException;
import java.util.List;

public interface Deserializer {
    List<UnitOfSearch> deserialize(String filePath) throws IOException;
}

