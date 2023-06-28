package it.unipd.dei.dbdc.download.src_api_managers.TestManager;

import it.unipd.dei.dbdc.download.QueryParam;
import it.unipd.dei.dbdc.download.interfaces.APICaller;
import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.CallAPIThread;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIInfo;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIParams;
import it.unipd.dei.dbdc.tools.ThreadPoolTools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

// It's a class useful only for the tests, which is identical to GuardianAPIManager but differs in the name
public class TestManager implements APIManager {

    private final APICaller caller;

    private final GuardianAPIParams params;

    private final String name;

    public TestManager(APICaller a, String n) {
        caller = a;
        params = new GuardianAPIParams();
        name = n;
    }

    public TestManager(TestManager g) {
        caller = g.caller;
        params = g.params;
        name = g.name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getFormattedParams() {
        return GuardianAPIInfo.getFormattedParams();
    }

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
        ExecutorService threadPool = ThreadPoolTools.getExecutor();

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

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof TestManager))
        {
            return false;
        }
        TestManager obj = (TestManager) o;
        return (Objects.equals(caller, obj.caller) && params.equals(obj.params) && Objects.equals(name, obj.name));
    }

    public APIManager copy()
    {
        return new TestManager(this);
    }
}