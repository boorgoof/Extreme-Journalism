package it.unipd.dei.dbdc.DownloadAPI;

import java.io.IOException;

// The Objects that interacts with the libraries to call the API
public interface APICaller {

    // Return the status of the request
    boolean sendRequest(String url, String path) throws IOException;

    // Shuts down the communication
    void endRequests();
}
