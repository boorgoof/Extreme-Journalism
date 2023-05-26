package it.unipd.dei.dbdc.UTILITY_SONO_CLASSI_CHE_NON_SERVONO_PIU.DESERIALIZERS_INTERFACE;

import java.io.IOException;
import java.util.List;

interface Deserializer<T> {
    List<T> deserialize(String filePath) throws IOException;
}


