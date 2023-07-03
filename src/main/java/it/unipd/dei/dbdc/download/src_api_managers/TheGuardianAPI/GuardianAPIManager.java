package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.interfaces.APICaller;
import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.download.QueryParam;
import it.unipd.dei.dbdc.tools.ThreadPool;

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
     * The constructor of the class that requires an {@link APICaller} to pass the requests to
     * and the name of the API, as it is presented to the user.
     *
     * @param a The {@link APICaller} to pass the request to.
     * @param n The name of the API as it is presented to the user.
     */
    public GuardianAPIManager(APICaller a, String n) {
        caller = a;
        params = new GuardianAPIParams();
        name = n;
    }

    /**
     * The copy constructor of the class, which requires another {@link GuardianAPIManager}
     * as a parameter.
     *
     * @param g The {@link GuardianAPIManager} to copy from.
     */
    public GuardianAPIManager(GuardianAPIManager g) {
        caller = g.caller;
        params = new GuardianAPIParams(g.params);
        name = g.name;
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
     * @throws IllegalArgumentException If one of those parameters is not a possible parameter (or is invalid if it's a date or pages or page-size) or if the list is null.
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
            if (q != null && GuardianAPIInfo.isPresent(q.getKey()))
            {
                params.addParam(q);
            }
            else
            {
                throw new IllegalArgumentException("The key of the parameter is not present in the system, or the parameters is null");
            }
        }
    }

    /**
     * The function calls the API with all the parameters specified before. If there is any not correct parameter, it will be ignored, or an exception will be thrown.
     * It uses the {@link CallAPIThread} to send the requests to the {@link APICaller}.
     *
     * @param path_folder The path of the folder where the files of the requests should be saved.
     * @throws IllegalArgumentException If the {@link APICaller} was not initialized, the api-key was not given or there was an exception in the calling of the API.
     */
    @Override
    public void callAPI(String path_folder) throws IllegalArgumentException
    {
        if (caller == null)
        {
            throw new IllegalArgumentException("Caller not initialized");
        }

        ArrayList<Map<String, Object>> requests = params.getParams();

        // Create a thread pool and send requests
        List<Future<?>> futures = new ArrayList<>();

        for (int i = 0; i < requests.size(); i++) {
            String path = path_folder+"/request"+(i+1)+".json";
            Future<?> f = ThreadPool.submit(new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), path, requests.get(i)));
            futures.add(f);
        }

        // Wait for all sent tasks to complete:
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                ThreadPool.shutdown();
                throw new IllegalArgumentException(e.getMessage());
            }
        }

        ThreadPool.shutdown();
        caller.endRequests();
    }

    /**
     * The function copies this object and gives another object that is identical to it.
     * It is a stub to the copy constructor.
     *
     */
    @Override
    public APIManager copy()
    {
        return new GuardianAPIManager(this);
    }

    /**
     * This function overrides the function of Object. It returns true if the Object passed
     * is of the same class and has the same parameters of this Object.
     *
     * @param o The Object to compare to
     * @return True if the two objects are equals
     */
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof GuardianAPIManager))
        {
            return false;
        }
        GuardianAPIManager obj = (GuardianAPIManager) o;
        return (Objects.equals(caller, obj.caller) && params.equals(obj.params) && Objects.equals(name, obj.name));
    }
}