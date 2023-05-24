package it.unipd.dei.dbdc.DownloadAPI.TheGuardianAPI;

import it.unipd.dei.dbdc.DownloadAPI.QueryParam;

import java.util.ArrayList;

public class GuardianAPIRequests {

    // Default URL to create the request.
    private final static String defaultURL = "https://content.guardianapis.com/search?";

    // Default fields to put in the request
    private final static String default_fields = "show-fields=bodyText,headline&format=json";

    // Important parameter that must be specified
    private String api_key;

    // Parameters that have a default value
    private int pages = 5;
    private int page_size = 200;
    private String q = "\"nuclear power\"";

    private final ArrayList<QueryParam> specified_params;

    public GuardianAPIRequests()
    {
        // Dovrebbe esserci almeno la key
        specified_params = new ArrayList<>(1);
    }

    public void addParam(QueryParam param)
    {
        switch (param.getKey())
        {
            case ("api-key"):
                api_key = param.getValue();
                return;
            case ("pages"):
                pages = Integer.parseInt(param.getValue());
                return;
            case ("page-size"):
                page_size = Integer.parseInt(param.getValue());
                return;
            case ("q"):
                q = param.getValue();
                return;
        }
        specified_params.add(param);
    }

    // It's a function that creates the queries
    public String[] createRequests() throws IllegalArgumentException
    {
        if (api_key == null)
        {
            throw new IllegalArgumentException("Api-key not specified");
        }
        // Crea la stringa di base:
        // 1. Crea una stringa con il default URL
        StringBuilder res = new StringBuilder(defaultURL+"api-key="+api_key+"&page-size="+page_size+"&q="+q);

        // 2. Aggiunge i parametri specificati
        for (QueryParam el : specified_params)
        {
            res.append("&").append(el.getKey()).append("=").append(el.getValue());
        }

        // 3. Mette i default values, se questi non sono gia' stati messi
        res.append("&").append(default_fields);

        // 4. Crea l'array di stringhe
        String[] results = new String[pages];
        for (int i = 0; i<pages; i++) {
            results[i] = res + "&page=" + (i+1);
        }
        return results;
    }
}
