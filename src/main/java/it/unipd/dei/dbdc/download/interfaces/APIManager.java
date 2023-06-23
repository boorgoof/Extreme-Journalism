package it.unipd.dei.dbdc.download.interfaces;

import it.unipd.dei.dbdc.download.QueryParam;

import java.util.List;

/**
 * This interface represents a manager of an API. It should be implemented to permit the call of that API.
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
     * The function which returns the formatted possible parameters.
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
     * The function calls the API with all the parameters specified before.
     *
     * @param path_folder The path of the folder where the files of the requests should be saved.
     * @throws IllegalArgumentException If there was an error in the calling of the API.
     */
    void callAPI(String path_folder) throws IllegalArgumentException;
}
