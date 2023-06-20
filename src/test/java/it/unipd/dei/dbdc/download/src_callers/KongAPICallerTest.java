package it.unipd.dei.dbdc.download.src_callers;

import it.unipd.dei.dbdc.download.QueryParam;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.IOException;
import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class KongAPICallerTest {

    private static KongAPICaller caller;

    public static String key = "21b5c154-934c-4a4e-b2f5-64adbd68af5f"; //todo: prendila da altro posto comune

    private final static String defaultURL = "https://content.guardianapis.com/search?";
    private final static String outputFolder = "./output/";
    private final static Map<String, Object> fields1 = new TreeMap<>();

    @BeforeAll
    public static void initializeCaller()
    {
        caller = new KongAPICaller();
        assertNotNull(caller);
    }

    @Order(1)
    @Test
    public void sendRequest() throws IOException {
        //Di volta in volta cambiamo solo un parametro alla volta. Usiamo theGuardianApi per fare i test per comodità

        //TRUE TESTS
        int i = 0;
        //FALSE TESTS
        int j = 0;

        // CAMBIO PAGE-SIZE

        fields1.put( "api-key", key);
        fields1.put( "page-size", "200");
        fields1.put( "page", "5");
        fields1.put( "q", "solar energy");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "1");
        fields1.put( "page", "5");
        fields1.put( "q", "solar energy");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "130");
        fields1.put( "page", "5");
        fields1.put( "q", "solar energy");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "245");
        fields1.put( "page", "5");
        fields1.put( "q", "solar energy");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertFalse(caller.sendRequest(defaultURL, fields1, "false"+j+".json"));

        j++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "-34");
        fields1.put( "page", "5");
        fields1.put( "q", "solar energy");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertFalse(caller.sendRequest(defaultURL, fields1, "false"+j+".json"));

        //CAMBIO PAGE
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "1");
        fields1.put( "q", "solar energy");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "100");
        fields1.put( "q", "solar energy");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertFalse(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "34"); //TODO: vedere quante pagine sono con questi parametri e mettere l'ultima
        fields1.put( "q", "solar energy");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        //CAMBIO Q
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "banana");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "properties of the kingdom");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        j++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "tistestisfalse");
        fields1.put( "order-by", "newest");
        fields1.put( "from-date", "2001-12-23");
        fields1.put( "to-date", "2002-12-23");

        assertFalse(caller.sendRequest(defaultURL, fields1, "false"+j+".json"));

        //CAMBIO ORDER-BY
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "oldest");
        fields1.put( "from-date", "1902-01-03"); //TODO: la data come funziona?
        fields1.put( "to-date", "2023-06-18");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "relevance");
        fields1.put( "from-date", "1902-01-03"); //TODO: la data come funziona?
        fields1.put( "to-date", "2023-06-18");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        j++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "ugo");
        fields1.put( "from-date", "1902-01-03"); //TODO: la data come funziona?
        fields1.put( "to-date", "2023-06-18");

        assertTrue(caller.sendRequest(defaultURL, fields1, "false"+j+".json"));

        //CAMBIO DATE
        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "oldest");
        fields1.put( "from-date", "1789-01-03"); //TODO: la data come funziona?
        fields1.put( "to-date", "2000-06-18");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        i++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "oldest");
        fields1.put( "from-date", "1012-01-03"); //TODO: la data come funziona?
        fields1.put( "to-date", "2021-06-18");

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        j++;
        fields1.clear();
        fields1.put( "api-key", key);
        fields1.put( "page-size", "100");
        fields1.put( "page", "5");
        fields1.put( "q", "kingdom");
        fields1.put( "order-by", "oldest");
        fields1.put( "from-date", "1902-01-03"); //TODO: la data come funziona?
        fields1.put( "to-date", "3056-06-18");

        assertFalse(caller.sendRequest(defaultURL, fields1, "false"+j+".json"));

        //CHIAMATA DI DEFAULT:
        i++;
        fields1.clear();
        fields1.put( "api-key", key);

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

        //CHIAMATA CON PATH NON CORRETTO
        j++;
        fields1.clear();
        fields1.put( "api-key", key);

        assertFalse(caller.sendRequest(defaultURL, fields1, "./non esisto/false"+j+".json"));

        j++;
        assertFalse(caller.sendRequest(defaultURL, fields1, "./nonesisto/false"+j+".json"));

        //CHIAMATA CON URL NON CORRETTO:
        j++;
        assertFalse(caller.sendRequest("iniziononvalido"+defaultURL, fields1, "false"+j+".json"));


        //TODO: fare con altre api

        assertTrue(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));
        assertFalse(caller.sendRequest(defaultURL, fields1, "true"+i+".json"));

    }

    @Order(2)
    @Test
    public void endRequest() throws IOException {

        fields1.put( "api-key", key);
        assertTrue(caller.sendRequest(defaultURL, fields1, "myrequest.json"));

        caller.endRequests();
        assertFalse(caller.sendRequest(defaultURL, fields1, "myrequest.json"));

        caller = new KongAPICaller();
        assertTrue(caller.sendRequest(defaultURL, fields1, "myrequest.json"));
    }
}
