package it.unipd.dei.dbdc.DownloadAPI;

import it.unipd.dei.dbdc.ConsoleTextColors;

import java.io.IOException;
import java.util.Scanner;

public class DownloadHandler {
    public static String download(String folder_path, String api_name) throws IOException {

        APIContainer container = APIContainer.getInstance();
        APIManager manager;
        Scanner in = new Scanner(System.in);
        while (true) {
            if (api_name == null) {
                api_name = InteractiveSelectAPI.askAPIName(in, container);
            }

            manager = InteractiveSelectAPI.askParams(in, container, api_name);
            if (manager == null)
            {
                // Facciamo in modo che richieda il nome
                api_name = null;
                continue;
            }

            System.out.println(ConsoleTextColors.BLUE + "API selected correctly..." + ConsoleTextColors.RESET);

            String file_path;
            // Cerca di chiamare la API
            try {
                System.out.println(ConsoleTextColors.BLUE + "Calling the API..." + ConsoleTextColors.RESET);
                file_path = manager.callAPI(folder_path);
            }
            catch (IOException | IllegalArgumentException e)
            {
                System.out.println(ConsoleTextColors.RED + "Errore nella chiamata all'API"+ConsoleTextColors.RESET);
                e.printStackTrace();
                api_name = null;
                continue;
            }
            in.close();
            System.out.println(ConsoleTextColors.BLUE + "You can find the downloaded json in "+folder_path+ ConsoleTextColors.RESET);
            return file_path;
        }


    }
}
