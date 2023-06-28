package it.unipd.dei.dbdc.deserialization.interfaces;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Deserializer {

    // todo: per me dovrebbe lavorare con i serializable poi handler li mette in una lista di unitOfSearch, che dovrebbe estendere serializable. cosi per me funziona
    List<UnitOfSearch> deserialize(File file) throws IOException;
}

