package it.unipd.dei.dbdc.UTILITY_SONO_CLASSI_CHE_NON_SERVONO_PIU.DESERIALIZERS_INTERFACE;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {

    public static final String[] formats = {"json", "xml", "csv"};

    public static final String filePath = "D:\\ingengeria software\\eis-final\\database\\databaseProva\\Serialized.json";
    public static final String folderPath = ".\\database\\databaseProva";

    public static void main(String[] args) {



        String[] jsonFields = {"id", "webUrl", "headline", "bodyText", "firstPublicationDate", "publication" };
        String[] jsonFields2 = {"id", "url", "title", "body", "date", "source" };

        String[] CSVHeaders = {"Identifier","URL","Title","Body","Date","Source Set","Source"};

        DeserializationHandler<Article> handler = new DeserializationHandler<>();


        /*
        *  l'oggetto handler registra le classi di derializzazione che si desidera utilizzare
        *  Dunque una volta specificato il formato in cui si vuole serializzare gli oggetti,
        *  Bisogna indicare il nome della classe che detiene il metodo che serializza gli oggetti nel formato specificato (La clsse implementa l'interfaccia Derializer).
        *  Nel fututo se avessi altre classi Serializer in grado di serializzare è suinserirli quì dento. Sto usando una Hashmap quindi se inserisco piu metodi che serializzano il programma utilizzera l'utltimo inserito
        *  in quanto lo considero come la versione più "nuova"
        *
        */
        handler.registerDeserializer(formats[0], new JsonDeserializer(jsonFields2));
        handler.registerDeserializer(formats[1], new XmlDeserializer());
        handler.registerDeserializer(formats[2], new CsvDeserializer(CSVHeaders));
        // usando deserializeFile adesso posso deserilizzare file json, xml, e csv. Se voglio altri formati mi basata aggiunngere ("registrare") un altro Deserializzatore

        // PROVA: 1
        // Deserializzo un file
        System.out.println("Serializzazione file:");
        List<Article> articles = new ArrayList<>();
        try {

            articles = handler.deserializeFile(formats[0], filePath ); // basta indicare il formato e il path del file
            System.out.println("Serialization completed successfully!");

        } catch (IOException e) {
            System.err.println("Serialization failed: " + e.getMessage());
        }

        // PROVA: 2

        // Deserializzo una cartella
        System.out.println("Serializzazione cartella:");
        List<Article> articles2 = new ArrayList<>();
        try {

            handler.deserializeFolder(formats[0], folderPath, articles2); // é sufficiente indicare il formato e il path della cartella
            System.out.println("Serialization completed successfully!");

        } catch (IOException e) {
            System.err.println("Serialization failed: " + e.getMessage());
            // se ci sono errori probabilmente è stato inserito un header sbagliato. oppure sono stati specificati male i campi del file json
            // bisogna segnalare all'utente e dire di reinserire i campi
        }



        // QUESTO é QUELLO CHE FAREI NEL MAIN
        System.out.println("Sono stati forniti i deserializzatori per i seguenti formati:");
        Set<String> formatsAvailable = handler.getFormats();

        for (String format : formatsAvailable) {
            System.out.println(format);
        }

        System.out.println("Nel caso in cui ci fossero file di formato differente da questi elencati non verranno presi in considerazione");
        List<Article> articles3 = new ArrayList<>();
        try {

            for (String format : formatsAvailable) {
                handler.deserializeFolder(format, folderPath, articles3);
            }

        } catch (IOException e) {
            System.err.println("Serialization failed: " + e.getMessage());
            // se ci sono errori probabilmente è stato inserito un header sbagliato. oppure sono stati specificati male i campi del file JSON
            // bisogna segnalare all'utente e dire di reinserire i campi
        }

    }

}