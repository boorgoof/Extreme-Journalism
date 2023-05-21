package it.unipd.dei.dbdc.SERIALIZERS_INTERFACE;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String[] formats = {"json", "xml", "csv"};
    public static final String destinationFolderPath = ".\\database\\";
    public static void main(String[] args) {

        List<Article> articles = createDummyArticles();

        // Creazione della lista di oggetti Serializable a partire dalla lista di Article (Article implementa Serializzable)
        List<Serializable> objects = new ArrayList<>(articles);

        // Creazione dell'istanza di SerializationHandler
        SerializationHandler handler = new SerializationHandler();


        // l'oggeto handler registra le classi di serializzazione che si desidera utilizzare
        // Dunque una volta specificato il formato in cui si vuole serializzare gli oggetti,
        // Bisogna indicare il nome della classe che detiene il metodo che serializza gli oggetti nel formato specificato (La clsse implementa l'interfaccia Serializer).
        handler.registerSerializer(formats[0],new JsonSerializer() );
        handler.registerSerializer(formats[1], new XmlSerializer() );
        handler.registerSerializer(formats[2], new CsvSerializer() );
        // Nel fututo se avessi altre classi Serializer in grado di serializzare è suinserirli quì dento. Sto usando una Hashmap quindi se inserisco piu metodi che serializzano il programma utilizzera l'utltimo inserito
        // in quanto lo considero come la versione più "nuova"



        String format = formats[1];
        String filename = "SerializedIterface." ; // Percorso del file di output
        String filePath = destinationFolderPath + filename + format;

        try {

            handler.serializeObjects(objects, format, filePath);
            System.out.println("Serialization completed successfully!");

        } catch (IOException e) {
            System.err.println("Serialization failed: " +  e.getMessage());
        }

    }
    private static List<Article> createDummyArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("Title 1", "Content 1"));
        articles.add(new Article("Title 2", "Content 2"));
        articles.add(new Article("Title 3", "Content 3"));
        return articles;
    }

}
