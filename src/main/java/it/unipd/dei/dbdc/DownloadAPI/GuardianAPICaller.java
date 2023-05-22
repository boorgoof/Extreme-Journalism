package it.unipd.dei.dbdc.DownloadAPI;

import java.util.ArrayList;
import java.util.List;

// Questo caller ha le informazioni per ogni chiamata fatta al TheGuardian.
public class GuardianAPICaller implements APICaller {

    // TODO: take it from a properties file
    // Default URL of the API.
    public final static String defaultURI = "https://content.guardianapis.com/search";

    // TODO: take the folder (database) from a properties file
    // Location to put all the downloaded file
    public final static String folder_path = "./database/the_guardian/";

    // Possible fields, that are not mandatory. Some of them have a default value
    private final static QueryParam[] fields = {
            new QueryParam("number-of-articles","The total number of articles to download. Values: 1-200 or multiples of 200. Default = 1000"),
            new QueryParam("q", "The topic of the articles to search for."),
            new QueryParam("order-by","The way the articles should be ordered (we take the first n in that order). Default = newest"),
            new QueryParam("from-date", "The date to search from"),
            new QueryParam("to-date", "The date to search to")};

    private final static String utilityFields = "show-fields=bodyText,headline&format=json";
    // Mandatory fields
    private final static String mandatoryField = "api-key";

    // Fields specificated in the query
    private final ArrayList<QueryParam> params;

    // Some of the important fields
    private String apiKey;
    private int pageSize;
    private int pages;
    private final APIAdapter adapt;

    // To create this object, you have to pass an Adapter to it
    public GuardianAPICaller(APIAdapter a) {
        adapt = a;
        params = new ArrayList<>(0);
        apiKey = null;
        // Il numero di default di articoli da scaricare è 1000
        pages = 5;
        pageSize = 200;
    }

    // Returns the name of the API
    public String getInfo()
    {
        return "TheGuardianAPI";
    }

    // Returns the possible fields
    public String possibleParams()
    {
        String tot = mandatoryField+" MANDATORY\n";
        for (QueryParam par : fields)
        {
            tot += par.getKey() + "  " + par.getValue() + "\n";
        }
        return tot;
    }

    // To add parameters
    public void addParams(List<QueryParam> l) throws IllegalArgumentException
    {
        for (QueryParam q : l)
        {
            addParam(q);
        }
    }

    // NumberFormatException is a subclass of IllegalArgumentException
    public void addParam(QueryParam q) throws IllegalArgumentException
    {
        if (q.getKey().equals(mandatoryField))
        {
            apiKey = q.getValue();
        }
        else if (checkParams(q)) {
            params.add(q);
        }
        else {
            throw new IllegalArgumentException();
        }
    }

    // This checks the parameters that are not mandatory
    private boolean checkParams(QueryParam q) throws NumberFormatException
    {
        String key = q.getKey();
        for (int i = 0; i< fields.length; i++)
        {
            if (key.equals(fields[i].getKey()))
            {
                if (i == 0)
                {
                    // The first one is a special case.
                    addNumbers(q);
                }
                return true;
            }
        }
        return false;
    }

    // A function that permits to create the right query
    private void addNumbers(QueryParam q) throws NumberFormatException
    {
        int n = Integer.parseInt(q.getValue());
        pages = 1;
        if (n < pageSize)
        {
            pageSize = n;
        }
        while (n > pageSize)
        {
            pages++;
            n -= pageSize;
        }
    }

    // This calls the API
    public String callAPI() throws IllegalArgumentException {
        if (adapt == null)
        {
            throw new IllegalArgumentException();
        }
        String[] requests = queryStrings();
        for (int i = 0; i<requests.length; i++)
        {
            if (!adapt.sendRequest(requests[i], folder_path+"request"+(i+1)+".json"))
            {
                throw new IllegalArgumentException();
            }
        }
        adapt.endRequests();
        return folder_path;
    }

    // It's a function that determines the queries
    private String[] queryStrings()
    {
        String res = defaultURI + "?" + "api-key=" + apiKey +"&"+utilityFields;
        for (QueryParam el : params)
        {
            res += "&" + el.getKey() + "=" + el.getValue();
        }
        String[] results = new String[pages];
        for (int i = 0; i<pages; i++) {
                results[i] = res + "&page-size=" + pageSize + "&page=" + (i+1);
        }
        return results;
    }
}
