package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.QueryParam;

/**
 * This is a utility class that contains all the information related to the API of theGuardian.
 * It is used by the {@link GuardianAPIManager} class.
 *
 * @see GuardianAPIManager
 */
public class GuardianAPIInfo {
    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created, as this is a utility class.
     *
     */
    private GuardianAPIInfo() {}

    /**
     * Default URL of the API, it's the base to create the request, it is accessible by
     * the {@link GuardianAPIInfo#getDefaultURL()} method.
     *
     */
    private final static String defaultURL = "https://content.guardianapis.com/search?";

    /**
     * All the possible fields that can be put in a request to the API and their descriptions.
     * Some of them have a default value, specified in the description.
     * It is accessible through the {@link GuardianAPIInfo#getFormattedParams()} method
     *
     */
    private final static QueryParam[] possible_fields = {
            new QueryParam("api-key", "MANDATORY: the key to access the API"),
            new QueryParam("page-size","The number of articles to have in a single file .json. Values: 1-200. Default = 200"),
            new QueryParam("pages", "The number of pages to download from the API. Default is 5, which means that by default are downloaded 1000 articles"),
            new QueryParam("q", "The topic of the articles to analysis for. Default is \"nuclear power\""),
            new QueryParam("order-by","The way the articles should be ordered (we take the first n in that order). Default = relevance"),
            new QueryParam("from-date", "The date to search from, in the format yyyy-mm-dd"),
            new QueryParam("to-date", "The date to search to, in the format yyyy-mm-dd")
    };

    /**
     * The maximum length of the key that is accepted by the class.
     * It is used only to provide a good way to print to standard output the possible fields
     * in the {@link GuardianAPIInfo#getFormattedParams()} method.
     *
     */
    private final static int formatted_key_length = 20;

    /**
     * This method gives the possible fields to pass to the API and a description of them.
     * It formats them as they are to be printed to standard output.
     *
     * @return a formatted {@link String} which contains the fields and their description, one for each line, and their default value.
     */
    public static String getFormattedParams()
    {
        StringBuilder params = new StringBuilder();
        for (QueryParam q : possible_fields)
        {
            StringBuilder this_field = new StringBuilder(q.getKey());
            while (this_field.length() < formatted_key_length)
            {
                this_field.append(" ");
            }
            this_field.append(q.getValue()).append("\n");
            params.append(this_field);
        }
        return params.toString();
    }

    /**
     * This method gives the base URL of the API.
     *
     * @return a {@link String} that contains the URL.
     */
    public static String getDefaultURL()
    {
        return defaultURL;
    }

    /**
     * This method checks if the key of the field passed as a {@link String} as a parameter is a possible field of the API.
     *
     * @param key The key of the field to check.
     * @return true if the key of the field specified is valid.
     */
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