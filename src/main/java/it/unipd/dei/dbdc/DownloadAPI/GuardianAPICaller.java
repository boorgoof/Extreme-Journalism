package it.unipd.dei.dbdc.DownloadAPI;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GuardianAPICaller implements APICaller {
    // Questo caller ha le informazioni per ogni chiamata fatta al TheGuardian.
    // ATTENZIONE: utilizziamo il Builder design pattern per le queries
    public final static String defaultURI = "https://content.guardianapis.com/search";

    public final static String folder_path = "./database/the_guardian/";

    private final static String[] fields = {"number-of-articles", "format", "order-by", "show-fields", "q", "from-date", "to-date"};
    private final static String[] mandatoryFields = {"api-key"};
    private ArrayList<QueryParams> params;

    private String apiKey;
    private int pageSize;
    private int pages;
    private APIAdapter adapt;

    public GuardianAPICaller() {
        params = new ArrayList<>(0);
        adapt = null;
        apiKey = null;
        pages = 1;
        pageSize = 200;
    }
    public GuardianAPICaller(APIAdapter a) {
        adapt = a;
        params = new ArrayList<>(0);
        apiKey = null;
        pages = 1;
        pageSize = 200;
    }

    public void addParam(QueryParams q) {
        if (!checkImportantParams(q))
        {
            if (checkParams(q)) {
                params.add(q);
                return;
            }
            throw new IllegalArgumentException();
        }
    }

    private boolean checkParams(QueryParams q)
    {
        String key = q.getKey();
        // I primi 2 sono gia' controllati
        for (int i = 2; i< fields.length; i++)
        {
            if (key.equals(fields[i]))
            {
                return true;
            }
        }
        return false;
    }

    private boolean checkImportantParams(QueryParams q)
    {
        if (q.getKey().equals("api-key"))
        {
            apiKey = q.getValue();
            return true;
        }
        else if (q.getKey().equals("number-of-articles"))
        {
            int n = Integer.parseInt(q.getValue());
            if (n < pageSize)
            {
                pageSize = n;
            }
            while (n > pageSize)
            {
                pages++;
                n -= pageSize;
            }
            return true;
        }
        return false;
    }

    public void addParams(List<QueryParams> l)
    {
        for (QueryParams q : l)
        {
            addParam(q);
        }
    }

    @Override
    public String callAPI() {
        String[] requests = queryStrings();
        for (int i = 0; i<requests.length; i++) {
            adapt.sendRequest(requests[i], folder_path+"request"+(i+1)+".json");
        }
        adapt.endRequests();
        return folder_path;
    }

    public String getInfo()
    {
        return "TheGuardianAPI";
    }

    public String possibleParams()
    {
        String tot = "";
        for (String s : mandatoryFields)
        {
            tot += s + " MANDATORY\n";
        }
        for (String s : fields)
        {
            tot += s + "\n";
        }
        return tot;
    }

    private String[] queryStrings()
    {
        String res = defaultURI + "?" + "api-key=" + apiKey;
        for (QueryParams el : params)
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
