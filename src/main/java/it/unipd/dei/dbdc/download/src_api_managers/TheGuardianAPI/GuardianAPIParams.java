package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.QueryParam;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    public void addParam(QueryParam param) throws IllegalArgumentException
    {
        String elem = param.getValue();
        String key = param.getKey();
        switch (key)
        {
            case ("api-key"):
                api_key = elem;
                return;
            case ("pages"):
                pages = Integer.parseInt(elem);
                return;
            case ("page-size"):
                page_size = Integer.parseInt(elem);
                return;
            case("q"):
                query = elem;
                return;
            case ("from-date"):
            case ("to-date"):
                if (!format(elem)) {
                    throw new IllegalArgumentException("The date is not in the correct format");
                }
        }
        specified_params.put(key, elem);
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

    private boolean format(String date)
    {
        // E' l'unico modo in cui accetta la data. Puo' anche essere dell'anno 1
        //TODO: semplifica
        if (!date.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}"))
        {
            return false;
        }
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(5, 7));
        int day = Integer.parseInt(date.substring(8));
        if (month > 12 || month <= 0 || day <= 0 || day > 31)
        {
            return false;
        }
        if ((day == 31 && (month == 4 || month == 6 || month == 9 || month == 11)) || (day > 29 && month == 2))
        {
            return false;
        }
        //Un anno divisibile per 100 (ad esempio il 1900) è un anno bisestile solo se è anche divisibile per 400
        return day != 29 || month != 2 || year % 4 == 0 && !((year % 100 == 0) && (year % 400 != 0));
    }
}
