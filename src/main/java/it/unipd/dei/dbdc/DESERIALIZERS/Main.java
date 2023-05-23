package it.unipd.dei.dbdc.DESERIALIZERS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String[] formats = {"json", "xml", "csv"};
    public static final String[] jsonFields = {"id", "webUrl", "headline", "bodyText", "firstPublicationDate", "publication" };
    public final static String[] CSVcustomHeaders = {"Identifier","URL","Title","Body","Date","Source Set","Source"}; // Custom header names
    public static final String folderPath = ".\\database\\the_guardian";

    public static void main(String[] args) {

        List<Article> articles = new ArrayList<>();

        DeserializationHandler handler = new DeserializationHandler();


        /*
        *  l'oggetto handler registra le classi di derializzazione che si desidera utilizzare
        *  Dunque una volta specificato il formato in cui si vuole serializzare gli oggetti,
        *  Bisogna indicare il nome della classe che detiene il metodo che serializza gli oggetti nel formato specificato (La clsse implementa l'interfaccia Derializer).
        *  Nel fututo se avessi altre classi Serializer in grado di serializzare è suinserirli quì dento. Sto usando una Hashmap quindi se inserisco piu metodi che serializzano il programma utilizzera l'utltimo inserito
        *  in quanto lo considero come la versione più "nuova"
        *
        */
        handler.registerDeserializer("csv", new CsvDeserializer());
        handler.registerDeserializer("json", new JsonDeserializer());


        try {

            List<Object> objects = new ArrayList<>();

            objects = handler.deserializeFolder(formats[0], jsonFields, folderPath, objects);
            System.out.println("Serialization completed successfully!");

            articles = convertList(objects);

        } catch (IOException e) {
            System.err.println("Serialization failed: " + e.getMessage());
        }

    }

    public static List<Article> convertList(List<Object> objects){

        List<Article> articles = new ArrayList<>();

        for (Object obj : objects) {
            if (obj instanceof Article) {
                Article article = (Article) obj;
                System.out.println(article);
                System.out.println("\n");
                articles.add(article);
            }
        }

        return articles;
    }
}