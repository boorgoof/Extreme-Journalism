package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.DownloadAPI.DownloadHandler;
import it.unipd.dei.dbdc.DownloadAPI.QueryParam;
import org.apache.commons.cli.CommandLine;

import java.io.IOException;
import java.util.ArrayList;

public class App
{
    private final static String database_path = "./database";

    public static void main( String[] args ) throws IOException {
        // TODO: junit
        // TODO: maven site plugin
        // TODO: maven javadoc plugin
        // TODO: bash o .bat

        // L'utente puo' passare da riga di comando quello che vuole fare.
        CommandLine cmd = CommandLineInterpreter.parseCommandLine(args);
        if (cmd == null)
        {
            return;
        }
        boolean download = false;
        boolean search = false;

        if (cmd.hasOption("d"))
        {
            download = true;
        }
        else if (cmd.hasOption("s"))
        {
            search = true;
        }
        else if (cmd.hasOption("ds"))
        {
            download = true;
            search = true;
        }

        if (download) {
            // Se vuole download, passo a download handler
            System.out.println(ConsoleTextColors.BLUE + "Entering the download part..." + ConsoleTextColors.RESET);
            String name = CommandLineInterpreter.obtainDownloadOptions(cmd);
            DownloadHandler.download(database_path, name);
            System.out.println(ConsoleTextColors.BLUE + "Exiting the download part..." + ConsoleTextColors.RESET);
        }
        if (search)
        {
            // Serialization e search terms
            //obtainSearchOptions(cmd);
        }

    }

}









