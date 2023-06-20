package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.resources.PathManager;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class  DownloadHandler {

    public static String download(String download_props, String api_props) throws IOException {

        // Inizializza il container con le props, in modo che chi poi lo invoca trovi l'istanza corretta
        APIContainer.getInstance(download_props);

        APIManager manager = null;
        try {
            manager = APIProperties.readAPIProperties(api_props);
        }
        catch (IOException | IllegalArgumentException e)
        {
            System.out.println("Selecting the API interactively...");
        }

        String file_path;

        while(true) {

            if (manager == null) {
                manager = selectInteractive();
            }

            System.out.println("API selected correctly...");

            // Cerca di chiamare la API
            try {
                System.out.println("Calling the API...");

                // Il nuovo folder
                file_path = PathManager.getDatabaseFolder() + manager.getName();

                PathManager.clearFolder(file_path);
                manager.callAPI(file_path);
                break;
            }
            catch (IOException | IllegalArgumentException e) {
                System.err.println("Errore nella chiamata all'API");
                e.printStackTrace();
                // To ask another time for the API interactively
                manager = null;
            }
        }
        System.out.println( "You can find the downloaded files in the format in which they were download in "+file_path);
        return file_path;
    }

    private static APIManager selectInteractive() throws IOException
    {
        try(Scanner in = new Scanner(System.in)) {
            InteractiveSelectAPI interact = new InteractiveSelectAPI(in);
            while (true) {

                String api_name = interact.askAPIName();

                APIManager manager = interact.askParams(api_name);
                if (manager != null) {
                    in.close();
                    return manager;
                }
            }
        }
    }

}