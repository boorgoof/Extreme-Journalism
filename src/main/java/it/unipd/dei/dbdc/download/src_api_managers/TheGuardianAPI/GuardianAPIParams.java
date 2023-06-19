package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.QueryParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GuardianAPIParams {

    // Default fields to put in the request
    private final static QueryParam[] default_fields =
            {
                    new QueryParam("show-fields", "bodyText,headline"),
                    new QueryParam("format", "json")
            };

    // Important parameter that must be specified
    private String api_key;

    // Parameters that have a default value
    private int pages = 5;
    private int page_size = 200;
    private String query = "\"nuclear power\"";

    private final Map<String, Object> specified_params;

    public GuardianAPIParams()
    {
        specified_params = new HashMap<>();
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
            case("q"):
                query = param.getValue();
                return;
        }
        specified_params.put(param.getKey(), param.getValue());
        //TODO: come funziona la data?
    }

    public ArrayList<Map<String, Object>> getParams() throws IllegalArgumentException
    {
        if (api_key == null)
        {
            throw new IllegalArgumentException("Api-key not specified");
        }
        specified_params.put("api-key", api_key);
        specified_params.put("page-size", page_size);
        specified_params.put("q", query);
        for (QueryParam q : default_fields) {
            specified_params.put(q.getKey(), q.getValue());
        }
        ArrayList<Map<String, Object>> ret = new ArrayList<>(pages);
        for (int i = 0; i<pages; i++)
        {
            Map<String, Object> others = new HashMap<>(specified_params);
            others.put("page", (i+1));
            ret.add(i, others);
        }
        return ret;
    }
}
