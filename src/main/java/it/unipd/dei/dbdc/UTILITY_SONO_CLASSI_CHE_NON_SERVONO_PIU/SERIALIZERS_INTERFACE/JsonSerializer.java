package it.unipd.dei.dbdc.UTILITY_SONO_CLASSI_CHE_NON_SERVONO_PIU.SERIALIZERS_INTERFACE;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class JsonSerializer implements Serializer {

    @Override
    public void serialize(List<Serializable> objects, String filePath) throws IOException {

        try{

            // Creazione dell'ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // serve per formattare tutto bene con gli spazi

            // Serializzazione della lista di articoli in un file JSON
            objectMapper.writeValue(new File(filePath), objects);

            // Serializzazione della lista di articoli in una stringa JSON
            String stringaJson = objectMapper.writeValueAsString(objects);
            System.out.println(stringaJson);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
