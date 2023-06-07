package it.unipd.dei.dbdc.Serializers;

import it.unipd.dei.dbdc.Deserializers.Serializable;
import it.unipd.dei.dbdc.Interfaces.Serializers.Serializer;

import java.io.IOException;
import java.util.List;

public class CsvSerializer implements Serializer {
    @Override
    public void serialize(List<Serializable> objects, String filePath) throws IOException {

    }

    // non l'ho ancora fatto ma perchè al momento non serve

}
