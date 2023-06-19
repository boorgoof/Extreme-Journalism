package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.QueryParam;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GuardianAPIInfoTest {
    private final static String defaultURL = "https://content.guardianapis.com/search?";
    private final static QueryParam[] possible_fields = {
            new QueryParam("api-key", "MANDATORY: the key to access the API"),
            new QueryParam("page-size","The number of articles to have in a single file .json. Values: 1-200. Default = 200"),
            new QueryParam("pages", "The number of pages to download from the API. Default is 5, which means that by default are downloaded 1000 articles"),
            new QueryParam("q", "The topic of the articles to search for. Default is \"nuclear power\""),
            new QueryParam("order-by","The way the articles should be ordered (we take the first n in that order). Default = relevance"),
            new QueryParam("from-date", "The date to search from"),
            new QueryParam("to-date", "The date to search to")
    };
    @Test
    public void getFormattedParams()
    {
        StringBuilder par = new StringBuilder();
        for (QueryParam q : possible_fields)
        {
            StringBuilder this_field = new StringBuilder(q.getKey());
            while (this_field.length() < GuardianAPIInfo.max_key_length)
            {
                this_field.append(" ");
            }
            this_field.append(q.getValue()).append("\n");
            par.append(this_field);
        }
        String params = par.toString();
        assertEquals(params, GuardianAPIInfo.getFormattedParams());
    }

    @Test
    public void getDefaultURL()
    {
        assertEquals(defaultURL, GuardianAPIInfo.getDefaultURL());
    }

    @Test
    public void isPresent()
    {
        for (QueryParam q : possible_fields) {
            assertTrue(GuardianAPIInfo.isPresent(q.getKey()));
        }

        assertFalse(GuardianAPIInfo.isPresent("invalid"));
        assertFalse(GuardianAPIInfo.isPresent("apikey"));
        assertFalse(GuardianAPIInfo.isPresent("page"));
    }

}
