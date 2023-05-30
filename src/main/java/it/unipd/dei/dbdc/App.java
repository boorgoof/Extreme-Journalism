package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.DESERIALIZERS_FILE_PROPERTIES.Article;
import it.unipd.dei.dbdc.DESERIALIZERS_FILE_PROPERTIES.DeserializationHandler;
import it.unipd.dei.dbdc.DownloadAPI.DownloadHandler;

import java.io.IOException;
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
        DeserializationHandler<Article> handler = new DeserializationHandler<>(filePropertiesName);

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

    }

}









