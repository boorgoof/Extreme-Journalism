package it.unipd.dei.dbdc.UTILITY_SONO_CLASSI_CHE_NON_SERVONO_PIU.SERIALIZERS_INTERFACE;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

interface Serializer {
    void serialize(List<Serializable> objects, String filePath) throws IOException ;

}


