package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InteractiveSelectAPI {

    private final APIContainer container;
    private final Scanner in;

    public InteractiveSelectAPI(Scanner sc, String download_props) throws IOException {
        container = APIContainer.getInstance(download_props);
        in = sc;
    }

    public String askAPIName()
    {
        System.out.println("Insert the name of the API to call. List of possible APIs:\n" + container.getAPINames());
        return in.nextLine();
    }

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

        System.out.println("Insert the parameters for the query, one for line (insert quit to end the insertion):\n" + par);

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
