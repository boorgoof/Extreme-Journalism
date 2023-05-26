package it.unipd.dei.dbdc.DESERIALIZERS_FILE_PROPERTIES;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {

    public static final String[] formats = {"json", "xml", "csv"};

    public static final String filePath = ".\\database\\databaseProva\\Serialized.json";
    public static final String folderPath = ".\\database\\databaseProva";
    public static final String filePropertiesName = "deserializers.properties";

    public static void main(String[] args) {

        String[] jsonFields = {"id", "webUrl", "headline", "bodyText", "firstPublicationDate", "publication" };

        String[] jsonFields2 = {"id", "url", "title", "body", "date", "source" };

        String[] CSVHeaders = {"Identifier","URL","Title","Body","Date","Source Set","Source"};

        try {
            DeserializationHandler<Article> handler = new DeserializationHandler<>(filePropertiesName);


            // PROVA: 1. DESERIALIZZO UN FILE
            System.out.println("Serializzazione file:");
            List<Article> articles = new ArrayList<>();

            articles = handler.deserializeFile(formats[0], filePath ); // basta indicare il formato e il path del file
            System.out.println("Serialization completed successfully!");


            // QUESTO é QUELLO CHE FAREI NEL MAIN
            System.out.println("Sono stati forniti i deserializzatori per i seguenti formati:");
            Set<String> formatsAvailable = handler.getFormats();
            for (String format : formatsAvailable) {
                System.out.println(format);
            }

            System.out.println("Nel caso in cui ci fossero file di formato differente da questi elencati non verranno presi in considerazione");

            List<Article> articles2 = new ArrayList<>();
            try {

                for (String format : formatsAvailable) {
                    handler.deserializeFolder(format, folderPath, articles2);
                }

            } catch (IOException e) {
                System.err.println("Serialization failed: " + e.getMessage());
                // se ci sono errori probabilmente è stato inserito un header sbagliato. oppure sono stati specificati male i campi del file JSON
                // bisogna segnalare all'utente e dire di reinserire i campi
            }

        } catch (IOException e) {
            throw new RuntimeException("Controlla di aver caricato i fields  di interesse corretti " + e);
        }

    }

}
