package it.unipd.dei.dbdc.download.src_callers;

import it.unipd.dei.dbdc.download.interfaces.APICaller;
import it.unipd.dei.dbdc.resources.PathManager;
//import javafx.scene.shape.Path;
import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * This class is an adapter which provides a simple way to call any API.
 * It implements the {@link APICaller} interface and uses the {@link Unirest} library.
 * It saves the downloaded files in the path specified.
 * At the end of the calls, the method {@link KongAPICaller#endRequests()} should be called to end the connection.
 *
 */
public class KongAPICaller implements APICaller {

    /**
     * The only constructor of this class.
     * It changes the cookies settings of {@link Unirest} to avoid errors during the calls.
     *
     */
    public KongAPICaller()
    {
        // This is needed as the cookies setting gives an error
        Unirest.config().cookieSpec("ignoreCookies");
    }

    /**
     * The main method of the class: it sends the request to the specified API with the specified params
     * and saves the result of the call as a file at the specified path.
     *
     * @param base_url The base url of the API to call.
     * @param params The parameters of the API.
     * @param path The path of the file where the response should be saved.
     * @return A boolean representing the success of the call
     * @throws IOException If the specified path to the file does not exist, or the parameters are not correct.
     */
    @Override
    public boolean sendRequest(String base_url, Map<String, Object> params, String path) {
        // To save the files in a path, we first have to make sure that there is
        // no other file with that name in that directory.
        PathManager.deleteDirOrFile(new File(path));
        HttpResponse<File> res = Unirest.get(base_url).queryString(params).asFile(path);
        return res.isSuccess();
    }

    /**
     * This method ends the connection with the server. It should be called after the end of the requests to the server.
     *
     */
    @Override
    public void endRequests()
    {
        Unirest.shutDown();
    }
}
