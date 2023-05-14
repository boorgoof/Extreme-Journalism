package it.unipd.dei.dbdc;

//opencsv
import com.opencsv.bean.CsvToBeanBuilder;

//per serializzare
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;

import java.util.List;


// questa è con CSV open. unica cosa è che l'header lo specico nella classe. E' meglio? peggio? non saprei. forse ci sta specificarlo come costanti nel programma cosi la classe article è protetta? da vedere
// questo sistema che si specifica sulla classe esiste anche per json ( ma funziona per json semplici) in caso da vedere.

// PEPLESSITA: l'header puo cambiare tra i file, come faccio a comprenderli entrambi. Ad esempio nella prima rica di nytimes_articles_v1.csv c'è come colloa "fullText", in nytimes_articles_v2.csv c'è invece body.
// stessa roba in csv1, anche se è più gestibile con qualche controllo perche è piu semplice selezionare le parole dell'header.

// manca il serializzatore csv ma non credo serva in caso bisogna farlo.
public class csv0 {
    public static void main( String[] args ) {

        String fileName = ".\\database\\csv\\nytimes_articles_v2.csv";
        String path_serialized_file = ".\\database\\fileSerializzato3.json";

        List<Article> articles;
        try {

            articles = new CsvToBeanBuilder(new FileReader(fileName)).withType(Article.class).build().parse();

            articles.forEach(System.out::println);

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        serializazion_JSON_file(path_serialized_file, articles);
    }


    public static void serializazion_JSON_file(String path, List<Article> articles){

        try{

            // Creazione dell'ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT); // serve per formattare tutto bene con gli spazi

            // Serializzazione della lista di articoli in un file JSON
            objectMapper.writeValue(new File(path), articles);

            // Serializzazione della lista di articoli in una stringa JSON
            String stringaJson = objectMapper.writeValueAsString(articles);
            System.out.println(stringaJson);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

