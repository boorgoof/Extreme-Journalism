package it.unipd.dei.dbdc.DownloadAPI;

import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public class KongAPIAdapter implements APIAdapter {
    public KongAPIAdapter()
    {
        Unirest.config().enableCookieManagement(false);
    }
    public void sendRequest(String url, String path)
    {
        Unirest.get(url).asFile(path);
    }

    public void endRequests()
    {
        Unirest.shutDown();
    }
}
