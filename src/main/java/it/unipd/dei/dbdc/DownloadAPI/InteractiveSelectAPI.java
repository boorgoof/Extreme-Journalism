package it.unipd.dei.dbdc.DownloadAPI;

import java.util.ArrayList;
import java.util.Scanner;

public class InteractiveSelectAPI {
    public static APIManager askAPI(APIContainer container)
    {
        Scanner sc = new Scanner(System.in);
        while (true)
        {
            System.out.println("Inserire il nome della API che si vuole avere:\n" + container.getAPINames());
            String name = sc.next();

            System.out.println("Inserire i parametri per la query, uno per ogni riga (inserire quit per terminare):\n" + container.getAPIPossibleParams(name));
            ArrayList<QueryParam> queries = new ArrayList<>();
            while (sc.hasNextLine()) {
                String k = sc.next();
                if (k.equalsIgnoreCase("quit")) {
                    break;
                }
                String v = sc.next();
                queries.add(new QueryParam(k, v));
            }

            try {
                return container.getAPIManager(name, queries);
            } catch (IllegalArgumentException e) {
                System.out.println("Nome o parametri forniti errati, riprovare");
            }
        }
    }
}
