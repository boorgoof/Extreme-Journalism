package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.Deserializers.Article;
import it.unipd.dei.dbdc.Handlers.DeserializationHandler;
import it.unipd.dei.dbdc.Handlers.DownloadHandler;
import it.unipd.dei.dbdc.Search_terms.Analyzer;
import it.unipd.dei.dbdc.Search_terms.MapAnalyzer;
import it.unipd.dei.dbdc.Handlers.SerializationHandler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class App
{
    // TODO: prendi tutto da properties, e fai in modo che ti possano passare un properties diverso da CLI
    private static final String database_path = "./database/";
    private static final String deserializers_properties = "deserializers.properties";

    private static final String serializers_properties = "serializers.properties";

    private static final String download_properties = "download.properties";

    private static final String common_format = "xml";

    private static final String outFile = "./database/output.txt";

    public static void main( String[] args ) throws IOException {
        // TODO: interactive
        // TODO: sistema il main

        // L'utente puo' passare da riga di comando quello che vuole fare.
        CommandLineInterpreter interpreter = new CommandLineInterpreter(args);
        if (interpreter.help())
        {
            return;
        }

        // Se non passa da linea di comando, gli si chiede interattivamente
        // TODO: interactive

        //if (interpreter.downloadPhase()) {
            // Se vuole download, passo a download handler
            System.out.println(ConsoleTextColors.BLUE + "Entering the download part..." + ConsoleTextColors.RESET);
            String name = null ; //interpreter.obtainDownloadOptions();
            String folderPath = DownloadHandler.download(database_path, name);
            System.out.println(ConsoleTextColors.BLUE + "Exiting the download part..." + ConsoleTextColors.RESET);
        //}
        //if (interpreter.searchPhase())
        //{
            // DESERIALIZZAZIONE formato fornito -> Article
            DeserializationHandler<Article> deserializer = new DeserializationHandler<>(deserializers_properties);

            System.out.println(ConsoleTextColors.BLUE + "Sono stati forniti i deserializzatori per i seguenti formati:"+ ConsoleTextColors.RESET);
            Set<String> formatsAvailable = deserializer.getFormats();
            for (String format : formatsAvailable) {
                System.out.println(format);
            }

            System.out.println(ConsoleTextColors.BLUE +"Nel caso in cui ci fossero file di formato differente da questi elencati non verranno presi in considerazione"+ ConsoleTextColors.RESET);

            List<Article> articles = new ArrayList<>();
            try {

                for (String format : formatsAvailable) {
                    deserializer.deserializeFolder(format, folderPath, articles);
                }

            } catch (IOException e) {
                System.err.println(ConsoleTextColors.RED + "Deserializzazione fallita per il formato: " + e.getMessage() + ConsoleTextColors.RESET);
                // se ci sono errori probabilmente è stato inserito un header sbagliato. oppure sono stati specificati male i campi del file JSON
                // bisogna segnalare all'utente e dire di reinserire i campi
                // TODO: return?
            }

            //SERIALIZZAZIONE Article -> formato comune
            String filename = "Serialized" ; // Percorso del file di output. TODO: facciamo in modo che il nome cambi?
            String filePath = database_path +"/"+ filename + "." + common_format;
            try {

                // Creazione della lista di oggetti Serializable a partire dalla lista di Article (Article implementa Serializable)
                List<Serializable> objects = new ArrayList<>(articles);



                SerializationHandler serializer = new SerializationHandler(serializers_properties);

                serializer.serializeObjects(objects, common_format, filePath);

            } catch (IOException e) {
                e.printStackTrace();
                // TODO: return?
            }

            // DESERIALIZZAZIONE formato comune -> articles
            List<Article> articles2 = new ArrayList<>();
            try {

                for (String format : formatsAvailable) {
                    articles2 = deserializer.deserializeFile(common_format, filePath);
                }

            } catch (IOException e) {
                System.err.println(ConsoleTextColors.RED + "Deserializzazione fallita per il formato: " + e.getMessage() + ConsoleTextColors.RESET);
                // se ci sono errori probabilmente è stato inserito un header sbagliato. oppure sono stati specificati male i campi del file JSON
                // bisogna segnalare all'utente e dire di reinserire i campi
                // TODO: return?
            }


            // ESTRAZIONE DEI TERMINI PIU' IMPORTANTI
            //creo un arraylist con tutti i termini come chiavi e numero di articoli in cui sono presenti come valori
            System.out.println(ConsoleTextColors.BLUE + "Scrittura primi 50 termini più presenti in corso.."+ConsoleTextColors.RESET);
            Analyzer<Article> analyzer = new MapAnalyzer();
            ArrayList<it.unipd.dei.dbdc.Search_terms.MapEntry> max = analyzer.mostPresent(articles);

            //stampo i primi 50 termini più presenti nei vari articoli
            MapAnalyzer.outFile(max, outFile);
        //}
    }

}









