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
        queries.add(new QueryParam("api-key", "ugo"));
        g.addParams(queries);
        assertEquals(g, container.getAPIManager(g.getName(), queries));

        //Checks if the APIManager returned is always the same or if it changes
        queries.clear();
        assertNotEquals(g, container.getAPIManager(g.getName(), queries));
        g = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        assertEquals(g,container.getAPIManager(g.getName(), queries));

        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("TheGuardianAPI", null));
        assertThrows(IllegalArgumentException.class, () -> container.getAPIManager("NotExistent", queries));
        //TODO: crea altri test
    }
}
