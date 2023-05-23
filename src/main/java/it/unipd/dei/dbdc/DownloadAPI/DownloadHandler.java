package it.unipd.dei.dbdc.DownloadAPI;

import java.io.IOException;

// L'unica classe che ha il main
public class DownloadHandler {
    public static String download() throws IOException {

        // Crea una Factory che crea tutte le istanze delle APIManager.
        APIProperties factory = new APIProperties(); // Lancia IOException

        APIManager manager = null;
        // Controlla se è stato passato da riga di comando qualcosa
        boolean rigaDiComando = false;
        if (!rigaDiComando)
        {
            manager = InteractiveSelectAPI.askAPI(factory);
        }
        else
        {
            // Prende gli oggetti della riga di comando e crea l'oggetto tramite la factory
            // factory.getAPICaller();
        }

        // Cerca di chiamare la API
        String path_folder = "./database";
        try {
            manager.callAPI(path_folder);
        }
        catch (IOException e)
        {
            throw new IOException("Errore nella chiamata alla API");
        }
        return path_folder;
    }
}
