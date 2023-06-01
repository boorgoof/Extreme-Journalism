package it.unipd.dei.dbdc;


import it.unipd.dei.dbdc.Deserializers.Article;
import it.unipd.dei.dbdc.Handlers.DeserializationHandler;
import it.unipd.dei.dbdc.Handlers.DownloadHandler;
import it.unipd.dei.dbdc.Search_terms.Analyzer;
import it.unipd.dei.dbdc.Search_terms.MapArrayScannerAnalyzer;
import it.unipd.dei.dbdc.Handlers.SerializationHandler;
import it.unipd.dei.dbdc.Search_terms.MapArraySplitAnalyzer;
import it.unipd.dei.dbdc.Search_terms.MapEntrySI;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class App
{
    // TODO: prendi tutto da properties, e fai in modo che ti possano passare un properties diverso da CLI. Oppure metti le properties fuori da src
    private static final String database_path = "./database/";

    private static final String deserializers_properties = "deserializers.properties";
    private static final String serializers_properties = "serializers.properties";
    private static final String download_properties = "download.properties";

    private static final String common_format = "xml";

    private static final String outFile = "./database/output.txt";

    // TODO: remove from here
    private static int tot_count = 50;

    public static void main( String[] args ) {

        // L'utente deve passare da riga di comando l'azione che vuole fare.
        CommandLineInterpreter interpreter;
        try {
            interpreter = new CommandLineInterpreter(args);
        }
        catch (IllegalStateException e)
        {
            System.err.println("Programma terminato perche' non e' stata fornita una azione da compiere.");
            return;
        }

        if (interpreter.help())
        {
            return;
        }

        // Folder to serialize to common format.
        String folderPath = null;

        // FASE 1: download
        if (interpreter.downloadPhase()) {

            System.out.println(ConsoleTextColors.BLUE + "Entering the download part..." + ConsoleTextColors.RESET);

            // The only download option is the path of the properties file for the API to call.
            String props = interpreter.obtainAPIProps();
            try {
                folderPath = DownloadHandler.download(database_path, download_properties, props);
            }
            catch (IOException e)
            {
                System.err.println("Errore nella fase di download: ");
                e.printStackTrace();
                return;
            }
            System.out.println(ConsoleTextColors.BLUE + "Exiting the download part..." + ConsoleTextColors.RESET);
        }

        // FASE 2: avviene sempre, è la serializzazione in formato comune

        // Percorso del file serializzato. TODO: facciamo in modo che il nome cambi?
        String filePath = database_path + "/Serialized." + common_format;

        // A. DESERIALIZZAZIONE formato fornito -> Article
        String path_cli = interpreter.obtainPathOption();

        if (path_cli != null && folderPath == null) {
            System.out.println(ConsoleTextColors.RED + "Errore: nessun file da deserializzare"+ConsoleTextColors.RESET);
            return;
        } else if (path_cli != null) {
            folderPath = path_cli;
        }

        System.out.println(ConsoleTextColors.BLUE+"Inizio deserializzazione di "+folderPath+"..."+ConsoleTextColors.RESET);
        DeserializationHandler<Article> deserializer;
        try {
             deserializer = new DeserializationHandler<>(deserializers_properties);
        }
        catch (IOException e)
        {
            System.err.println("Errore nella deserializzazione");
            e.printStackTrace();
            return;
        }

        System.out.println("Sono stati forniti i deserializzatori per i seguenti formati:");
        Set<String> formatsAvailable = deserializer.getFormats();
        for (String format : formatsAvailable) {
            System.out.println(format);
        }
        System.out.println("Nel caso in cui ci fossero file di formato differente da questi elencati non verranno presi in considerazione");

        // Cerco di deserializzare l'intero folder, con tutti i formati possibili
        List<Article> articles = new ArrayList<>();
        try {
            for (String format : formatsAvailable) {
                deserializer.deserializeFolder(format, folderPath, articles);
            }
        } catch (IOException e) {
            System.err.println(ConsoleTextColors.RED + "Deserializzazione fallita per il formato: " + e.getMessage() + ConsoleTextColors.RESET);
            // se ci sono errori probabilmente è stato inserito un header sbagliato. oppure sono stati specificati male i campi del file JSON
            // bisogna segnalare all'utente e dire di reinserire i campi
            return;
        }

        System.out.println(ConsoleTextColors.BLUE+"Fine deserializzazione..."+ConsoleTextColors.RESET);

        System.out.println(ConsoleTextColors.BLUE+"Inizio serializzazione..."+ConsoleTextColors.RESET);

        // B. SERIALIZZAZIONE Article -> formato comune
        try {

            // Creazione della lista di oggetti Serializable a partire dalla lista di Article (Article implementa Serializable)
            List<Serializable> objects = new ArrayList<>(articles);

            SerializationHandler serializer = new SerializationHandler(serializers_properties);

            serializer.serializeObjects(objects, common_format, filePath);

        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        System.out.println(ConsoleTextColors.BLUE+"Fine serializzazione. Potrete trovare il file serializzato in "+filePath+ConsoleTextColors.RESET);

        // FASE 3: Estrazione termini
        if (interpreter.searchPhase())
        {
            // DESERIALIZZAZIONE formato comune -> articles
            System.out.println(ConsoleTextColors.BLUE+"Inizio deserializzazione..."+ConsoleTextColors.RESET);

            try {
                articles = deserializer.deserializeFile(common_format, filePath);
            }
            catch (IOException e) {
                System.err.println(ConsoleTextColors.RED + "Deserializzazione fallita per il formato: " + e.getMessage() + ConsoleTextColors.RESET);
                // se ci sono errori probabilmente è stato inserito un header sbagliato. oppure sono stati specificati male i campi del file JSON
                // bisogna segnalare all'utente e dire di reinserire i campi
                return;
            }
            System.out.println(ConsoleTextColors.BLUE+"Fine deserializzazione..."+ConsoleTextColors.RESET);

            System.out.println(ConsoleTextColors.BLUE+"Inizio estrazione termini..."+ConsoleTextColors.RESET);

            // ESTRAZIONE DEI TERMINI PIU' IMPORTANTI
            int count_cli = interpreter.obtainNumberOption();
            if (count_cli != -1)
            {
                tot_count = count_cli;
            }

            // Creo un arraylist con tutti i termini come chiavi e numero di articoli in cui sono presenti come valori
            System.out.println(ConsoleTextColors.BLUE + "Scrittura dei primi "+tot_count+" termini più importanti in corso.."+ConsoleTextColors.RESET);

            Analyzer<Article> analyzer = new MapArraySplitAnalyzer(tot_count);
            ArrayList<MapEntrySI> max;

            long start = System.currentTimeMillis();
            max = analyzer.mostPresent(articles);
            long end = System.currentTimeMillis();
            System.out.println(ConsoleTextColors.YELLOW + "Con split: "+(end-start)+ConsoleTextColors.RESET);

            try {
                analyzer.outFile(max, "./database/split.txt");
            }
            catch (IOException e)
            {
                System.err.println("Errore nella scritture del file");
                e.printStackTrace();
                return;
            }

            analyzer = new MapArrayScannerAnalyzer(tot_count);

            start = System.currentTimeMillis();
            max = analyzer.mostPresent(articles);
            end = System.currentTimeMillis();

            System.out.println(ConsoleTextColors.YELLOW + "Con scanner: "+(end-start+ConsoleTextColors.RESET));

            try {
                analyzer.outFile(max, outFile);
            }
            catch (IOException e)
            {
                System.err.println("Errore nella scritture del file");
                e.printStackTrace();
                return;
            }

            System.out.println(ConsoleTextColors.BLUE+"Fine estrazione termini..."+ConsoleTextColors.RESET);
        }
    }

}









