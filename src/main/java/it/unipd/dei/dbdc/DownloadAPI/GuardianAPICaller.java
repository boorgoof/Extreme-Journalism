package it.unipd.dei.dbdc.DownloadAPI;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class GuardianAPICaller implements APICaller {
    // Questo caller ha le informazioni per ogni chiamata fatta al TheGuardian.
    // Non è tuttavia la classe che fa la chiamata
    // ATTENZIONE: utilizziamo il Builder design pattern per le queries
    public final static String defaultURI = "https://content.guardianapis.com/search";
    public final static String showFields = "bodyText,headline";
    public final static String format = "json";

    private final String apiKey;
    private final ArrayList<QueryParams> params;
    private final APIAdapter adapt;

    public GuardianAPICaller(APIAdapter a, String k) {
        adapt = a;
        apiKey = k;
        params = new ArrayList<>();
    }

    // TODO: migliora la logica di questa cosa: dovresti potergli passare solo alcuni argomenti, e in base a quelli ti crea tutto
    public void addParam(QueryParams q) {
        params.add(q);
    }

    public void addParams(List<QueryParams> l)
    {
        params.addAll(l);
    }

    @Override
    public Path callAPI() {
        String par = queryString(params, apiKey);
        return adapt.sendRequest(defaultURI, par);
    }

    private static String queryString(List<QueryParams> l, String apiKey)
    {
        String res = "?" + "api-key=" + apiKey;
        for (QueryParams el : l)
        {
            // Potrei aggiungere un controllo per i parametri passati
            res += "&"+el.getKey()+"="+el.getValue();
        }
        return res;
    }
}
