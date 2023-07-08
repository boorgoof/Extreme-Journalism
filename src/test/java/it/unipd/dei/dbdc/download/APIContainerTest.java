package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.src_api_managers.TestManager.TestManager;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManager;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link APIContainer}.
 * As this class is one of the first to be processed, the {@link APIContainer} has not already been initialized, so
 * it can throw {@link java.io.IOException}
 */
@Order(1)
public class APIContainerTest {

    /**
    * The instance of the {@link APIContainer} that will be used.
    */
    private static APIContainer container = null;

    /**
     * Initializes the {@link APIContainer} instance used by all the other tests and classes.
     * It uses the trueDownload.properties file.
     * It is the only function where passing an invalid properties file causes an {@link IOException} to be thrown.
     */
    @BeforeAll
    public static void getInstance()
    {
        assertThrows(IOException.class, () -> APIContainer.getInstance(DownloadHandlerTest.resources_url+"falseDownload.properties"));
        try {
            //The container is initialized with trueDownload.properties by this class, so all the other classes will have this instance.
            //Tests if every instance returned is the same
            container = APIContainer.getInstance(DownloadHandlerTest.resources_url+"trueDownload.properties");
            assertEquals(container, APIContainer.getInstance(null));
            assertEquals(container, APIContainer.getInstance(DownloadHandlerTest.resources_url+"falseDownload.properties"));
        } catch (IOException e) {
            fail("Error in the instantiation of the container");
        }
    }

    /**
     * Tests the {@link APIContainer#getAPINames()}
     */
    @Test
    public void getAPINames()
    {
        String names = "Test\nTheGuardianAPI\n";
        assertEquals(names, container.getAPINames());
    }

    /**
     * Tests the {@link APIContainer#getAPIPossibleParams(String)} with the {@link GuardianAPIManager#getFormattedParams()}
     * and {@link TestManager#getFormattedParams()}.
     */
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

    /**
     * Tests the {@link APIContainer#getAPIManager(String, List)} with valid and invalid inputs.
     */
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

    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created.
     */
    private APIContainerTest() {}
}
