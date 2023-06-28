package it.unipd.dei.dbdc.download.src_callers;

import it.unipd.dei.dbdc.download.DownloadHandlerTest;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIInfo;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManagerTest;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class KongAPICallerTest {

    private static final String key = GuardianAPIManagerTest.key;
    private static KongAPICaller caller;
    @BeforeAll
    public static void KongAPICaller()
    {
        caller = new KongAPICaller();
        assertNotNull(caller);
    }

    @Test
    public void sendRequest() {
        //TRUE TESTS
        int i = 0;
        //FALSE TESTS
        int j = 0;

        //PARAMETERS:
        Map<String, Object> fields1 = new TreeMap<>();
        String defaultURL = GuardianAPIInfo.getDefaultURL();
        String outputFolder = DownloadHandlerTest.resources_url+"kong/";

        //FIRST PART: pass every possible combination of parameters to the theGuardian API

        //PAGE-SIZE

        //Max
        fields1.put( "api-key", key);
        fields1.put( "page-size", "200");
        fields1.put( "page", "5");
        fields1.put( "q", "\"solar energy\"");
        fields1.put( "order-by", "newest");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //Min
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "1");
        fields1.put( "page", "5");
        fields1.put( "q", "\"solar energy\"");
        fields1.put( "order-by", "newest");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //Medium
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "130");
        fields1.put( "page", "5");
        fields1.put( "q", "\"solar energy\"");
        fields1.put( "order-by", "newest");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //Invalid
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "245");
        fields1.put( "page", "5");
        fields1.put( "q", "\"solar energy\"");
        fields1.put( "order-by", "newest");

        assertFalse(caller.sendRequest(defaultURL, fields1, outputFolder+"false"+j+".json"));

        //Invalid
        j++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "-34");
        fields1.put( "page", "5");
        fields1.put( "q", "\"solar energy\"");
        fields1.put( "order-by", "newest");

        assertFalse(caller.sendRequest(defaultURL, fields1, outputFolder+"false"+j+".json"));

        //PAGE

        //Min
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "1");
        fields1.put( "q", "\"solar energy\"");
        fields1.put( "order-by", "newest");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //Max
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "19");
        fields1.put( "q", "\"solar energy\"");
        fields1.put( "order-by", "newest");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //Medium
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "10");
        fields1.put( "q", "\"solar energy\"");
        fields1.put( "order-by", "newest");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //Invalid
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "20");
        fields1.put( "q", "\"solar energy\"");
        fields1.put( "order-by", "newest");

        assertFalse(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //Invalid
        j++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "-1");
        fields1.put( "q", "\"solar energy\"");
        fields1.put( "order-by", "newest");

        assertFalse(caller.sendRequest(defaultURL, fields1, outputFolder+"false"+j+".json"));

        //Q

        //Valid
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "newest");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "banana");
        fields1.put( "order-by", "newest");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "properties of the kingdom"); // It does not contain the "
        fields1.put( "order-by", "newest");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //Valid (there should not be pages)
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "1");
        fields1.put( "q", "hfaihaiha");
        fields1.put( "order-by", "newest");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //Invalid q, but the request should be true anyway
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", null);
        fields1.put( "order-by", "newest");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //ORDER-BY

        //Oldest
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "oldest");
        fields1.put( "from-date", "1902-01-03");
        fields1.put( "to-date", "2023-06-18");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //Relevance
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "relevance");
        fields1.put( "from-date", "1902-01-03");
        fields1.put( "to-date", "2023-06-18");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //Invalid
        j++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "ugo");
        fields1.put( "from-date", "1902-01-03");
        fields1.put( "to-date", "2023-06-18");

        assertFalse(caller.sendRequest(defaultURL, fields1, outputFolder+"false"+j+".json"));

        //DATE

        //Valid
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "oldest");
        fields1.put( "from-date", "1789-01-03");
        fields1.put( "to-date", "2000-06-18");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "oldest");
        fields1.put( "from-date", "1012-01-03");
        fields1.put( "to-date", "2021-06-18");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "oldest");
        fields1.put( "from-date", "1902-01-03");
        fields1.put( "to-date", "3056-06-18");

        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        j++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "oldest");
        fields1.put( "from-date", null);
        fields1.put( "to-date", "3056-06-18");

        assertFalse(caller.sendRequest(defaultURL, fields1, outputFolder+"false"+j+".json"));

        j++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "oldest");
        fields1.put( "from-date", null);
        fields1.put( "to-date", "3056.06.18");

        assertFalse(caller.sendRequest(defaultURL, fields1, outputFolder+"false"+j+".json"));

        //Null parameters
        assertFalse(caller.sendRequest(defaultURL, null, outputFolder+"false"+j+".json"));

        //SECOND PART: Paths that are not correct
        fields1.clear();
        fields1.put("api-key", key);
        assertFalse(caller.sendRequest(defaultURL, fields1, "./notexists/false"+j+".json"));
        assertFalse(caller.sendRequest(defaultURL, fields1, "./not exists/false"+j+".json"));
        assertFalse(caller.sendRequest(defaultURL, fields1, ""));
        assertFalse(caller.sendRequest(defaultURL, fields1, null));

        //Try to put in the same file. It should work fine
        i++;
        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));
        assertTrue(caller.sendRequest(defaultURL, fields1, outputFolder+"true"+i+".json"));

        //THIRD PART: change the URL
        i++;
        fields1.clear();
        assertTrue(caller.sendRequest("https://api.publicapis.org/entries", fields1, outputFolder+"true"+i+".json"));

        j++;
        assertFalse(caller.sendRequest(null, fields1, outputFolder+"false"+j+".json"));

        j++;
        assertFalse(caller.sendRequest("", fields1, outputFolder+"false"+j+".json"));

    }

    @Test
    public void endRequest() {
        //There is nothing to test, except if there is any error
        caller.endRequests();
    }

    @Test
    public void equals()
    {
        KongAPICaller c = new KongAPICaller();
        assertTrue(c.equals(caller));
        assertFalse(c.equals(123));
    }
}
