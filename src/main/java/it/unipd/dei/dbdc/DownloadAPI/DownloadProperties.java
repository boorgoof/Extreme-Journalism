package it.unipd.dei.dbdc.DownloadAPI;

import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APICaller;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APIManager;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

public class DownloadProperties {

    private final static String caller_key = "library";

    public static ArrayList<APIManager> readProperties(String properties_file) throws IOException {

        Properties appProps = getProperties(properties_file);

        // 1. Cerco la property del caller, che è la classe che implementa APICaller
        String caller_name = appProps.getProperty(caller_key);
        Class<?> caller_class;
        try {
            caller_class = Class.forName(caller_name);
        }
        catch (ClassNotFoundException e)
        {
            throw new IOException("There is no "+caller_key+" property in the file "+properties_file + " or the value is not correct");
        }

        // 2. Creo un'istanza di questa classe che implementa APICaller
        APICaller caller;
        try {
            caller = (APICaller) caller_class.newInstance(); // Lancia ClassCastException o InstantiationException o IllegalAccessException
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            throw new IOException("The class "+caller_name+" is not in the correct format.\n" +
                    "It should have a constructor with no parameters and it should implement APICaller interface");
        }

        ArrayList<APIManager> managers = new ArrayList<>(1);
        // 3. Leggendo le properties, creo tutte le istanze degli APIManager di tutte le API specificate
        try {
            Enumeration<?> enumeration = appProps.propertyNames();
            while (enumeration.hasMoreElements())
            {
                String prop = (String) enumeration.nextElement();
                if (prop.equals(caller_key))
                {
                    continue;
                }
                String manager_name = appProps.getProperty(prop);
                Class<?> manager_class = Class.forName(manager_name);
                Constructor<?> constructor = manager_class.getConstructor(APICaller.class); // Lancia NoSuchMethodException
                managers.add((APIManager) constructor.newInstance(caller)); // Lancia InvocationTargetException
            }
        }
        catch (InstantiationException | IllegalAccessException | ClassCastException | ClassNotFoundException | NoSuchMethodException | InvocationTargetException ex)
        {
            throw new IOException("The format of the file is not correct.\n" +
                    "It should have a key string representing the name of the API and " +
                    "as a value the class that implements the manager of that API.\n" +
                    "This class should also implement APIManager interface and have a constructor that accepts an instance of an APICaller as argument.");
        }
        return managers;
    }

    private static Properties getProperties(String properties_file) throws IOException
    {
        // 1. Prendo il nome del file
        InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(properties_file);

        // 2. Creo un oggetto di Properties
        Properties appProps = new Properties();
        try {
            appProps.load(propertiesFile);
        }
        catch (IOException e)
        {
            throw new IOException("There is no file properties with this name: "+properties_file);
        }
        return appProps;
    }








}
