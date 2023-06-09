package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.interfaces.APICaller;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.Callable;

// Utilizziamo callable per poter mandare eccezioni
public class CallAPIThread implements Callable<Object> {
    private final APICaller caller;
    private final String url;
    private final String path;
    private final Map<String, Object> params;
    public CallAPIThread(APICaller c, String u, String p, Map<String, Object> par)
    {
        caller = c;
        url = u;
        path = p;
        params = par;
    }

    @Override
    public Object call() throws IOException {
        // Se qualcosa nel formato era errato, lancia l'errore
        if (!caller.sendRequest(url, params, path)) {
            throw new IllegalArgumentException("Query parameters are not correct");
        }
        // Ogni tanto da IllegalArgument, probably perche' c'è il limite di una richiesta al secondo
        return null;
    }
}
