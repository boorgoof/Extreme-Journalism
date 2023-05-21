package it.unipd.dei.dbdc.DESERIALIZERS;

import java.io.IOException;
import java.util.List;

interface Deserializer {
    List<Object> deserialize(String[] fields, String filePath) throws IOException ;

}


