package it.unipd.dei.dbdc.DownloadAPI;

import it.unipd.dei.dbdc.ConsoleColors;

import java.util.ArrayList;
import java.util.Scanner;

public class InteractiveSelectAPI {
    public static APIManager askAPI(APIContainer container)
    {
        System.out.println(ConsoleColors.BLUE + "Selecting the API manually..." + ConsoleColors.RESET);
        Scanner sc = new Scanner(System.in);

        while (true)
        {
            System.out.println("Inserire il nome della API che si vuole avere. Lista delle possibili API:\n" + container.getAPINames());
            String name = sc.nextLine();
            String par = "";
            try
            {
                par = container.getAPIPossibleParams(name);
            }
            catch (IllegalArgumentException e)
            {
                System.out.println("Il nome della API e' incorretto. Riprovare");
                continue;
            }

            System.out.println("Inserire i parametri per la query, uno per ogni riga (inserire quit per terminare):\n" + par);

            ArrayList<QueryParam> queries = new ArrayList<>();

            while (sc.hasNextLine()) {

                String line = sc.nextLine();

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

            }

            try {
                System.out.println(ConsoleColors.BLUE + "Processing the request..." + ConsoleColors.RESET);
                return container.getAPIManager(name, queries);
            } catch (IllegalArgumentException e) {
                System.out.println("Nome o parametri forniti errati, riprovare");
            }
        }
    }
}
