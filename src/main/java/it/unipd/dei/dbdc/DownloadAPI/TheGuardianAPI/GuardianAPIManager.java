package it.unipd.dei.dbdc.DownloadAPI.TheGuardianAPI;

import it.unipd.dei.dbdc.ConsoleTextColors;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APICaller;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APIManager;
import it.unipd.dei.dbdc.DownloadAPI.QueryParam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// Questo caller ha le informazioni per ogni chiamata fatta al TheGuardian.
public class GuardianAPIManager implements APIManager {

    // The library to call the API
    private final APICaller caller;

    // Utilizza il meccanismo della delega
    private final GuardianAPIParams params;

    // To create this object, you have to pass a Caller to it
    public GuardianAPIManager(APICaller a) {
        caller = a;
        params = new GuardianAPIParams();
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
        String new_path_folder = path_folder + GuardianAPIInfo.getAPIName();

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
        // start = System.currentTimeMillis();

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
        // long end = System.currentTimeMillis();

        // System.out.println("Con parallelismo: "+(end-start)+ ConsoleTextColors.RESET);

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
