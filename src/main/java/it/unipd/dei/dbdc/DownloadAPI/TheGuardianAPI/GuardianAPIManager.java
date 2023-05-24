package it.unipd.dei.dbdc.DownloadAPI.TheGuardianAPI;

import it.unipd.dei.dbdc.DownloadAPI.Libraries.APICaller;
import it.unipd.dei.dbdc.DownloadAPI.APIManager;
import it.unipd.dei.dbdc.DownloadAPI.QueryParam;

import java.io.IOException;
import java.util.List;

// Questo caller ha le informazioni per ogni chiamata fatta al TheGuardian.
public class GuardianAPIManager implements APIManager {

    // The library to call the API
    private final APICaller caller;

    // Utilizza il meccanismo della delega
    private final GuardianAPIRequests requests;

    // To create this object, you have to pass a Caller to it
    public GuardianAPIManager(APICaller a) {
        caller = a;
        requests = new GuardianAPIRequests();
    }

    @Override
    public String getAPIName() {
        return GuardianAPIInfo.getAPIName();
    }

    @Override
    public String getParams() {
        return GuardianAPIInfo.getParams();
    }

    // To add parameters
    public void addParams(List<QueryParam> l) throws IllegalArgumentException
    {
        if (l == null)
        {
            throw new IllegalArgumentException("The list of parameters is null");
        }
        for (QueryParam q : l)
        {
            if (GuardianAPIInfo.isPresent(q.getKey()))
            {
                requests.addParam(q);
            }
            else
            {
                throw new IllegalArgumentException("The key of the parameter "+q.getKey()+" is not present in the system");
            }
        }
    }

    // This calls the API
    public void callAPI(String path_folder) throws IllegalArgumentException, IOException
    {
        if (caller == null)
        {
            throw new IllegalArgumentException();
        }

        // Crea le richieste
        String[] req = requests.createRequests();

        // Manda le richieste tramite la libreria e le salva in file
        for (int i = 0; i<req.length; i++)
        {
            String path = path_folder +"/"+GuardianAPIInfo.getAPIName()+"/request"+(i+1)+".json";

            // Se qualcosa nel formato era errato, lancia l'errore
            if (!caller.sendRequest(req[i], path))
            {
                throw new IllegalArgumentException("Query parameters are not correct");
            }
        }
        caller.endRequests();
    }

}
