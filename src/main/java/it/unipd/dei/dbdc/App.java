package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.DownloadAPI.DownloadHandler;

import java.io.IOException;

public class App
{
    private final static String database_path = "./database";

    public static void main( String[] args ) throws IOException {
        // TODO: junit
        // TODO: maven site plugin
        // TODO: maven javadoc plugin
        // TODO: bash o .bat

        // L'utente puo' passare da riga di comando quello che vuole fare.
        /*
        CommandLineInterpreter interpreter;
        try {
            interpreter = new CommandLineInterpreter(args);
        }
        catch (HelpException e)
        {
            return;
        }

         */

        //if (interpreter.downloadPhase()) {
            // Se vuole download, passo a download handler
            System.out.println(ConsoleTextColors.BLUE + "Entering the download part..." + ConsoleTextColors.RESET);
            String name = null; //interpreter.obtainDownloadOptions();
            DownloadHandler.download(database_path, name);
            System.out.println(ConsoleTextColors.BLUE + "Exiting the download part..." + ConsoleTextColors.RESET);
        //}
        //if (interpreter.searchPhase())
        //{
            // Serialization e search terms
            //interpreter.obtainSearchOptions();
            //...
        //}

    }

}









