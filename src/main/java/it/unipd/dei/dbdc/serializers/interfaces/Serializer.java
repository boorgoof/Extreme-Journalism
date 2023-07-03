package it.unipd.dei.dbdc.serializers.interfaces;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Serializer {
    void serialize(List<UnitOfSearch> objects, File file) throws IOException ;

}


