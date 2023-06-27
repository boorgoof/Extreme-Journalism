package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.QueryParam;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * This is a class which contains all the parameters specified by the user to call the theGuardian API.
 * It is used by the {@link GuardianAPIManager} class, and also has a set of default parameters, the same
 * specified in the {@link GuardianAPIInfo} class.
 *
 * @see GuardianAPIManager
 * @see GuardianAPIInfo
 */
public class GuardianAPIParams {

    /**
     * A map that contains the parameters specified by the user.
     *
     */
    private final Map<String, Object> specified_params;

    /**
     * Default fields to put in the request
     *
     */
    private final static QueryParam[] default_fields =
            {
                    new QueryParam("show-fields", "bodyText,headline"),
                    new QueryParam("format", "json")
            };

    /**
     * The api-key necessary for the call.
     *
     */
    private String api_key;

    /**
     * Pages of the call, it has a default value
     *
     */
    private int pages = 5;

    /**
     * Page size of the call, it has a default value.
     *
     */
    private int page_size = 200;

    /**
     * Query of the call, it has a default value.
     *
     */
    private String query = "\"nuclear power\"";

    /**
     * Default constructor: it only initializes the map of the specified params.
     *
     */
    public GuardianAPIParams()
    {
        specified_params = new HashMap<>();
    }

    /**
     * Copy constructor: It initializes the map to be identical to the one of the object
     * passed as a parameter.
     *
     * @param par The object to copy from
     */
    public GuardianAPIParams(GuardianAPIParams par)
    {
        specified_params = new HashMap<>(par.specified_params);
        api_key = par.api_key;
        pages = par.pages;
        page_size = par.page_size;
        query = par.query;
    }

    /**
     * Function to add a param to the specified params for the call.
     * If it's a date, the format should be yyyy-mm-dd
     *
     * @param param The param to add to the specified params
     * @throws IllegalArgumentException If the date specified is not in the correct format or is invalid
     */
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

    /**
     * Function to get the specified params for the call.
     * It returns a {@link ArrayList} of a number of {@link Map} equal to the number of pages of the request,
     * and every map contains all the specified params, plus the default params.
     *
     * @throws IllegalArgumentException If the api-key was not specified
     * @return A {@link ArrayList} of a number of {@link Map} equal to the number of pages of the request
     * and every map contains all the specified params, plus the default params.
     */
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

    /**
     * This function overrides the function of Object. It returns true if the Object passed
     * is of the same class and has the same parameters of this Object.
     *
     * @param o The Object to compare to
     * @return True if the two objects are equals
     */
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof GuardianAPIParams))
        {
            return false;
        }
        GuardianAPIParams obj = (GuardianAPIParams) o;
        return (Objects.equals(api_key, obj.api_key) && specified_params.equals(obj.specified_params) && page_size == obj.page_size && pages == obj.pages && query.equals(obj.query));
    }

    /**
     * Utility function to check the correctness of the date passed as a parameter.
     *
     * @return True if the format is correct and the date is valid (the year is valid if it's positive)
     */
    private static boolean format(String date)
    {
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
        return day != 29 || month != 2 || year % 4 == 0 && !((year % 100 == 0) && (year % 400 != 0));
    }
}
