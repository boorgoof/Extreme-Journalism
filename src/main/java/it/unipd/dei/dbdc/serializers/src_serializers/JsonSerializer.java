package it.unipd.dei.dbdc.serializers.src_serializers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;

import java.io.File;
import java.io.IOException;
import java.util.List;


/**
 * Classe che implementa l'interfaccia "Serializer".
 * La classe serializza in un file ".json" una lista di oggetti "UnitOfSearch".
 * L'implementazione utilizza la libreria Jackson per la serializzazione JSON.
 * @see UnitOfSearch
 */
public class JsonSerializer implements Serializer {

    /**
     * Serializza una lista di oggetti UnitOfSearch in un file JSON.
     *
     * @param objects   Lista di oggetti da serializzare.

     * @throws IOException Se si verifica un errore durante la scrittura del file JSON.
     */
    // tanto è da togliere ora non funziona perchè è ancora con le stringhe
    @Override
    public void serialize(List<UnitOfSearch> objects, File file) throws IOException {

        // Create an instance of objectMapper for the serialization
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // serve per formattare tutto bene con gli spazi

        // Writes the objects of the list in json format to the specified file
        objectMapper.writeValue(file, objects);

        // Da togliere
        //String jsonString = objectMapper.writeValueAsString(objects);
        //System.out.println(jsonString);
    }


}
