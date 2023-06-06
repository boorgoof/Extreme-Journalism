package it.unipd.dei.dbdc.DownloadAPI.TheGuardianAPI;

import it.unipd.dei.dbdc.ConsoleTextColors;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APICaller;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APIManager;
import it.unipd.dei.dbdc.DownloadAPI.QueryParam;
//import jdk.nashorn.internal.codegen.CompilerConstants;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
    public String callAPI(String path_folder) throws IllegalArgumentException, IOException
    {
        if (caller == null)
        {
            throw new IllegalArgumentException();
        }

        // Prende i parametri
        ArrayList<Map<String, Object>> requests = params.getParams();

        // Il nuovo folder
        String new_path_folder = path_folder + name;

        /* SENZA PARALLELISMO

        // Elimina il folder, se era gia' presente.
        if (!deleteFilesInDir(new File(new_path_folder))) {
            // Se non era presente, lo crea
            Files.createDirectories(Paths.get(new_path_folder));
        }

        long start = System.currentTimeMillis();
        for (int i = 0; i<requests.size(); i++)
        {
            String path = new_path_folder+"/request"+(i+1)+".json";
            if (!caller.sendRequest(GuardianAPIInfo.getDefaultURL(), requests.get(i), path)) {
                throw new IllegalArgumentException("Query parameters are not correct");
            }
        }
        long end = System.currentTimeMillis();

        System.out.println(ConsoleTextColors.YELLOW + "Senza parallelismo: "+(end-start));

         */

        if (!deleteFilesInDir(new File(new_path_folder))) {
            // Se non era presente, lo crea
            Files.createDirectories(Paths.get(new_path_folder));
        }

        // Manda le richieste tramite la libreria e le salva in file
        long start = System.currentTimeMillis();
        //TODO: vedi se ha senso usare i thread cosi o fare un insieme di thread da chiamare continuamente (sempre gli stessi)

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
                // TODO: vedere come gestire questa eccezione
            }
        }
        long end = System.currentTimeMillis();

        System.out.println("Con parallelismo thread: "+(end-start)+ ConsoleTextColors.RESET);

        // Manda le richieste tramite la libreria e le salva in file. FIXME: in teoria questo serve solo per cose ricorsive
        /*if (!deleteFilesInDir(new File(new_path_folder))) {
            // Se non era presente, lo crea
            Files.createDirectories(Paths.get(new_path_folder));
        }

        start = System.currentTimeMillis();

        ForkJoinPool commonPool = ForkJoinPool.commonPool();
        for (int i = 0; i<requests.size(); i++)
        {
            String path = new_path_folder+"/request"+(i+1)+".json";
            commonPool.invoke(new RecursiveCallAPIAction(caller, GuardianAPIInfo.getDefaultURL(), path, requests.get(i)));
        }
        end = System.currentTimeMillis();

        System.out.println("Con parallelismo fork join: "+(end-start)+ ConsoleTextColors.RESET);

         */

        if (!deleteFilesInDir(new File(new_path_folder))) {
            // Se non era presente, lo crea
            Files.createDirectories(Paths.get(new_path_folder));
        }

        start = System.currentTimeMillis(); // TODO: guarda baeldung

        List<Future> futures = new ArrayList<>();
        System.out.println(Runtime.getRuntime().availableProcessors());
        ExecutorService threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // Send stuff to thread pool:
        for (int i = 0; i < requests.size(); i++) {
            String path = new_path_folder+"/request"+(i+1)+".json";
            CallAPIThread task = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), path, requests.get(i));
            Future f = threadPool.submit(task);
            futures.add(f);
        }

        // Wait for all sent tasks to complete:
        for (Future future : futures) {
            try {
                future.get();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        threadPool.shutdown();
        end = System.currentTimeMillis();

        System.out.println("Con parallelismo future: "+(end-start)+ ConsoleTextColors.RESET);

        caller.endRequests();
        return new_path_folder;
    }

    // TODO: dove metterle?
    private boolean deleteFilesInDir(File dir)
    {
        File[] contents = dir.listFiles();
        if (contents == null) {
            return false;
        }
        for (File f : contents) {
            deleteDir(f);
        }
        return true;
    }

    private void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }

}
