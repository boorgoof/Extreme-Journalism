package it.unipd.dei.dbdc.DownloadAPI;

// The Objects that interacts with the libraries to call the API
public interface APIAdapter {

    // Return the status of the request
    boolean sendRequest(String url, String path);

    // Shuts down the communication
    void endRequests();
}
