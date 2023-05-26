package it.unipd.dei.dbdc.UTILITY_SONO_CLASSI_CHE_NON_SERVONO_PIU.METODI_DESERIALIZZAZIONE_SERIALIZZAZIONE_PROVE;

//opencsv
import com.opencsv.bean.CsvToBeanBuilder;

//per serializzare
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;

import java.util.List;


// Questa è con CSV open.
// Unica cosa è che l'header lo specifico nella classe.
// E' meglio? peggio? non saprei.
// Forse ci sta specificarlo come costanti nel programma cosi la classe article è protetta? Da vedere
// Questo sistema che si specifica sulla classe esiste anche per json (ma funziona per json semplici) in caso da vedere.

// PEPLESSITA: l'header puo cambiare tra i file, come faccio a comprenderli entrambi.
// Ad esempio nella prima riga di nytimes_articles_v1.csv c'è come colonna "fullText", in nytimes_articles_v2.csv c'è invece body.
// Stessa roba in csv1, anche se è più gestibile con qualche controllo perche' è piu semplice selezionare le parole dell'header.

// FRANCESCO: secondo me bisogna pensare a una cosa dove selezioniamo le parole dell'header (o le mettiamo come campi pubblici
// bene in evidenza), in modo che sia facile passare da una cosa all'altra.

// Manca il serializzatore csv ma non credo serva in caso bisogna farlo.
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

        serialization_JSON_file(path_serialized_file, articles);
    }


    public static void serialization_JSON_file(String path, List<Article> articles){

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

