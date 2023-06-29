package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.src_api_managers.TestManager.TestManager;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManager;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class APIContainerTest {

    public static APIContainer container = null;
    @BeforeAll
    public static void getInstance()
    {
        try {
            //Tests if every instance returned is the same
            container = APIContainer.getInstance(DownloadHandlerTest.resources_url+"trueDownload.properties");
            assertEquals(container, APIContainer.getInstance(null));
            assertEquals(container, APIContainer.getInstance(DownloadHandlerTest.resources_url+"falseDownload.properties"));
        } catch (IOException e) {
            fail("Error in the instantiation of the container");
        }
    }

    @Test
    public void getAPINames()
    {
        String names = "Test\nTheGuardianAPI\n";
        assertEquals(names, container.getAPINames());
    }

    @Test
    public void getAPIPossibleParams()
    {
        GuardianAPIManager g = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        String expected = g.getFormattedParams();
        assertEquals(expected, container.getAPIPossibleParams(g.getName()));

        TestManager t = new TestManager(new KongAPICaller(), "Test");
        expected = t.getFormattedParams();
        assertEquals(expected, container.getAPIPossibleParams(t.getName()));
    }

    @Test
    public void getAPIManager()
    {
        //Test with everything right
        GuardianAPIManager g = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        List<QueryParam> queries = new ArrayList<>();
        assertEquals(g, container.getAPIManager(g.getName(), queries));

        //With a parameter
        queries.add(new QueryParam("api-key", "ugo"));
        g.addParams(queries);
        assertEquals(g, container.getAPIManager(g.getName(), queries));

        queries.add(new QueryParam("page-size", "3"));
        g.addParams(queries);
        assertEquals(g, container.getAPIManager(g.getName(), queries));

        queries.add(new QueryParam("pages", "4"));
        g.addParams(queries);
        assertEquals(g, container.getAPIManager(g.getName(), queries));

        queries.add(new QueryParam("from-date", "2303-02-23"));
        g.addParams(queries);
        assertEquals(g, container.getAPIManager(g.getName(), queries));

        queries.add(new QueryParam("to-date", "1203-03-21"));
        g.addParams(queries);
        assertEquals(g, container.getAPIManager(g.getName(), queries));

        //Checks if the APIManager returned is always the same or if it changes
        queries.clear();
        assertNotEquals(g, container.getAPIManager(g.getName(), queries));
        g = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        assertEquals(g,container.getAPIManager(g.getName(), queries));

        //Pass invalid parameters
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", null));

        queries.clear();
        queries.add(new QueryParam("to-date", "1203.03-21"));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam("from-date", "120-03-21"));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam("pages", "-1200"));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam("page-size", "203"));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam("page-size", "aaa"));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam("page-size", "-2"));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam("page-size", "aaa"));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam("pages", "aaa"));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(null);
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam(null, "aaa"));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam("page-size", null));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam(null, null));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam("from-date", null));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        queries.clear();
        queries.add(new QueryParam("api-key", null));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", queries));

        //Pass invalid names
        queries.clear();
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("NotExistent", queries));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager(null, queries));
    }
}
