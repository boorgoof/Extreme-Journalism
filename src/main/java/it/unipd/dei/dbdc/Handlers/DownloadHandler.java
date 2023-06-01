package it.unipd.dei.dbdc.Handlers;

import it.unipd.dei.dbdc.ConsoleTextColors;
import it.unipd.dei.dbdc.DownloadAPI.APIContainer;
import it.unipd.dei.dbdc.DownloadAPI.APIProperties;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APIManager;
import it.unipd.dei.dbdc.DownloadAPI.InteractiveSelectAPI;

import java.io.IOException;
import java.util.Scanner;

public class DownloadHandler {
    public static String download(String folder_path, String download_props, String api_props) throws IOException {

        APIContainer container = APIContainer.getInstance(download_props);

        APIManager manager = null;
        if (api_props != null) {
            try {
                manager = APIProperties.readAPIProperties(api_props, container);
            }
            catch (IOException | IllegalArgumentException e)
            {
                System.out.println(ConsoleTextColors.BLUE + "Selecting the API interactively..."+ConsoleTextColors.RESET);
            }
        }
        boolean ok = false;
        String file_path = "";
        while(!ok) {
            if (manager == null) {
                manager = selectInteractive(container);
            }

            System.out.println(ConsoleTextColors.BLUE + "API selected correctly..." + ConsoleTextColors.RESET);

            // Cerca di chiamare la API
            try {
                System.out.println(ConsoleTextColors.BLUE + "Calling the API..." + ConsoleTextColors.RESET);
                file_path = manager.callAPI(folder_path);
                ok = true;
            } catch (IOException | IllegalArgumentException e) {
                System.out.println(ConsoleTextColors.RED + "Errore nella chiamata all'API" + ConsoleTextColors.RESET);
                e.printStackTrace();
                manager = null;
            }
        }
        System.out.println(ConsoleTextColors.BLUE + "You can find the downloaded files in "+file_path+ ConsoleTextColors.RESET);
        return file_path;
    }

    private static APIManager selectInteractive(APIContainer container)
    {
        Scanner in = new Scanner(System.in);
        while (true) {

            String api_name = InteractiveSelectAPI.askAPIName(in, container);

            APIManager manager = InteractiveSelectAPI.askParams(in, container, api_name);
            if (manager != null)
            {
                return manager;
            }
        }
    }
}
