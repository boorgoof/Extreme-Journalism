package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.interfaces.APICaller;

import java.util.Map;

/**
 * This class implements the interface {@link Runnable}.
 * It represents a thread that can be called to send a request to the specified url
 * with the specified params and through the specified {@link APICaller} and save it
 * at the specified path.
 *
 * @see Runnable
 */
public class CallAPIThread implements Runnable {
    /**
     * The {@link APICaller} to pass the request to.
     *
     */
    private final APICaller caller;
    /**
     * Default URL of the API to call.
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
     * @param c The {@link APICaller} to pass the request.
     * @param u The base url of the API to call
     * @param p The path of the file where the response should be saved.
     * @param par The parameters of the API call.
     */
    public CallAPIThread(APICaller c, String u, String p, Map<String, Object> par)
    {
        caller = c;
        url = u;
        path = p;
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
        try
        {
            if (!caller.sendRequest(url, params, path)) {
                throw new IllegalArgumentException();
            }
        }
        //This is required as we don't know what are the customized exceptions that the library could throw
        catch(Exception e)
        {
            throw new IllegalArgumentException("The request made is not correct: "+e.getMessage());
        }
    }
}
