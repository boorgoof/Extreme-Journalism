package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.interfaces.APICaller;
import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.download.QueryParam;
import it.unipd.dei.dbdc.resources.ThreadPool;

import java.util.*;
import java.util.concurrent.*;

// Questo caller ha le informazioni per ogni chiamata fatta al TheGuardian.
public class GuardianAPIManager implements APIManager {

    // The library to call the API
    private final APICaller caller;

    // Utilizza il meccanismo della delega
    private final GuardianAPIParams params;

    private final String name;

    // To create this object, you have to pass a Caller to it
    public GuardianAPIManager(APICaller a, String n) {
        caller = a;
        params = new GuardianAPIParams();
        name = n;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFormattedParams() {
        return GuardianAPIInfo.getFormattedParams();
    }

    // To add parameters
    @Override
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
                params.addParam(q);
            }
            else
            {
                throw new IllegalArgumentException("The key of the parameter "+q.getKey()+" is not present in the system");
            }
        }
    }

    // This calls the API
    @Override
    public void callAPI(String path_folder) throws IllegalArgumentException
    {
        if (caller == null)
        {
            throw new IllegalArgumentException("Caller non inizializzato");
        }

        // Prende i parametri
        ArrayList<Map<String, Object>> requests = params.getParams();

        List<Future<?>> futures = new ArrayList<>();
        ExecutorService threadPool = ThreadPool.getExecutor();

        // Chiamiamo e mandiamo nella thread pool:
        for (int i = 0; i < requests.size(); i++) {
            String path = path_folder+"/request"+(i+1)+".json";
            Future<?> f = threadPool.submit(new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), path, requests.get(i)));
            futures.add(f);
        }

        // Wait for all sent tasks to complete:
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                threadPool.shutdown();
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        threadPool.shutdown();
        caller.endRequests();
    }
}