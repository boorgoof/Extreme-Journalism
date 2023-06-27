package it.unipd.dei.dbdc.download.interfaces;

import java.util.Map;

/**
 * This is the interface that should be implemented by any adapter
 * which provides a way to call any API.
 *
 */
public interface APICaller {

    /**
     * The main method of the interface: this is the way to send a request to the specified API with the specified params
     * and save the result of the call as a file at the specified path.
     *
     * @param url The base url of the API to call.
     * @param params The parameters of the API call.
     * @param path The path of the file where the response should be saved.
     * @return A boolean representing the success of the call
     */
    boolean sendRequest(String url, Map<String, Object> params, String path);

    /**
     * This method ends the connection with the server.
     * It should be called after the end of the requests to the server.
     *
     */
    void endRequests();
}
