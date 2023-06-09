package it.unipd.dei.dbdc.serializers.interfaces;

import it.unipd.dei.dbdc.deserialization.interfaces.UnitOfSearch;

import java.io.IOException;
import java.util.List;

public interface Serializer {
    void serialize(List<UnitOfSearch> objects, String filePath) throws IOException ;

}


