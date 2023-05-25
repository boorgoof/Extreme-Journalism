package it.unipd.dei.dbdc.SEARCH_TERMS;

import it.unipd.dei.dbdc.DESERIALIZERS.*;
import it.unipd.dei.dbdc.DESERIALIZERS.Article;

import java.io.*;
import java.util.*;

public class main {
    public static final String[] formats = {"json", "xml", "csv"};

    public static final String filePath = "/Users/giovannidemaria/IdeaProjects/eis-final/database/databaseProva/Serialized.json";

    public static final String filePath2 = "/Users/giovannidemaria/IdeaProjects/eis-final/database/fileSerializzato.json";

    public static final String outFilePath ="/Users/giovannidemaria/IdeaProjects/eis-final/src/main/java/it/unipd/dei/dbdc/SEARCH_TERMS/output.txt";
    public static void main(String[] args) {

        String[] jsonFields = {"id", "webUrl", "headline", "bodyText", "firstPublicationDate", "publication" };
        String[] jsonFields2 = {"id", "url", "title", "body", "date", "source" };

        String[] CSVHeaders = {"Identifier","URL","Title","Body","Date","Source Set","Source"};

        DeserializationHandler<Article> handler = new DeserializationHandler<>();

        handler.registerDeserializer(formats[0], new JsonDeserializer(jsonFields2));
        handler.registerDeserializer(formats[1], new XmlDeserializer());
        handler.registerDeserializer(formats[2], new CsvDeserializer(CSVHeaders));

        List<Article> articles = new ArrayList<>();
        try {

            articles = handler.deserializeFile(formats[0], filePath2 ); // basta indicare il formato e il path del file

        } catch (IOException e) {
            System.err.println("Serialization failed: " + e.getMessage());
        }
        Set<String> formatsAvailable = handler.getFormats();

        //creo un arraylist con tutti i termini come chiavi e numero di articoli in cui sono presenti come valori
        ArrayList<it.unipd.dei.dbdc.SEARCH_TERMS.MyOtherEntry> max = MyPriorityQueue.mostPresent(articles);

        //stampo i primi 50 termini più presenti nei vari articoli
        MyPriorityQueue.outFile(max, outFilePath);
    }
}