package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.interfaces.APICaller;

import java.util.Map;

/**
 * This class implements the interface {@link Runnable}.
 * It represents a thread that can be called to send a request to the specified URL
 * with the specified params and through the specified {@link APICaller} and save it
 * at the specified path.
 * It is used by {@link GuardianAPIManager}, but can also be used by any {@link it.unipd.dei.dbdc.download.interfaces.APIManager}.
 *
 * @see GuardianAPIManager
 * @see Runnable
 */
public class CallAPIThread implements Runnable {
    /**
     * The {@link APICaller} to pass the request to.
     *
     */
    private final APICaller caller;
    /**
     * The URL of the API to call.
     *
     */
    private final String url;
    /**
     * The path of the file where the response should be saved.
     *
     */
    private final String path;
    /**
     * The parameters to pass to the request.
     *
     */
    private final Map<String, Object> params;

    /**
     * The constructor of the class: it accepts all the parameters of the API call
     * and the {@link APICaller} to pass the call to.
     *
     * @param call The {@link APICaller} to pass the request.
     * @param url_ The base url of the API to call
     * @param path_ The path of the file where the response should be saved.
     * @param par The parameters of the call to the API.
     */
    public CallAPIThread(APICaller call, String url_, String path_, Map<String, Object> par)
    {
        caller = call;
        url = url_;
        path = path_;
        params = par;
    }

    /**
     * The method that overrides the one of the {@link Runnable} interface:
     * it simply sends the request with the parameters passed to the constructor.
     *
     * @throws IllegalArgumentException if there is an error in the call or if the caller passed to the constructor is null
     */
    @Override
    public void run() throws IllegalArgumentException {
        if (caller == null || !caller.sendRequest(url, params, path)) {
            throw new IllegalArgumentException("The request made is not correct");
        }
    }
}
