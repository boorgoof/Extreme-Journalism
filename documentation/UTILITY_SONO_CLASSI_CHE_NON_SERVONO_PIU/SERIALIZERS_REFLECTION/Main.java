package it.unipd.dei.dbdc.UTILITY_SONO_CLASSI_CHE_NON_SERVONO_PIU.SERIALIZERS_REFLECTION;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String destinationFolderPath = ".\\database\\";
    public static void main(String[] args) {
        List<Article> articles = createDummyArticles();

        SerializationHandler handler = new SerializationHandler();

        // l'oggeto handler registra i metodi di serializzazione che si desidera utilizzare
        // Dunque una volta specificato il formato in cui si vuole Serializzare gli oggetti,
        // Bisogna indicare il nome della classe che detiene il metodo che serializza gli oggetti nel formato specificato (Bisogna specificare anche il metodo in questione, con i rispettivi parametri).
        try {

            handler.registerSerializer("json", JsonSerializer.class.getDeclaredMethod("serializeToJSON", List.class, String.class));
            handler.registerSerializer("xml", XmlSerializer.class.getDeclaredMethod("serializeToXML", List.class, String.class));
            // Nel fututo se avessi altri metodi in grado di serializzare è suinserirli quì dento. Sto usando una Hashmap quindi se inserisco piu metodi che serializzano il programma utilizzera l'utltimo inserito
            // in quanto lo considero come la versione più "nuova"

        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }


        String format = "xml"; // Formato di serializzazione desiderato (es. "json", "xml", "csv")
        String filename = "Serialized." ; // Percorso del file di output
        String filePath = destinationFolderPath + filename + format;

        try {

            handler.serializeObjects(articles, format, filePath); // Serializza la lista di oggetti nel formato specificato
            System.out.println("Serialization successful.");
            //nel caso in cui tu abbia più metodi registrati che serializzano per lo stesso formato di serializzazione, l'handler utilizzerà l'ultimo metodo registrato per quel formato.

        } catch (IOException e) {
            System.out.println("Serialization failed: " + e.getMessage());
        }


        // stessa cosa per un altro formato ipoteticamente.
        /*

        String format = "json"; // Formato di serializzazione desiderato (es. "json", "xml", "csv")
        String filename = "Serialized." ; // Percorso del file di output
        String filePath = destinationFolderPath + filename + format;

        try {
            handler.serializeObjects(articles, format, filePath); // Serializza la lista di oggetti nel formato specificato
            System.out.println("Serialization successful.");
        } catch (IOException e) {
            System.out.println("Serialization failed: " + e.getMessage());
        }
        */
    }
    private static List<Article> createDummyArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("Title 1", "Content 1"));
        articles.add(new Article("Title 2", "Content 2"));
        articles.add(new Article("Title 3", "Content 3"));
        return articles;
    }
}
