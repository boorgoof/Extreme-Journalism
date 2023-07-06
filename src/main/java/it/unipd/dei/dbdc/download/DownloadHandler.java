package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.tools.PathManager;

import java.io.IOException;
import java.util.Scanner;

public class DownloadHandler {

    /**
     * The main function of the Handler. It selects the API specified by the API properties file passed as
     * a parameter or asks the user interactively to select the API and the parameters.
     * It continues to ask the user if something is not correct, to the point where the responses are correct.
     * It returns the path of the folder where the responses are saved.
     *
     * @param download_props The download properties specified by the user. If null, the default ones will be used.
     * @param api_props The API properties specified by the user. If null or incorrect, the user will be asked to select the API.
     * @throws IOException If the download properties files (the default one and the one specified by the user) are not present or are not correct.
     * @return A {@link String} representing the path where the downloaded files were saved.
     */
    public static String download(String download_props, String api_props) throws IOException {

        //If the IOException was caused by the api properties, the user will be asked to select the correct API.
        APIManager manager = null;
        boolean selected = false;
        try {
            manager = APIProperties.readProperties(api_props, download_props);
            if (manager != null) {
                selected = true;
            }
        }
        catch (IOException | IllegalArgumentException e)
        {
            System.out.println("Selecting the API interactively...");
        }

        String file_path;
        while(true) {

            if (!selected) {
                manager = selectInteractive(download_props);
            }

            System.out.println("API selected correctly...");

            try {
                //Creates the folder to put the responses
                file_path = PathManager.getDatabaseFolder() + manager.getName();
                PathManager.clearFolder(file_path);

                manager.callAPI(file_path);
                System.out.println( "You can find the downloaded files in the format in which they were downloaded in "+file_path);
                return file_path;
            }
            catch (IllegalArgumentException e) {
                System.err.println("Error in the calling of the API: "+e.getMessage());
                System.err.println("Retry");
                selected = false;
            }
        }
    }

    /**
     * The function which interacts with the {@link InteractiveSelectAPI} class, asking the user to enter
     * the name and the parameters of the API and returning the initialized manager.
     * It asks repetitively the user, to the point where the constructed {@link APIManager} is correct.
     *
     * @param download_props The download properties specified by the user. If null, the default ones will be used.
     * @throws IOException If the download properties file specified by the user is incorrect or the default one is missing or incorrect.
     * @return A {@link APIManager} that represents the API to call.
     */
    private static APIManager selectInteractive(String download_props) throws IOException
    {
        //The Scanner is initialized only one time to avoid to close it repetitively
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