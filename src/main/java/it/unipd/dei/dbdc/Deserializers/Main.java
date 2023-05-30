package it.unipd.dei.dbdc.Deserializers;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {

    public static final String[] formats = {"json", "xml", "csv"};

    public static final String filePath = ".\\database\\fileSerializzato50.json";
    public static final String folderPath = ".\\database\\TheGuardianAPI";
    public static final String filePropertiesName = "deserializers.properties";

    public static void main(String[] args) {

        String[] jsonFields = {"id", "webUrl", "headline", "bodyText", "firstPublicationDate", "publication" };

        String[] jsonFields2 = {"id", "url", "title", "body", "date", "source" };

        String[] CSVHeaders = {"Identifier","URL","Title","Body","Date","Source Set","Source"};

        try {
            DeserializationHandler<Article> handler = new DeserializationHandler<>(filePropertiesName);

            // PROVA: 1. DESERIALIZZO UN FILE
            /*
            System.out.println("Deserializzazione file:");
            List<Article> articles = new ArrayList<>();

            articles = handler.deserializeFile(formats[0], filePath ); // basta indicare il formato e il path del file
            System.out.println("Serialization completed successfully!");

             */


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
                System.err.println("Deserializzazione fallita per il formato: " + e.getMessage());
                // se ci sono errori probabilmente è stato inserito un header sbagliato. oppure sono stati specificati male i campi del file JSON
                // bisogna segnalare all'utente e dire di reinserire i campi
            }

        } catch (IOException e) {
            throw new RuntimeException("Controlla di aver caricato i fields  di interesse corretti " + e);
        }

    }

}


/*
    specificDeserializer deserializer;
    // Crea uno scanner per leggere l'input dell'utente
    Scanner scanner = new Scanner(System.in);

    String risposta = scanner.nextLine();

    System.out.println("Inserisci una parola alla volta per indicare i campi di interesse. Ricorda che sono sei parole in tutto");

    String[] fields = new String[6];
    for (int i = 0; i < fields.length; i++) {
        System.out.print("Campo " + (i + 1) + ": ");
        fields[i] = scanner.nextLine();
    }

    deserializer.setFields(fields);

 */
 /*
    System.out.println("E' fallita la deserializzazione per il formato" + format);

    if(deserializer instanceof specificDeserializer){

        specificDeserializer specDeserializer = (specificDeserializer) deserializer;

        System.out.println("Sono attualmente deserializzati i seguenti campi: ");
        System.out.println(specDeserializer.getFields());
        System.out.println("Vuole per caso modificarli? ");
        Scanner scanner = new Scanner(System.in);
        String answer = scanner.nextLine();
        setSpecificSerializer(specDeserializer);
    }
*/