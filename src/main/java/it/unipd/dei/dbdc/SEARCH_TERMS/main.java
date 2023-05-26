package it.unipd.dei.dbdc.SEARCH_TERMS;

import it.unipd.dei.dbdc.DESERIALIZERS_FILE_PROPERTIES.DeserializationHandler;
import it.unipd.dei.dbdc.DESERIALIZERS_FILE_PROPERTIES.Article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class main {
    public static final String[] formats = {"json", "xml", "csv"};


    public static final String filePath = "/Users/giovannidemaria/IdeaProjects/eis-final/database/fileSerializzato.json";
    public static final String filePath2 = "/Users/giovannidemaria/IdeaProjects/eis-final/src/main/java/it/unipd/dei/dbdc/SEARCH_TERMS/english_stoplist_v1.txt";



    public static final String outFilePath ="./SEARCH_TERMS/output.txt";
    public static final String filePropertiesName = "deserializers.properties";
    public static void main(String[] args) {

        try {
            DeserializationHandler<Article> handler = new DeserializationHandler<>(filePropertiesName);

            System.out.println("Serializzazione file:");
            List<Article> articles;

            articles = handler.deserializeFile(formats[0], filePath); // basta indicare il formato e il path del file
            System.out.println("Serialization completed successfully!");

            System.out.println("Sono stati forniti i deserializzatori per i seguenti formati:");
            Set<String> formatsAvailable = handler.getFormats();
            for (String format : formatsAvailable) {
                System.out.println(format);
            }

            //creo un arraylist con tutti i termini come chiavi e numero di articoli in cui sono presenti come valori
            System.out.println("scrittura primi 50 termini più presenti in corso..");
            ArrayList<it.unipd.dei.dbdc.SEARCH_TERMS.MyOtherEntry> max = MyPriorityQueue.mostPresent(articles);

            //stampo i primi 50 termini più presenti nei vari articoli
            MyPriorityQueue.outFile(max, outFilePath);
        } catch (IOException e) {
            throw new RuntimeException("Controlla di aver caricato i fields  di interesse corretti " + e);
        }

    }
}