package it.unipd.dei.dbdc.SERIALIZERS_FILE_PROPERTIES;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String[] formats = {"json", "xml"};
    public static final String destinationFolderPath = ".\\database\\";
    public static final String filePropertiesName = "serializers.properties";
    public static void main(String[] args) {

        try {

            List<Article> articles = createDummyArticles();
            // Creazione della lista di oggetti Serializable a partire dalla lista di Article (Article implementa Serializzable)
            List<Serializable> objects = new ArrayList<>(articles);

            String format = formats[1];
            String filename = "Serialized" ; // Percorso del file di output
            String filePath = destinationFolderPath + filename + "." + format;

            SerializationHandler handler = new SerializationHandler(filePropertiesName);

            handler.serializeObjects(objects, format, filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static List<Article> createDummyArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("Title 111", "Content 111"));
        articles.add(new Article("Title 222", "Content 222"));
        articles.add(new Article("Title 322", "Content 3333"));
        return articles;
    }

}
