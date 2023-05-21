package it.unipd.dei.dbdc.DownloadAPI;

import java.util.ArrayList;
import java.util.List;

public class APIFactory {
    // This is the Singleton design pattern
    private static APIFactory instance;

    // The adapter to call the API: is only one for every API, as it is only the library to send the requests.
    private APIAdapter adapter;

    // The list of the API that we can call
    private final static ArrayList<APICaller> callers = new ArrayList<>();

    // This is the only way we can access this class.
    public static APIFactory getInstance()
    {
        if (instance == null)
        {
            instance = new APIFactory();
        }
        return instance;
    }
    private APIFactory()
    {
        // TODO: prendile dal file di properties
        adapter = new KongAPIAdapter();
        callers.add(new GuardianAPICaller(adapter));
    }

    // Returns the info of every API caller
    public String getAPIs()
    {
        String s = "";
        for (APICaller a : callers)
        {
            s += a.getInfo() + "\n";
        }
        return s;
    }

    // Returns the possible parameters of the API whose info are in the String info
    public String getAPIPossibleParams(String info) throws IllegalArgumentException
    {
        APICaller a = searchCaller(info);
        return a.possibleParams();
    }

    // Returns an instance of the API caller whose info are in the String info
    public APICaller getAPICaller(String info, List<QueryParam> l) throws IllegalArgumentException
    {
        APICaller a = searchCaller(info);
        a.addParams(l);
        return a;
    }

    // Search for a caller whose info are in the String
    private APICaller searchCaller(String info) throws IllegalArgumentException
    {
        for (APICaller a : callers)
        {
            if (a.getInfo().equals(info))
            {
                return a;
            }
        }
        throw new IllegalArgumentException();
    }
}
