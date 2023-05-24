package it.unipd.dei.dbdc.DownloadAPI;

import java.io.IOException;

public class DownloadHandler {
    public static String download() throws IOException {
        // TODO: dovrebbe ricevere folder_path e boolean interactive
        APIContainer container = APIContainer.getInstance();

        APIManager manager = null;

        // Controlla se è stato passato da riga di comando qualcosa
        boolean rigaDiComando = false;
        if (!rigaDiComando)
        {
            manager = InteractiveSelectAPI.askAPI(container);
        }
        else
        {
            // Prende gli oggetti della riga di comando e crea l'oggetto tramite la factory
            // container.getAPIManager();
        }

        // Cerca di chiamare la API
        String folder_path = "./database";
        try {
            manager.callAPI(folder_path);
        }
        catch (IOException e)
        {
            throw new IOException("Errore nella chiamata alla API");
        }
        return folder_path;
    }
}
