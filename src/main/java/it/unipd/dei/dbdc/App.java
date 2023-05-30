package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.Deserializers.Article;
import it.unipd.dei.dbdc.Deserializers.DeserializationHandler;
import it.unipd.dei.dbdc.DownloadAPI.DownloadHandler;
import it.unipd.dei.dbdc.Search_terms.Analyze;
import it.unipd.dei.dbdc.Serializers.SerializationHandler;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class App
{
    private final static String database_path = "./database";
    public static final String filePropertiesName = "deserializers.properties";


    public static void main( String[] args ) throws IOException {
        // TODO: junit
        // TODO: maven site plugin
        // TODO: maven javadoc plugin
        // TODO: bash o .bat

        // L'utente puo' passare da riga di comando quello che vuole fare.
        CommandLineInterpreter interpreter;
        try {
            interpreter = new CommandLineInterpreter(args);
        }
        catch (HelpException e)
        {
            return;
        }

        //if (interpreter.downloadPhase()) {
            // Se vuole download, passo a download handler
            System.out.println(ConsoleTextColors.BLUE + "Entering the download part..." + ConsoleTextColors.RESET);
            String name = null; //interpreter.obtainDownloadOptions();
            String folderPath = DownloadHandler.download(database_path, name);
            System.out.println(ConsoleTextColors.BLUE + "Exiting the download part..." + ConsoleTextColors.RESET);
        //}
        //if (interpreter.searchPhase())
        //{
            // Serialization e search terms
            //interpreter.obtainSearchOptions();
            //...
        //}

        // DESERIALIZZAZIONE
        DeserializationHandler<it.unipd.dei.dbdc.Deserializers.Article> handler = new DeserializationHandler<>(filePropertiesName);

        System.out.println("Sono stati forniti i deserializzatori per i seguenti formati:");
        Set<String> formatsAvailable = handler.getFormats();
        for (String format : formatsAvailable) {
            System.out.println(format);
        }

        System.out.println("Nel caso in cui ci fossero file di formato differente da questi elencati non verranno presi in considerazione");

        List<Article> articles = new ArrayList<>();
        try {

            for (String format : formatsAvailable) {
                handler.deserializeFolder(format, folderPath, articles);
            }

        } catch (IOException e) {
            System.err.println("Deserializzazione fallita per il formato: " + e.getMessage());
            // se ci sono errori probabilmente è stato inserito un header sbagliato. oppure sono stati specificati male i campi del file JSON
            // bisogna segnalare all'utente e dire di reinserire i campi
        }

        //SERIALIZZAZIONE IN XML
        try {

            // Creazione della lista di oggetti Serializable a partire dalla lista di Article (Article implementa Serializzable)
            List<Serializable> objects = new ArrayList<>(articles);

            String format = "xml";
            String filename = "Serialized" ; // Percorso del file di output
            String filePath = database_path +"/"+ filename + "." + format;

            SerializationHandler handler2 = new SerializationHandler("serializers.properties");

            handler2.serializeObjects(objects, format, filePath);

        } catch (IOException e) {
            e.printStackTrace();
        }

        // DESERIALIZZAZIONE DA XML
        List<it.unipd.dei.dbdc.Deserializers.Article> articles2 = new ArrayList<>();
        try {

            for (String format : formatsAvailable) {
                articles2 = handler.deserializeFile("xml", database_path+"/Serialized.xml");
            }

        } catch (IOException e) {
            System.err.println("Deserializzazione fallita per il formato: " + e.getMessage());
            // se ci sono errori probabilmente è stato inserito un header sbagliato. oppure sono stati specificati male i campi del file JSON
            // bisogna segnalare all'utente e dire di reinserire i campi
        }

        System.out.println(ConsoleTextColors.GREEN+ "Tutto beneee");

        // MAPPA
        //creo un arraylist con tutti i termini come chiavi e numero di articoli in cui sono presenti come valori
        System.out.println("scrittura primi 50 termini più presenti in corso..");
        ArrayList<it.unipd.dei.dbdc.Search_terms.MapEntry> max = Analyze.mostPresent(articles);

        String outFilePath = "./database/output.txt";
        //stampo i primi 50 termini più presenti nei vari articoli
        Analyze.outFile(max, outFilePath);

    }

}









