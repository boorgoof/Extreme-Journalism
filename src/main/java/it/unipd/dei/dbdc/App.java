package it.unipd.dei.dbdc;


import it.unipd.dei.dbdc.search.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.search.AnalyzerHandler;
import it.unipd.dei.dbdc.deserialization.DeserializationHandler;
import it.unipd.dei.dbdc.download.DownloadHandler;
import it.unipd.dei.dbdc.serializers.SerializationHandler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class App
{
    // TODO: prendi tutto da properties, e fai in modo che ti possano passare un properties diverso da CLI. Oppure metti le properties fuori da src
    private static final String database_path = "./database/";
    private static final String deserializers_properties = "deserializers.properties";
    private static final String serializers_properties = "serializers.properties";
    private static final String download_properties = "download.properties";
    private static final String analyze_properties = "analyze.properties";
    private static final String common_format = "xml";
    private static final String outFile = "./database/output.txt";
    private static int tot_count = 50;

    // TODO: crea di default una pool di threads che viene utilizzata per tutte le cose

    public static void main(String[] args) {

        // L'utente deve passare da riga di comando l'azione che vuole fare.
        CommandLineInterpreter interpreter;
        try {
            interpreter = new CommandLineInterpreter(args);
        }
        catch (IllegalStateException e)
        {
            Console.printlnError("Programma terminato perche' non e' stata fornita una azione da compiere.");
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

            Console.printlnProcessInfo("Entering the download part...");

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
            Console.printlnProcessInfo("Exiting the download part...");
        }

        // FASE 2: avviene sempre, è la serializzazione in formato comune

        // Percorso del file serializzato. TODO: facciamo in modo che il nome cambi?
        String filePath = database_path + "/Serialized." + common_format;

        // A. DESERIALIZZAZIONE formato fornito -> Article
        String path_cli = interpreter.obtainPathOption();

        if (path_cli == null && folderPath == null) {
            Console.printlnError("Errore: nessun file da deserializzare");
            return;
        } else if (path_cli != null) {
            folderPath = path_cli;
        }

        Console.printlnProcessInfo("Inizio deserializzazione di "+folderPath+"...");
        DeserializationHandler deserializersHandler;
        try {
             deserializersHandler = new DeserializationHandler(deserializers_properties);
        }
        catch (IOException e)
        {
            Console.printlnError("Errore del programma: non sono stati caricati correttamente i deserializzatori del file "+deserializers_properties);
            e.printStackTrace();
            return;
        }
        List<UnitOfSearch> articles = deserializersHandler.deserializeALLFormatsFolder(folderPath);
        Console.printlnProcessInfo("Fine deserializzazione...");

        // B. SERIALIZZAZIONE Article -> formato comune
        Console.printlnInteractiveInfo("Inizio serializzazione...");
        try {

            // Creazione della lista di oggetti UnitOfSearch a partire dalla lista di Article (Article implementa UnitOfSearch)
            List<UnitOfSearch> objects = new ArrayList<>(articles);

            SerializationHandler serializersHandler = new SerializationHandler(serializers_properties);

            long start = System.currentTimeMillis();
            serializersHandler.serializeObjects(objects, common_format, filePath);
            long end = System.currentTimeMillis();

            System.out.println(Console.YELLOW+"Tempo serializzazione: "+(end-start)+ Console.RESET);

        } catch (IOException e) {
            Console.printlnError("Errore nella serializzazione: ");
            e.printStackTrace();
            return;
        }
        Console.printlnProcessInfo("Fine serializzazione. Potrete trovare il file serializzato in "+filePath);

        // FASE 3: Estrazione termini
        if (interpreter.searchPhase())
        {
            // DESERIALIZZAZIONE formato comune -> articles
            Console.printlnProcessInfo("Inizio deserializzazione...");

            try {
                articles = deserializersHandler.deserializeFile(common_format, filePath);
            }
            catch (IOException e) {
                Console.printlnError("Deserializzazione fallita per il formato: " + e.getMessage());
                return;
            }
            Console.printlnProcessInfo("Fine deserializzazione...");

            Console.printlnProcessInfo("Inizio estrazione termini...");

            // ESTRAZIONE DEI TERMINI PIU' IMPORTANTI
            int count_cli = interpreter.obtainNumberOption();
            if (count_cli != -1)
            {
                tot_count = count_cli;
            }

            Console.printlnProcessInfo("Scrittura dei primi "+tot_count+" termini più importanti in corso..");
            try {
                AnalyzerHandler analyzerHandler = new AnalyzerHandler(analyze_properties);
                analyzerHandler.analyze(articles, outFile, tot_count);
            } catch (IOException e) {
                Console.printlnError("Errore nell'apertura del file di properties di analyze");
                e.printStackTrace();
                return;
            }

            Console.printlnProcessInfo("Fine estrazione termini...");
        }
    }

}









