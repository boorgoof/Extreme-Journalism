package it.unipd.dei.dbdc.DownloadAPI;

import java.nio.file.Path;

public class DownloadHandler
{
    // Rappresenta l'API che voglio chiamare
    private APICaller caller;

    public DownloadHandler(APICaller a)
    {
        caller = a;
    }

    public void download()
    {
        if (caller == null) {
            throw new IllegalStateException();
        }
        String path = caller.callAPI();
        // Chiama il SerializationHandler passandogli il Path dei file downloadati, o restituisce al main il path
    }
}
