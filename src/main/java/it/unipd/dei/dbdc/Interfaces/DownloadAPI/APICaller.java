package it.unipd.dei.dbdc.Interfaces.DownloadAPI;

import java.io.IOException;
import java.util.Map;

// The Objects that interacts with the libraries to call the API
public interface APICaller {

    // Return the status of the request
    boolean sendRequest(String url, Map<String, Object> param, String path) throws IOException;

    // Shuts down the communication
    void endRequests();
}
