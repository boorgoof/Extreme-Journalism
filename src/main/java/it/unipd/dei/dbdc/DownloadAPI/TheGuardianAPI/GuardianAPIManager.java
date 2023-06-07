package it.unipd.dei.dbdc.DownloadAPI.TheGuardianAPI;

import it.unipd.dei.dbdc.ConsoleTextColors;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APICaller;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APIManager;
import it.unipd.dei.dbdc.DownloadAPI.QueryParam;

import java.io.IOException;
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
                params.addParam(q);
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

        // Prende i parametri
        ArrayList<Map<String, Object>> requests = params.getParams();

        long start = System.currentTimeMillis();
        List<Future<Object>> futures = new ArrayList<>();
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // Chiamiamo e mandiamo nella thread pool:
        for (int i = 0; i < requests.size(); i++) {
            String path = path_folder+"/request"+(i+1)+".json";
            CallAPIThread task = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), path, requests.get(i));
            Future<Object> f = threadPool.submit(task);
            futures.add(f);
        }

        // Wait for all sent tasks to complete:
        for (Future<Object> future : futures) {
            try {
                // Get is
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                // Avviene se e' stato interrotto mentre aspettava o ha lanciato un'eccezione
                threadPool.shutdown();
                throw new IOException("Parametri non esatti");
            }
        }

        threadPool.shutdown();
        long end = System.currentTimeMillis();

        System.out.println(ConsoleTextColors.YELLOW+"Con parallelismo future: "+(end-start)+ ConsoleTextColors.RESET);

        caller.endRequests();
    }
}




/*if (!deleteFilesInDir(new File(new_path_folder))) {
            // Se non era presente, lo crea
            Files.createDirectories(Paths.get(new_path_folder));
        }

        // Manda le richieste tramite la libreria e le salva in file
        long start = System.currentTimeMillis();

        Thread[] ts = new Thread[requests.size()];
        for (int i = 0; i<requests.size(); i++)
        {
            String path = new_path_folder+"/request"+(i+1)+".json";
            ts[i] = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), path, requests.get(i));
            ts[i].start();
        }
        for (int i = 0; i<requests.size(); i++)
        {
            try {
                ts[i].join();
            }
            catch (InterruptedException e)
            {
                // Non dovrebbe succedere, è se il thread viene interrotto mentre aspetta
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("Con parallelismo thread: "+(end-start)+ ConsoleTextColors.RESET);

         */