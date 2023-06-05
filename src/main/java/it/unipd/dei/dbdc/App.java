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
            ConsoleTextColors.printlnError("Programma terminato perche' non e' stata fornita una azione da compiere.");
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

            ConsoleTextColors.printlnProcess("Entering the download part...");

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
            ConsoleTextColors.printlnProcess("Exiting the download part...");
        }

        // FASE 2: avviene sempre, è la serializzazione in formato comune

        // Percorso del file serializzato. TODO: facciamo in modo che il nome cambi?
        String filePath = database_path + "/Serialized." + common_format;

        // A. DESERIALIZZAZIONE formato fornito -> Article
        String path_cli = interpreter.obtainPathOption();

        if (path_cli == null && folderPath == null) {
            ConsoleTextColors.printlnError("Errore: nessun file da deserializzare");
            return;
        } else if (path_cli != null) {
            folderPath = path_cli;
        }

        ConsoleTextColors.printlnProcess("Inizio deserializzazione di "+folderPath+"...");
        DeserializationHandler<Article> deserializer;
        try {
             deserializer = new DeserializationHandler<>(deserializers_properties);
        }
        catch (IOException e)
        {
            ConsoleTextColors.printlnError("Errore del programma: non sono stati caricati correttamente i deserilizzatori");
            e.printStackTrace();
            return;
        }

        ConsoleTextColors.printlnProcess("Sono stati forniti i deserializzatori per i seguenti formati:");
        Set<String> formatsAvailable = deserializer.getFormats();
        for (String format : formatsAvailable) {
            ConsoleTextColors.printlnInfo(format);
        }
        ConsoleTextColors.printlnInfo("Nel caso in cui ci fossero file di formato differente da questi elencati non verranno presi in considerazione");

        // Cerco di deserializzare l'intero folder, con tutti i formati possibili
        long start = System.currentTimeMillis();
        List<Article> articles = new ArrayList<>();
        try {
            for (String format : formatsAvailable) {
                deserializer.deserializeFolder(format, folderPath, articles);
            }
        } catch (IOException e) {
            ConsoleTextColors.printlnError("Deserializzazione fallita per il formato: " + e.getMessage());
            return;
        }
        long end = System.currentTimeMillis();
        System.out.println(ConsoleTextColors.YELLOW+"Tempo deserializzazione: "+(end-start)+ConsoleTextColors.RESET);


        ConsoleTextColors.printlnProcess("Fine deserializzazione...");


        // B. SERIALIZZAZIONE Article -> formato comune

        ConsoleTextColors.printlnInfo("Inizio serializzazione...");

        try {

            // Creazione della lista di oggetti Serializable a partire dalla lista di Article (Article implementa Serializable)
            List<Serializable> objects = new ArrayList<>(articles);

            SerializationHandler serializer = new SerializationHandler(serializers_properties);

            start = System.currentTimeMillis();
            serializer.serializeObjects(objects, common_format, filePath);
            end = System.currentTimeMillis();
            System.out.println(ConsoleTextColors.YELLOW+"Tempo serializzazione: "+(end-start)+ConsoleTextColors.RESET);


        } catch (IOException e) {
            ConsoleTextColors.printlnInfo("Errore nella serializzazione: ");
            e.printStackTrace();
            return;
        }
        ConsoleTextColors.printlnProcess("Fine serializzazione. Potrete trovare il file serializzato in "+filePath);

        // FASE 3: Estrazione termini
        if (interpreter.searchPhase())
        {
            // DESERIALIZZAZIONE formato comune -> articles
            ConsoleTextColors.printlnProcess("Inizio deserializzazione...");

            try {
                start = System.currentTimeMillis();
                articles = deserializer.deserializeFile(common_format, filePath);
                end = System.currentTimeMillis();
                System.out.println(ConsoleTextColors.YELLOW+"Tempo deserializzazione: "+(end-start)+ConsoleTextColors.RESET);
            }
            catch (IOException e) {
                ConsoleTextColors.printlnError("Deserializzazione fallita per il formato: " + e.getMessage());
                return;
            }
            ConsoleTextColors.printlnProcess("Fine deserializzazione...");

            ConsoleTextColors.printlnProcess("Inizio estrazione termini...");

            // ESTRAZIONE DEI TERMINI PIU' IMPORTANTI
            int count_cli = interpreter.obtainNumberOption();
            if (count_cli != -1)
            {
                tot_count = count_cli;
            }

            // Creo un arraylist con tutti i termini come chiavi e numero di articoli in cui sono presenti come valori
            ConsoleTextColors.printlnProcess("Scrittura dei primi "+tot_count+" termini più importanti in corso..");

            // TODO: properties file
            Analyzer<Article> analyzer = new MapArraySplitAnalyzer(tot_count);
            ArrayList<MapEntrySI> max;

            start = System.currentTimeMillis();
            max = analyzer.mostPresent(articles);
            end = System.currentTimeMillis();
            System.out.println(ConsoleTextColors.YELLOW + "Estrazione termini: "+(end-start)+ConsoleTextColors.RESET);

            try {
                analyzer.outFile(max, outFile);
            }
            catch (IOException e)
            {
                ConsoleTextColors.printlnError("Errore nella scritture del file");
                e.printStackTrace();
                return;
            }

            ConsoleTextColors.printlnProcess("Fine estrazione termini...");
        }
    }

}









