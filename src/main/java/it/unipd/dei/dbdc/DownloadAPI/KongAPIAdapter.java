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
    public Path sendRequest(String url, String paramString)
    {
        Unirest.config().defaultBaseUrl(url);
        File result = Unirest.get(paramString).asFile("./database/file_kong.json").getBody();
        return result.toPath();
    }
}
