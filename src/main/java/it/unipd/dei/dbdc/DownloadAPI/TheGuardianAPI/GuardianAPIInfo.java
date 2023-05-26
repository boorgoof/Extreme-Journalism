package it.unipd.dei.dbdc.DownloadAPI.TheGuardianAPI;

import it.unipd.dei.dbdc.DownloadAPI.QueryParam;

public class GuardianAPIInfo {
    // The name of the API
    private final static String name = "TheGuardianAPI";

    // Default URL to create the request.
    private final static String defaultURL = "https://content.guardianapis.com/search?";

    // Possible fields. Some of them have a default value, specified in the default_fields array.
    private final static QueryParam[] possible_fields = {
            new QueryParam("api-key", "MANDATORY: the key to access the API"),
            new QueryParam("page-size","The number of articles to have in a single file .json. Values: 1-200. Default = 200"),
            new QueryParam("pages", "The number of pages to download from the API. Default is 5, which means that by default are downloaded 1000 articles"),
            new QueryParam("q", "The topic of the articles to search for. Default is \"nuclear power\""),
            new QueryParam("order-by","The way the articles should be ordered (we take the first n in that order). Default = relevance"),
            new QueryParam("from-date", "The date to search from"),
            new QueryParam("to-date", "The date to search to")
    };

    private final static int max_key_length = 20;

    // Returns the name of the API
    public static String getAPIName()
    {
        return name;
    }

    // Returns the possible fields, and the ones that are already initialized
    public static String getParams()
    {
        StringBuilder params = new StringBuilder();
        for (QueryParam q : possible_fields)
        {
            StringBuilder this_field = new StringBuilder(q.getKey());
            while (this_field.length() < max_key_length)
            {
                this_field.append(" ");
            }
            this_field.append(q.getValue()).append("\n");
            params.append(this_field);
        }
        return params.toString();
    }

    public static String getDefaultURL()
    {
        return defaultURL;
    }

    public static boolean isPresent(String key)
    {
        for (QueryParam q : possible_fields)
        {
            if (q.getKey().equals(key))
            {
                return true;
            }
        }
        return false;
    }
}
