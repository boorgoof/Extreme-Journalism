package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.interfaces.APICaller;
import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.download.QueryParam;
import it.unipd.dei.dbdc.resources.ThreadPool;

import java.util.*;
import java.util.concurrent.*;

/**
 * This is the {@link APIManager} of the theGuardian API. It uses the {@link GuardianAPIInfo} and {@link GuardianAPIParams}
 * as proxies to get information about the API and to contain the specified params.
 *
 * @see GuardianAPIInfo
 * @see GuardianAPIManager
 * @see APIManager
 */
public class GuardianAPIManager implements APIManager {

    /**
     * The {@link APICaller} to pass the requests to. It should be declared during the initialization.
     *
     */
    private final APICaller caller;

    /**
     * The {@link GuardianAPIParams} object that contains all the specified or default parameters
     * for the requests.
     *
     */
    private final GuardianAPIParams params;

    /**
     * The name of the API, as it is presented to the user. It should be declared during initialization.
     *
     */
    private final String name;

    /**
     * The only constructor of the class, it should pass an {@link APICaller} to pass the requests to
     * and the name of the API, as it is presented to the user.
     *
     */
    public GuardianAPIManager(APICaller a, String n) {
        caller = a;
        params = new GuardianAPIParams();
        name = n;
    }

    /**
     * The function which returns the name of the API.
     *
     * @return A {@link String} which contains name of the API.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * The function which returns the possible parameters, formatted as specified in the
     * {@link GuardianAPIInfo} class.
     *
     * @return A {@link String} which contains the formatted parameters.
     */
    @Override
    public String getFormattedParams() {
        return GuardianAPIInfo.getFormattedParams();
    }

    /**
     * The function which permits to add parameters to the request to the API.
     *
     * @param l The list of the {@link QueryParam} parameters to pass to the API.
     * @throws IllegalArgumentException If one of those parameters is not a possible parameter (or is invalid if it's a date) or if the list is null.
     */
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

    /**
     * The function calls the API with all the parameters specified before.
     * It uses the {@link CallAPIThread} to send the requests to the {@link APICaller}
     *
     * @param path_folder The path of the folder where the files of the requests should be saved.
     * @throws IllegalArgumentException If the {@link APICaller} was not initialized, the api-key was not given or there was an exception in the calling of the API.
     */
    @Override
    public void callAPI(String path_folder) throws IllegalArgumentException
    {
        if (caller == null)
        {
            throw new IllegalArgumentException("Caller non inizializzato");
        }

        ArrayList<Map<String, Object>> requests = params.getParams();

        // Create a thread pool and send requests
        List<Future<?>> futures = new ArrayList<>();
        ExecutorService threadPool = ThreadPool.getExecutor();

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