package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.Console;
import it.unipd.dei.dbdc.download.interfaces.APIManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class InteractiveSelectAPI {

    private final APIContainer container;

    private final Scanner in;

    public InteractiveSelectAPI(String download_props, Scanner sc) throws IOException {
        container = APIContainer.getInstance(download_props);
        in = sc;
    }

    public String askAPIName()
    {
        Console.printlnInteractiveInfo("Inserire il nome della API che si vuole avere. Lista delle possibili API:\n" + container.getAPINames());
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
            Console.printlnInteractiveInfo("Il nome della API e' incorretto. Riprovare");
            return null;
        }

        Console.printlnInteractiveInfo("Inserire i parametri per la query, uno per ogni riga (inserire quit per terminare):\n" + par);

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
                Console.printlnInteractiveInfo("Fornire un valore al parametro, riprovare");
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
            Console.printlnInteractiveInfo("Nome o parametri forniti errati, riprovare");
        }
        return null;
    }
}
