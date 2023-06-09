package it.unipd.dei.dbdc.DownloadAPI;

import it.unipd.dei.dbdc.Console;
import it.unipd.dei.dbdc.Interfaces.DownloadAPI.APIManager;

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

            Scanner scan = new Scanner(line);
            if (scan.hasNext())
            {
                String key = scan.next();
                if (key.equalsIgnoreCase("quit"))
                {
                    break;
                }

                // TODO: migliora questa logica
                StringBuilder value = new StringBuilder();
                if (scan.hasNext())
                {
                    value.append(scan.next());
                }
                else {
                    Console.printlnInteractiveInfo("Fornire un valore al parametro, riprovare");
                    continue;
                }
                while (scan.hasNext())
                {
                    value.append(" ").append(scan.next());
                }
                queries.add(new QueryParam(key, value.toString()));
            }
            scan.close();

        }

        try {
            return container.getAPIManager(name, queries);
        } catch (IllegalArgumentException e) {
            Console.printlnInteractiveInfo("Nome o parametri forniti errati, riprovare");
        }
        return null;
    }
}
