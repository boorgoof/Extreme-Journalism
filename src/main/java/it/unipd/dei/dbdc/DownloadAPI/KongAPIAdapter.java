package it.unipd.dei.dbdc.DownloadAPI;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.File;

public class KongAPIAdapter implements APIAdapter {
    public KongAPIAdapter()
    {
        // This is needed as the cookies setting gives an error
        Unirest.config().enableCookieManagement(false);
    }
    public boolean sendRequest(String url, String path)
    {
        HttpResponse<File> res = Unirest.get(url).asFile(path);
        return res.isSuccess();
    }

    public void endRequests()
    {
        Unirest.shutDown();
    }
}
