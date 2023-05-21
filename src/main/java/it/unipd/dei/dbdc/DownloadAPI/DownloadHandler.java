package it.unipd.dei.dbdc.DownloadAPI;

public class DownloadHandler
{
    // Rappresenta l'API che voglio chiamare. E' una sola.
    private APICaller caller;

    public DownloadHandler(APICaller a)
    {
        caller = a;
    }

    public String download() throws IllegalArgumentException
    {
        if (caller == null) {
            throw new IllegalArgumentException();
        }
        return caller.callAPI();
    }
}
