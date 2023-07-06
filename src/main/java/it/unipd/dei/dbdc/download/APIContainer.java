package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;

import java.io.IOException;
import java.util.*;

/**
 * This class contains all the possible {@link APIManager} as specified in the download properties file.
 * It uses the Singleton design pattern to read only one time the properties.
 *
 * @see DownloadProperties
 */
public class APIContainer {

    /**
     * A {@link Map} containing all the possible {@link APIManager}, representing all the possible APIs that we can call.
     *
     */
    private static Map<String, APIManager> managers = new HashMap<>();

    /**
     * This is the only instance of the class that is possible to obtain.
     *
     */
    private static APIContainer instance;

    /**
     * This returns the only instance of this class, and initializes it if it is not.
     * Once initialized without exceptions, this function returns always the same Container.
     *
     * @param download_properties The properties specified by the user where are specified all the possible {@link APIManager}.
     *                            If it is null, the default properties file will be used.
     * @throws IOException If both the default and specified by the user properties files are not present or they are incorrect.
     */
    public static APIContainer getInstance(String download_properties) throws IOException
    {
        if (instance == null)
        {
            instance = new APIContainer(download_properties);
        }
        return instance;
    }

    /**
     * The constructor, which calls the {@link DownloadProperties#readProperties(String)} function.
     *
     * @param download_properties The properties specified by the user where are specified all the possible {@link APIManager}.
     *                            If it is null, the default properties file will be used.
     * @throws IOException If both the default and specified by the user properties files are not present or they are incorrect.
     */
    private APIContainer(String download_properties) throws IOException
    {
        managers = DownloadProperties.readProperties(download_properties);
    }

    /**
     * A function that returns the names of all the {@link APIManager} that are present in the container.
     *
     *  @return A {@link String} with all the names of the {@link APIManager} inside the container.
     */
    public String getAPINames()
    {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<String, APIManager> a : managers.entrySet())
        {
            s.append(a.getKey()).append("\n");
        }
        return s.toString();
    }

    /**
     * The function which returns the possible parameters of the API whose name is passed as a parameter.
     *
     * @param name The name of the {@link APIManager}.
     * @throws IllegalArgumentException If there is no {@link APIManager} with that name.
     *  @return A {@link String} with the parameters of the {@link APIManager} whose name is specified as a parameter. These parameters are already formatted
     */
    public String getAPIPossibleParams(String name) throws IllegalArgumentException
    {
        return getManager(name).getFormattedParams();
    }

    /**
     * The function which return an instance of the {@link APIManager} whose name is passed as a parameter
     * which has the parameters that are also passed to the function.
     *
     * @param name The name of the {@link APIManager}.
     * @param l The {@link List} of the {@link QueryParam} to pass to the {@link APIManager}.
     * @throws IllegalArgumentException If there is no {@link APIManager} with that name, or the parameters passed are not valid.
     * @return A {@link APIManager} whose name is passed as a parameter which has the parameters that are also passed to the function.
     */
    public APIManager getAPIManager(String name, List<QueryParam> l) throws IllegalArgumentException
    {
        APIManager a = getManager(name).copy();
        a.addParams(l);
        return a;
    }

    /**
     * The utility function that determines if the name passed as a parameter is present in the container.
     *
     * @param name The name of the {@link APIManager}.
     * @throws IllegalArgumentException If there is no {@link APIManager} with that name.
     */
    private APIManager getManager(String name) throws IllegalArgumentException
    {
        APIManager a = managers.get(name);
        if (a == null)
        {
            throw new IllegalArgumentException();
        }
        return a;
    }
}
