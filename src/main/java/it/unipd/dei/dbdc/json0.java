package it.unipd.dei.dbdc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

// funziona solo per i json semplici, non annidati
// l'ho messo solo a scopo didattico
// attumelte xml0 è uguale a questo ma per gli xml
// json1 è quello migliore mio parere
public class json0 {

    public static List<Article> deserializeFromJSON(String path) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            File jsonFile = new File(path);

            return objectMapper.readValue(jsonFile, new TypeReference<List<Article>>() {});
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList(); // In caso di errore, restituisce una lista vuota
    }
    public static void main(String[] args) {

        //json
        List<Article> articles2 = deserializeFromJSON("D:\\ingengeria software\\eis-final\\database\\fileSerializzato.json");

        for(Article article : articles2 ){

            System.out.println(article.toString());
        }
    }
}
