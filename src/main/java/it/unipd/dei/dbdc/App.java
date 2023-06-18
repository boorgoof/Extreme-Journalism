package it.unipd.dei.dbdc;


import it.unipd.dei.dbdc.resources.PathManager;
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
    // DEFAULT VALUES: todo prendili da un altro properties
    private static final String common_format = "xml";
    private static int tot_count = 50;

    public static void main(String[] args) {

        // L'utente deve passare da riga di comando l'azione che vuole fare.
        CommandLineInterpreter interpreter;
        try {
            interpreter = new CommandLineInterpreter(args);
        }
        catch (IllegalStateException e)
        {
            System.out.println("Programma terminato perche' non e' stata fornita una azione da compiere.");
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

            System.out.println("Entering the download part...");

            // The only download option is the path of the properties file for the API to call.
            String apiProps = interpreter.obtainAPIProps();
            String downProps = interpreter.obtainDownProps();
            try {
                folderPath = DownloadHandler.download(downProps, apiProps);
            }
            catch (IOException e)
            {
                System.err.println("Errore nella fase di download: ");
                e.printStackTrace();
                return;
            }

            System.out.println("Exiting the download part...\n");
        }

        // FASE 2: avviene sempre, è la serializzazione in formato comune

        // A. DESERIALIZZAZIONE formato fornito -> Article
        String path_cli = interpreter.obtainPathOption();

        if (path_cli == null && folderPath == null) {
            System.out.println("Errore: nessun file da deserializzare");
            return;
        } else if (path_cli != null) {
            folderPath = path_cli;
        }

        System.out.println("\nInizio deserializzazione di "+folderPath+"...");
        DeserializationHandler deserializersHandler;
        try {
             deserializersHandler = new DeserializationHandler(interpreter.obtainDeserProps());
        }
        catch (IOException e)
        {
            System.err.println("Errore del programma: non sono stati caricati correttamente i deserializzatori del file");
            e.printStackTrace();
            return;
        }
        List<UnitOfSearch> articles = deserializersHandler.deserializeALLFormatsFolder(folderPath);
        System.out.println("Fine deserializzazione...\n");

        // B. SERIALIZZAZIONE Article -> formato comune

        // Percorso del file serializzato.
        String filePath;
        try {
            filePath = PathManager.getSerializedFile(common_format);
        }
        catch (IOException e)
        {
            System.out.println("Non si riesce a creare il folder di output");
            return;
        }

        System.out.println("\nInizio serializzazione...");
        try {

            // Creazione della lista di oggetti UnitOfSearch a partire dalla lista di Article (Article implementa UnitOfSearch)
            List<UnitOfSearch> objects = new ArrayList<>(articles);

            SerializationHandler serializersHandler = new SerializationHandler(interpreter.obtainSerProps());

            serializersHandler.serializeObjects(objects, common_format, filePath);

        } catch (IOException e) {
            System.out.println("Errore nella serializzazione: ");
            e.printStackTrace();
            return;
        }
        System.out.println("Fine serializzazione. Potrete trovare il file serializzato in "+filePath+"\n");

        // FASE 3: Estrazione termini
        if (interpreter.searchPhase())
        {
            // DESERIALIZZAZIONE formato comune -> articles
            System.out.println("\nInizio deserializzazione...");

            try {
                articles = deserializersHandler.deserializeFile(common_format, filePath);
            }
            catch (IOException e) {
                System.out.println("Deserializzazione fallita per il formato: " + e.getMessage());
                return;
            }
            System.out.println("Fine deserializzazione...\n");

            System.out.println("\nInizio estrazione termini...");

            // ESTRAZIONE DEI TERMINI PIU' IMPORTANTI
            int count_cli = interpreter.obtainNumberOption();
            if (count_cli != -1)
            {
                tot_count = count_cli;
            }

            String analyzeProps = interpreter.obtainAnalyzeProps();
            try {
                AnalyzerHandler.analyze(analyzeProps, articles, tot_count);
            } catch (IOException e) {
                System.out.println("Errore nell'apertura del file di properties di analyze");
                e.printStackTrace();
                return;
            }

            System.out.println("Fine estrazione termini...\n");
        }
    }

}









