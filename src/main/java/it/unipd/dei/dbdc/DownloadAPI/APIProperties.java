package it.unipd.dei.dbdc.DownloadAPI;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class APIProperties {

    // The list of the API that we can call
    private final static ArrayList<APIManager> managers = new ArrayList<>();

    private final static String properties_file = "download.properties";

    public APIProperties() throws IOException {

        // 1. Prendo il nome del file
        InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(properties_file);

        // 2. Creo un oggetto di Properties
        Properties appProps = new Properties();
        try {
            appProps.load(propertiesFile);
        }
        catch (IOException e)
        {
            throw new IOException("There is no file named download.properties in src/main/resources");
        }

        // 3. Leggo queste properties (in una funzione separata solo per chiarezza)
        readProperties(appProps);
    }

    private void readProperties(Properties appProps) throws IOException
    {
        // 1. Cerco la property "library", che è la classe che implementa APICaller
        String caller_name = appProps.getProperty("library");
        Class<?> caller_class;
        try {
            caller_class = Class.forName(caller_name);
        }
        catch (ClassNotFoundException e)
        {
            throw new IOException("There is no library property in the file "+properties_file);
        }

        // 2. Creo un'istanza di questa classe che implementa APICaller
        APICaller adapter;
        try {
            adapter = (APICaller) caller_class.newInstance(); // Lancia ClassCastException o InstantiationException o IllegalAccessException
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            throw new IOException("The class "+caller_name+" is not in the correct format.\n" +
                    "It should have a constructor with no parameters and it should implement APICaller interface");
        }

        // 3. Leggendo le properties, creo tutte le istanze degli APIManager di tutte le API specificate
        try {
            Enumeration<?> enumeration = appProps.propertyNames();
            while (enumeration.hasMoreElements())
            {
                String prop = (String) enumeration.nextElement();
                if (prop.equals("library"))
                {
                    continue;
                }
                String manager_name = appProps.getProperty(prop);
                Class<?> manager_class = Class.forName(manager_name);
                Constructor<?> constructor = manager_class.getConstructor(APICaller.class); // Lancia NoSuchMethodException
                managers.add((APIManager) constructor.newInstance(adapter)); // Lancia InvocationTargetException
            }
        }
        catch (InstantiationException | IllegalAccessException | ClassCastException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException ex)
        {
            throw new IOException("The format of the file is not correct.\n" +
                    "It should have a key string representing the name of the API and " +
                    "as a value the class that implements the manager of that API.\n" +
                    "This class should also implement APIManager interface and have a constructor that accepts an instance of an APICaller as argument.");
        }
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
    public APIManager getAPICaller(String name, List<QueryParam> l) throws IllegalArgumentException
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
