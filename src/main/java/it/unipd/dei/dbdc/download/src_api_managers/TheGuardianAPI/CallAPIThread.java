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
    public Object call() throws IllegalArgumentException {
        try
        {
            if (!caller.sendRequest(url, params, path)) {
                throw new IllegalArgumentException("The request made is not correct");
            }
        }
        catch(Exception e)
        {
            throw new IllegalArgumentException("The request made is not correct: "+e.getMessage());
        }
        return null;
    }
}
