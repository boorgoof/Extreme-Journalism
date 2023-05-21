package it.unipd.dei.dbdc.DownloadAPI;

import java.util.ArrayList;
import java.util.List;

public class APIFactory {

    private APIAdapter adapter;
    private final static ArrayList<APICaller> callers = new ArrayList<>();
    private static APIFactory instance;
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
        // TODO: capisci come prendere queste info dal sistema
        adapter = new KongAPIAdapter();
        callers.add(new GuardianAPICaller());
    }

    public APICaller getAPICaller(String info, List<QueryParams> l)
    {
        // TODO: qua dovresti fare una scansione della lista e vedere quale prendere in base alle sue info
        return getGuardianCaller(l);
    }

    public String getAPIPossibleParams()
    {
        // TODO: migliora
        return (new GuardianAPICaller()).possibleParams();
    }

    private APICaller getGuardianCaller(List<QueryParams> l)
    {
        GuardianAPICaller c = new GuardianAPICaller(adapter);
        c.addParams(l);
        return c;
    }

    public String getAPIs()
    {
        String s = "";
        for (APICaller a : callers)
        {
            s += a.getInfo() + "\n";
        }
        return s;
    }
}
