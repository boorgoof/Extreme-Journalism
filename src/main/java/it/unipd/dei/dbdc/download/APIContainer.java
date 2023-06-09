package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;

import java.io.IOException;
import java.util.*;

public class APIContainer {

    // The list of the API that we can call
    private static Map<String, APIManager> managers = new HashMap<>();

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

    private APIContainer(String download_properties) throws IOException
    {
        managers = DownloadProperties.readAPIContainerProperties(download_properties);
    }

    // Returns the info of every API manager
    public String getAPINames()
    {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<String, APIManager> a : managers.entrySet())
        {
            s.append(a.getKey()).append("\n");
        }
        return s.toString();
    }

    // Returns the possible parameters of the API whose info are in the String info
    public String getAPIPossibleParams(String name) throws IllegalArgumentException
    {
        return managers.get(name).getParams();
    }

    // Returns an instance of the API manager whose info are in the String info
    public APIManager getAPIManager(String name, List<QueryParam> l) throws IllegalArgumentException
    {
        APIManager a = managers.get(name);
        a.addParams(l);
        return a;
    }
}
