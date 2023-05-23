package it.unipd.dei.dbdc.DownloadAPI;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class APIFactory {

    // This is the Singleton design pattern
    private static APIFactory instance;

    // The list of the API that we can call
    private final static ArrayList<APICaller> callers = new ArrayList<>();

    // This is the only way we can access this class.
    public static APIFactory getInstance() throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (instance == null)
        {
            instance = new APIFactory();
        }
        return instance;
    }
    private APIFactory() throws IOException, ClassCastException, ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {

        // Prendo il nome del file
        InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream("download.properties");

        // Creo un oggetto di Properties
        Properties appProps = new Properties();
        appProps.load(propertiesFile); // Lancia IOException

        // Cerco la property "library" e quella "theGuardian"
        String adapterName = appProps.getProperty("library");
        String theGuardianCaller = appProps.getProperty("theGuardian");

        // Cerco le classi corrispondente
        Class<?> adapter_class = Class.forName(adapterName); // Lancia ClassNotFoundException
        Class<?> theGuardian_class = Class.forName(theGuardianCaller);

        // Creo e aggiungo i callers.
        // The adapter to call the API: is only one for every API, as it is only the library to send the requests.
        APIAdapter adapter = (APIAdapter) adapter_class.newInstance(); // Lancia ClassCastException o InstantiationException o IllegalAccessException
        Constructor<?> co = theGuardian_class.getConstructor(APIAdapter.class); // Lancia NoSuchMethodException
        callers.add((APICaller) co.newInstance(adapter)); // Lancia InvocationTargetException
    }

    // Returns the info of every API caller
    public String getAPIs()
    {
        String s = "";
        for (APICaller a : callers)
        {
            s += a.getInfo() + "\n";
        }
        return s;
    }

    // Returns the possible parameters of the API whose info are in the String info
    public String getAPIPossibleParams(String info) throws IllegalArgumentException
    {
        APICaller a = searchCaller(info);
        return a.possibleParams();
    }

    // Returns an instance of the API caller whose info are in the String info
    public APICaller getAPICaller(String info, List<QueryParam> l) throws IllegalArgumentException
    {
        APICaller a = searchCaller(info);
        a.addParams(l);
        return a;
    }

    // Search for a caller whose info are in the String
    private APICaller searchCaller(String info) throws IllegalArgumentException
    {
        for (APICaller a : callers)
        {
            if (a.getInfo().equals(info))
            {
                return a;
            }
        }
        throw new IllegalArgumentException();
    }
}
