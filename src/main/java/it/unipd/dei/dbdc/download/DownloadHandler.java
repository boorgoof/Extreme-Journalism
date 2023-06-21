package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.resources.PathManager;

import java.io.IOException;
import java.util.Scanner;

public class  DownloadHandler {

    //Lancia IOException se le properties di download non sono corrette
    public static String download(String download_props, String api_props) throws IOException {

        APIManager manager = null;
        boolean selected = false;
        try {
            manager = APIProperties.readAPIProperties(api_props, download_props);
            selected = true;
        }
        catch (IOException | IllegalArgumentException e)
        {
            //If the IOException was caused by the download properties, the exception will be launched in the following cycle
            System.out.println("Selecting the API interactively...");
        }

        String file_path;
        while(true) {

            if (!selected) {
                manager = selectInteractive(download_props);
            }

            System.out.println("API selected correctly...");

            try {
                //Create the folder to put the responses
                file_path = PathManager.getDatabaseFolder() + manager.getName();
                PathManager.clearFolder(file_path);

                manager.callAPI(file_path);
                System.out.println( "You can find the downloaded files in the format in which they were downloaded in "+file_path);
                return file_path;
            }
            catch (IllegalArgumentException e) {
                System.err.println("Error in the calling of the API: "+e.getMessage());
                System.err.println("Retry");
                // To ask another time for the API interactively
                selected = false;
            }
        }
    }

    //Lancia IOException se non c'è download_props di default
    private static APIManager selectInteractive(String download_props) throws IOException
    {
        try(Scanner in = new Scanner(System.in)) {
            InteractiveSelectAPI interact = new InteractiveSelectAPI(in, download_props);
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