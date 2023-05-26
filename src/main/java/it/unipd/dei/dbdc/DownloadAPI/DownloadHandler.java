package it.unipd.dei.dbdc.DownloadAPI;

import it.unipd.dei.dbdc.ConsoleTextColors;

import java.io.IOException;

public class DownloadHandler {
    public static String download(boolean interactive, String folder_path) throws IOException {
        // TODO: dovrebbe ricevere folder_path e boolean interactive
        APIContainer container = APIContainer.getInstance();

        APIManager manager = null;

        if (interactive)
        {
            manager = InteractiveSelectAPI.askAPI(container);
        }
        else
        {
            // Prende gli oggetti della riga di comando e crea l'oggetto
            System.out.println(ConsoleTextColors.BLUE + "The API was selected from command line..." + ConsoleTextColors.RESET);
            // container.getAPIManager();
        }

        System.out.println(ConsoleTextColors.BLUE + "API selected correctly..." + ConsoleTextColors.RESET);

        // Cerca di chiamare la API
        try {
            System.out.println(ConsoleTextColors.BLUE + "Calling the API..." + ConsoleTextColors.RESET);
            manager.callAPI(folder_path);
        }
        catch (IOException e)
        {
            throw new IOException("Errore nella chiamata alla API");
        }
        return folder_path;
    }
}
