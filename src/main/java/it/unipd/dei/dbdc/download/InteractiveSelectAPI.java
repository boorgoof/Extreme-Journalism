package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * The class that asks the user interactively to select the API and its parameters.
 *
 */
public class InteractiveSelectAPI {

    /**
     * The instance of the {@link APIContainer} to take the information of the APIs from and to take
     * the selected {@link APIManager}
     *
     */
    private final APIContainer container;

    /**
     * The way to ask the user the API.
     *
     */
    private final Scanner in;

    /**
     * The constructor, which gets the {@link APIContainer} to get the information from and the way the user
     * should be asked.
     *
     * @param sc The {@link Scanner} that the user will use to select the API.
     * @param download_props The download properties specified by the user. If null, the default ones will be used.
     * @throws IOException If the download properties files (the default one and the one specified by the user) are not present or are not correct.
     */
    public InteractiveSelectAPI(Scanner sc, String download_props) throws IOException {
        container = APIContainer.getInstance(download_props);
        in = sc;
    }

    /**
     * The function that asks the user the name of the {@link APIManager} to call.
     *
     * @return A {@link String} representing the name of the {@link APIManager} selected.
     */
    public String askAPIName()
    {
        System.out.println("Insert the name of the API to call. List of possible APIs:\n" + container.getAPINames());
        return in.nextLine();
    }

    /**
     * The function that asks the user the parameters to add to the request through the possible parameters of the {@link APIManager}
     *
     * @param name The name of the {@link APIManager} the user asked to call.
     * @return A {@link APIManager} representing the selected API with all the parameter, or null if the parameters or the name were not correct.
     */
    public APIManager askParams(String name)
    {
        String par;
        try
        {
            par = container.getAPIPossibleParams(name);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("The name of the API is incorrect. Retry");
            return null;
        }

        System.out.println("Insert the parameters for the query, one for line (insert quit to end the insertion, retry to retry it (you have to specify all the parameters)):\n" + par);

        ArrayList<QueryParam> queries = new ArrayList<>();

        while (in.hasNextLine()) {

            String line = in.nextLine();

            String[] tokens = line.split("\\s+");

            if (tokens.length <= 1)
            {
                if (tokens.length == 1 && tokens[0].equalsIgnoreCase("quit"))
                {
                    break;
                }
                else if (tokens.length == 1 && tokens[0].equalsIgnoreCase("retry"))
                {
                    return null;
                }
                System.out.println("Insert a value with the parameter. Retry");
                continue;
            }

            String key = tokens[0];
            StringBuilder value = new StringBuilder(tokens[1]);
            for (int i = 2; i < tokens.length; i++)
            {
                value.append(" ").append(tokens[i]);
            }
            queries.add(new QueryParam(key, value.toString()));
        }

        try {
            return container.getAPIManager(name, queries);
        } catch (IllegalArgumentException e) {
            System.out.println("The name or the parameters were not correct. Retry.");
        }
        return null;
    }
}
