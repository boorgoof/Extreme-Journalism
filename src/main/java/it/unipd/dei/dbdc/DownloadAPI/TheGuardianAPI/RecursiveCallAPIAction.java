package it.unipd.dei.dbdc.DownloadAPI.TheGuardianAPI;

import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APICaller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.RecursiveAction;

public class RecursiveCallAPIAction extends RecursiveAction {

    private final APICaller caller;
    private final String url;
    private final String path;
    private final Map<String, Object> params;
    public RecursiveCallAPIAction(APICaller c, String u, String p, Map<String, Object> par)
    {
        caller = c;
        url = u;
        path = p;
        params = par;
    }
    @Override
    protected void compute() {
        // Se qualcosa nel formato era errato, lancia l'errore
        try {
            if (!caller.sendRequest(url, params, path)) {
                throw new IllegalArgumentException("Query parameters are not correct");
            }
            // Ogni tanto da IllegalArgument, probably perche' c'è il limite di una richiesta al secondo
        }
        catch(IOException e)
        {
            throw new IllegalArgumentException("Error in the call of the API");
        }
    }
}
