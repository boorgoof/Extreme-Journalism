package it.unipd.dei.dbdc.DownloadAPI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// Questo caller ha le informazioni per ogni chiamata fatta al TheGuardian.
public class GuardianAPIManager implements APIManager {
    // TODO: semplifica questa classe

    // Default parameters for the call of the API: the default URI and other fields.
    public final static String defaultURL = "https://content.guardianapis.com/search?";
    private final static QueryParam[] default_fields = {
                    new QueryParam("show-fields", "bodyText,headline"),
                    new QueryParam("format", "json"),
                    new QueryParam("page-size", "200"),
                    new QueryParam("q", "\"nuclear power\"")
            };
    public final static int default_pages = 5;

    // The library to call the API
    private final APICaller caller;

    private final ArrayList<QueryParam> specified_params;

    // Possible fields, that are not mandatory. Some of them have a default value, specified in the default_fields array.
    private final QueryParam[] possible_fields = {
            new QueryParam("api-key", "MANDATORY: the key to access the API"),
            new QueryParam("page-size","The number of articles to have in a single file .json. Values: 1-200. Default = 200"),
            new QueryParam("pages", "The number of pages to download from the API. Default is 5, which means that by default are downloaded 1000 articles"),
            new QueryParam("q", "The topic of the articles to search for. Default is \"nuclear power\""),
            new QueryParam("order-by","The way the articles should be ordered (we take the first n in that order). Default = newest"),
            new QueryParam("from-date", "The date to search from"),
            new QueryParam("to-date", "The date to search to")
    };

    // To create this object, you have to pass a Caller to it
    public GuardianAPIManager(APICaller a) {
        caller = a;
        specified_params = new ArrayList<>(1);
    }

    // Returns the name of the API
    public String getAPIName()
    {
        return "TheGuardianAPI";
    }

    // Returns the possible fields, and the ones that are already initialized
    public String getParams()
    {
        StringBuilder params = new StringBuilder();
        for (QueryParam q : possible_fields)
        {
            params.append(q.getKey()).append("\t\t").append(q.getValue());
        }
        return params.toString();
    }

    // To add parameters
    public void addParams(List<QueryParam> l) throws IllegalArgumentException
    {
        if (l == null)
        {
            throw new IllegalArgumentException("The list of parameters is null");
        }
        for (QueryParam q : l)
        {
            addParam(q);
        }
    }

    // NumberFormatException is a subclass of IllegalArgumentException
    public void addParam(QueryParam q) throws IllegalArgumentException
    {
        for (QueryParam possibleField : possible_fields) {
            if (q.getKey().equals(possibleField.getKey())) {
                specified_params.add(q);
            }
        }
        throw new IllegalArgumentException("The key of the parameter "+q.getKey()+" is not present in the system");
    }

    // This calls the API
    public void callAPI(String path_folder) throws IllegalArgumentException, IOException
    {
        if (caller == null)
        {
            throw new IllegalArgumentException();
        }

        // Crea le richieste
        String[] requests = createRequests();

        // Manda le richieste tramite la libreria e le salva in file
        for (int i = 0; i<requests.length; i++)
        {
            String path = path_folder +"/"+getAPIName()+"/request"+(i+1)+".json";

            // Se qualcosa nel formato era errato, gia' alla prima chiamata vediamo l'errore
            if (!caller.sendRequest(requests[i], path))
            {
                throw new IllegalArgumentException("Query parameters are not correct");
            }
        }
        caller.endRequests();
    }

    // It's a function that creates the queries
    private String[] createRequests() throws IllegalArgumentException
    {
        // Crea la stringa di base:
        // 1. Crea una stringa con il default URL
        StringBuilder res = new StringBuilder(defaultURL);

        int pages = default_pages;
        boolean is_there_page_size = false;
        boolean is_there_q = false;
        boolean is_there_api_key = false;

        // 2. Aggiunge i parametri specificati
        for (QueryParam el : specified_params)
        {
            switch (el.getKey()) {
                case ("api-key"):
                    is_there_api_key = true;
                    break;
                case ("pages"):
                    pages = Integer.parseInt(el.getValue());
                    continue;
                case ("page-size"):
                    is_there_page_size = true;
                    break;
                case ("q"):
                    is_there_q = true;
                    break;
            }
            res.append(el.getKey()).append("=").append(el.getValue()).append("&");
        }

        // 3. Controlla se c'è la api-key
        if (!is_there_api_key)
        {
            throw new IllegalArgumentException("Specify the api-key");
        }

        // 4. Mette i default values, se questi non sono gia' stati messi
        for (QueryParam el : default_fields)
        {
            if (el.getKey().equals("page-size") && is_there_page_size || el.getKey().equals("q") && is_there_q)
            {
                continue;
            }
            res.append(el.getKey()).append("=").append(el.getValue()).append("&");
        }

        // 5. Crea l'array di stringhe
        String[] results = new String[pages];
        for (int i = 0; i<pages; i++) {
                results[i] = res + "page=" + (i+1);
        }
        return results;
    }
}
