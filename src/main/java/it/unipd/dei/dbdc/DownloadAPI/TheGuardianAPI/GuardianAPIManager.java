package it.unipd.dei.dbdc.DownloadAPI.TheGuardianAPI;

import it.unipd.dei.dbdc.DownloadAPI.Libraries.APICaller;
import it.unipd.dei.dbdc.DownloadAPI.APIManager;
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
    public void callAPI(String path_folder) throws IllegalArgumentException, IOException
    {
        if (caller == null)
        {
            throw new IllegalArgumentException();
        }

        // Prende i parametri
        ArrayList<Map<String, Object>> requests = params.getParams();

        // Il nuovo folder
        path_folder = path_folder +"/"+GuardianAPIInfo.getAPIName();

        // Elimina il folder, se era gia' presente.
        if (deleteFilesInDir(new File(path_folder))) {
            // Se non era presente, lo crea
            Files.createDirectories(Paths.get(path_folder));
        }

        // Manda le richieste tramite la libreria e le salva in file
        for (int i = 0; i<requests.size(); i++)
        {
            String path = path_folder+"/request"+(i+1)+".json";

            // Se qualcosa nel formato era errato, lancia l'errore
            if (!caller.sendRequest(GuardianAPIInfo.getDefaultURL(), requests.get(i), path))
            {
                throw new IllegalArgumentException("Query parameters are not correct");
            }
        }
        caller.endRequests();
    }

    private boolean deleteFilesInDir(File dir)
    {
        File[] contents = dir.listFiles();
        if (contents == null) {
            return true;
        }
        for (File f : contents) {
            deleteDir(f);
        }
        return false;
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
