package it.unipd.dei.dbdc.download.interfaces;

import it.unipd.dei.dbdc.download.QueryParam;

import java.util.List;

/**
 * This interface represents a manager of an API, which is a class that has the logic for the passing
 * of the parameters to a specific API. It should be implemented to permit the call of that API.
 * All the APIManagers should also have a constructor that requires a {@link APICaller} and a {@link String}
 * representing the class to use to call the API and the name of the API, as it is presented to the user.
 *
 */
public interface APIManager {

    /**
     * The function which returns the name of the API.
     *
     * @return A {@link String} which contains name of the API.
     */
    String getName();

    /**
     * The function which returns the possible parameters for the call, formatted to be printed
     * in standard output.
     *
     * @return A {@link String} which contains the formatted parameters.
     */
    String getFormattedParams();

    /**
     * The function which permits to add parameters to the request to the API.
     *
     * @param l The list of the {@link QueryParam} parameters to pass to the API.
     * @throws IllegalArgumentException If one of those parameters is invalid.
     */
    void addParams(List<QueryParam> l) throws IllegalArgumentException;

    /**
     * The function that calls the API with all the parameters specified before, and
     * saves the files resulting in the specified folder.
     *
     * @param path_folder The path of the folder where the files of the requests should be saved.
     * @throws IllegalArgumentException If there was an error in the calling of the API.
     */
    void callAPI(String path_folder) throws IllegalArgumentException;

    /**
     * The function copies this object and gives another object that is identical to it.
     *
     * @return An {@link APIManager} which is the copy of this one.
     */
    APIManager copy();
}
