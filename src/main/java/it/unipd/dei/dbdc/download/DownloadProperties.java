package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APICaller;
import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.tools.PropertiesTools;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * This utility class analyzes the properties file that specifies what are the possible {@link APIManager} that the user can call.
 *
 * @see Properties
 * @see PropertiesTools
 */
public class DownloadProperties {

    /**
     * The only constructor of the class. It is declared private to
     * prevent the default constructor to be created, as this is only a utility class.
     *
     */
    private DownloadProperties() {}

    /**
     * The name of the default properties file. It is present in the folder tools.
     *
     */
    public final static String default_properties = "download.properties";

    /**
     * The key of the parameter of the properties file that specifies the {@link APICaller} to use
     *
     */
    private final static String caller_key = "library";

    /**
     * The function that reads the properties file and returns an {@link HashMap} of {@link APIManager} and their names
     * specified in the properties file.
     *
     * @param out_properties The name of the properties file specified by the user. If null, the default properties file will be used.
     * @return An {@link HashMap} of {@link APIManager} and their names specified in the properties file.
     * @throws IOException If the user's specified properties file is invalid, or if the default properties are invalid or missing.
     */
    public static HashMap<String, APIManager> readProperties(String out_properties) throws IOException {

        Properties downProps = PropertiesTools.getProperties(default_properties, out_properties);

        //Search the APICaller
        String caller_name = downProps.getProperty(caller_key);
        Class<?> caller_class;
        try {
            caller_class = Class.forName(caller_name);
        } catch (ClassNotFoundException | NullPointerException e) {
            throw new IOException("There is no " + caller_key + " property in the file of the download properties, or the value is not correct");
        }

        APICaller caller;
        try {
            caller = (APICaller) caller_class.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            throw new IOException("The class " + caller_name + " is not in the correct format.\n" +
                    "It should have a constructor with no parameters and it should implement APICaller interface");
        }

        //Creates the HashMap with all the APIManagers and their names
        HashMap<String, APIManager> managers = new HashMap<>(1);
        try {
            Enumeration<?> enumeration = downProps.propertyNames();
            while (enumeration.hasMoreElements()) {
                String prop = (String) enumeration.nextElement();
                if (prop.equals(caller_key)) {
                    continue;
                }
                String manager_name = downProps.getProperty(prop);
                Class<?> manager_class = Class.forName(manager_name);
                Constructor<?> constructor = manager_class.getConstructor(APICaller.class, String.class);
                managers.put(prop, (APIManager) constructor.newInstance(caller, prop));
            }
        } catch (InstantiationException | IllegalAccessException | ClassCastException | ClassNotFoundException |
                 NoSuchMethodException | InvocationTargetException | NullPointerException ex) {
            throw new IOException("The format of the download properties file is not correct.\n" +
                    "It should have a key string representing the name of the API and " +
                    "the class that implements the manager of that API as a value.\n" +
                    "This class should also implement APIManager interface and have a constructor that accepts an instance of an APICaller and a String as arguments.");
        }
        return managers;
    }
}