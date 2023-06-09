package it.unipd.dei.dbdc.download.src_callers;

import it.unipd.dei.dbdc.download.interfaces.APICaller;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class KongAPICaller implements APICaller {
    public KongAPICaller()
    {
        // This is needed as the cookies setting gives an error
        //Unirest.config().enableCookieManagement(false);
        Unirest.config().cookieSpec("ignoreCookies");
    }
    public boolean sendRequest(String base_url, Map<String, Object> params, String path) throws IOException {
        // To save the files in a path, we first have to make sure that there is
        // no other file with that name in that directory.
        Files.deleteIfExists(Paths.get(path));
        HttpResponse<File> res = Unirest.get(base_url).queryString(params).asFile(path);
        return res.isSuccess();
    }

    public void endRequests()
    {
        Unirest.shutDown();
    }
}
