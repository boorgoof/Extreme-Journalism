package it.unipd.dei.dbdc.DownloadAPI;

import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APIManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class APIContainer {
    // The list of the API that we can call
    private static ArrayList<APIManager> managers = new ArrayList<>();

    // We use the singleton design pattern to read the properties file only one time
    private static APIContainer instance;
    public static APIContainer getInstance(String download_properties) throws IOException
    {
        if (instance == null)
        {
            instance = new APIContainer(download_properties);
        }
        return instance;
    }

    public static APIContainer getInstance() throws IOException
    {
        if (instance == null)
        {
            throw new IOException("Download properties file not present");
        }
        return instance;
    }

    private APIContainer(String download_properties) throws IOException
    {
        managers = DownloadProperties.readProperties(download_properties);
    }

    // Returns the info of every API caller
    public String getAPINames()
    {
        StringBuilder s = new StringBuilder();
        for (APIManager a : managers)
        {
            s.append(a.getAPIName()).append("\n");
        }
        return s.toString();
    }

    // Returns the possible parameters of the API whose info are in the String info
    public String getAPIPossibleParams(String name) throws IllegalArgumentException
    {
        APIManager a = searchCaller(name);
        return a.getParams();
    }

    // Returns an instance of the API caller whose info are in the String info
    public APIManager getAPIManager(String name, List<QueryParam> l) throws IllegalArgumentException
    {
        APIManager a = searchCaller(name);
        a.addParams(l);
        return a;
    }

    // Search for a caller whose name is in the String
    private APIManager searchCaller(String name) throws IllegalArgumentException
    {
        for (APIManager a : managers)
        {
            if (a.getAPIName().equals(name))
            {
                return a;
            }
        }
        throw new IllegalArgumentException("The API name is not in the list of the available APIs");
    }
}
