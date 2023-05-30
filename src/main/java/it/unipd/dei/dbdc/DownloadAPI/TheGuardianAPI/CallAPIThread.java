package it.unipd.dei.dbdc.DownloadAPI.TheGuardianAPI;

import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APICaller;

import java.io.IOException;
import java.util.Map;

public class CallAPIThread extends Thread {
    private APICaller caller;

    private String url;
    private String path;
    private Map<String, Object> params;
    public CallAPIThread(APICaller c, String u, String p, Map<String, Object> par)
    {
        caller = c;
        url = u;
        path = p;
        params = par;
    }
    public void run()
    {
        // Se qualcosa nel formato era errato, lancia l'errore
        try {
            if (!caller.sendRequest(url, params, path)) {
                throw new IllegalArgumentException("Query parameters are not correct");
            }
            // Ogni tanto da IllaegalArgument, probably perche' c'è il limite di una richiesta al secondo
        }
        catch(IOException e)
        {
            throw new IllegalArgumentException("Error in the call of the API");
        }
    }
}
