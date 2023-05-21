package it.unipd.dei.dbdc;
import it.unipd.dei.dbdc.DownloadAPI.*;

import java.util.ArrayList;
import java.util.Scanner;

// L'unica classe che ha il main
public class UserInterface {
    public static void main(String[] args)
    {
        // Crea il caller tramite un Factory che crea tutte le istanze delle APICaller.
        APIFactory factory = APIFactory.getInstance();

        // Quale API vuoi chiamare? Dicendogli quali sono quelle disponibili
        Scanner sc = new Scanner(System.in);
        System.out.println("Inserire il nome della API che si vuole avere:\n"+factory.getAPIs());
        String s = sc.next();
        System.out.println("Inserire i parametri per la query, uno per ogni riga (inserire quit per terminare):\n"+factory.getAPIPossibleParams());
        ArrayList<QueryParams> queries = new ArrayList<>();
        while (sc.hasNextLine())
        {
            String k = sc.next();
            if (k.equalsIgnoreCase("quit"))
            {
                break;
            }
            String v = sc.next();
            queries.add(new QueryParams(k, v));
        }

        APICaller caller = factory.getAPICaller(s, queries);

        // Passa il controllo al DownloadHandler, passandogli anche un'istanza della API da chiamare.
        DownloadHandler dw = new DownloadHandler(caller);
        dw.download();
    }
}
