package it.unipd.dei.dbdc.deserialization.interfaces;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Deserializer {
    List<UnitOfSearch> deserialize(String filePath) throws IOException;
}

