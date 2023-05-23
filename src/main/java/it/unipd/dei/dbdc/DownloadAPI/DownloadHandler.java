package it.unipd.dei.dbdc.DownloadAPI;

import java.io.IOException;

public class DownloadHandler
{
    // Rappresenta l'API che voglio chiamare. E' una sola.
    private final APICaller caller;

    public DownloadHandler(APICaller a)
    {
        caller = a;
    }

    public String download() throws IllegalArgumentException, IOException
    {
        if (caller == null) {
            throw new IllegalArgumentException();
        }
        return caller.callAPI();
    }
}
