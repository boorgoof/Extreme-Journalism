package it.unipd.dei.dbdc.DownloadAPI;

import java.util.ArrayList;
import java.util.Scanner;

public class InteractiveSelectAPI {

    public static String askAPIName(Scanner in, APIContainer container)
    {
        System.out.println("Inserire il nome della API che si vuole avere. Lista delle possibili API:\n" + container.getAPINames());
        return in.nextLine();
    }

    public static APIManager askParams(Scanner in, APIContainer container, String name)
    {
        String par = "";
        try
        {
            par = container.getAPIPossibleParams(name);
        }
        catch (IllegalArgumentException e)
        {
            System.out.println("Il nome della API e' incorretto. Riprovare");
            return null;
        }

        System.out.println("Inserire i parametri per la query, uno per ogni riga (inserire quit per terminare):\n" + par);

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

                StringBuilder value = new StringBuilder();
                if (scan.hasNext())
                {
                    value.append(scan.next());
                }
                else {
                    System.out.println("Fornire un valore al parametro, riprovare");
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
            System.out.println("Nome o parametri forniti errati, riprovare");
        }
        return null;
    }
}
