package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.interfaces.APICaller;

import java.util.Map;

public class CallAPIThread implements Runnable {
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
    public void run() throws IllegalArgumentException {
        try
        {
            if (!caller.sendRequest(url, params, path)) {
                throw new IllegalArgumentException("The request made is not correct");
            }
        }
        catch(Exception e) //This is required as we don't know what are the exceptions that the library could throw
        {
            throw new IllegalArgumentException("The request made is not correct: "+e.getMessage());
        }
    }
}
