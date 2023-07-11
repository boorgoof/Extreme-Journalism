package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.DownloadHandlerTest;
import it.unipd.dei.dbdc.download.QueryParam;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link GuardianAPIManager} by calling approximately 21 times the TheGuardianAPI.
 * This causes a certain number of requests that is possible to use in a day to be used.
 * This class is tested right after {@link it.unipd.dei.dbdc.tools.ThreadPoolTest}
 */
@Order(7)
public class GuardianAPIManagerTest {

    /**
     * The key that is used to test the TheGuardianAPI. It should only be put here by the user, as all the other
     * test classes takes this one.
     */
    public static String key = "";

    /**
     * The instance of {@link GuardianAPIManager} used for the tests.
     */
    private static final GuardianAPIManager manager = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");

    /**
     * Tests of {@link GuardianAPIManager#getName()}
     */
    @Test
    public void getName() {
        assertEquals("TheGuardianAPI", manager.getName());
        assertEquals("test", (new GuardianAPIManager(null, "test")).getName());
    }

    /**
     * Tests of {@link GuardianAPIManager#getFormattedParams()} using {@link GuardianAPIInfo#getFormattedParams()}
     * The function {@link GuardianAPIInfo#getFormattedParams()} is tested in {@link GuardianAPIInfoTest}
     */
    @Test
    public void getFormattedParams() {
        assertEquals(GuardianAPIInfo.getFormattedParams(), manager.getFormattedParams());
    }

    /**
     * Tests of {@link GuardianAPIManager#addParams(List)} with valid and invalid inputs.
     */
    @Test
    public void addParams()
    {
        //Check if it throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(null));

        List<QueryParam> list = new ArrayList<>();
        assertDoesNotThrow(() -> manager.addParams(list));

        list.add(null);
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam(null, "a"));
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("a", null));
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("ugo", "gianni"));
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("apikey", "gianni"));
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("qu", "gianni"));
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("page", "gianni"));
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("fromdate", "gianni"));
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        //Check with valid params
        list.clear();
        list.add(new QueryParam("api-key", "false"));
        list.add(new QueryParam("from-date", "2011-10-12"));
        list.add(new QueryParam("to-date", "2014-10-12"));
        list.add(new QueryParam("pages", "13"));
        list.add(new QueryParam("page-size", "34"));
        list.add(new QueryParam("q", "\"solar energy\""));
        list.add(new QueryParam("order-by", "newest"));

        assertDoesNotThrow(() -> manager.addParams(list));

        //Arguments that are not legal, but have a right key
        list.clear();
        list.add(new QueryParam("api-key", null));
        assertThrows(IllegalArgumentException.class ,() -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("pages", "-2"));
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("page-size", "notNumber"));
        assertThrows(IllegalArgumentException.class ,() -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("pages", "notNumber"));
        assertThrows(IllegalArgumentException.class ,() -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("from-date", "2011.10.12"));
        assertThrows(IllegalArgumentException.class ,() -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("to-date", "2014/10/12"));
        assertThrows(IllegalArgumentException.class ,() -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("q", null));
        assertThrows(IllegalArgumentException.class ,() -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("order-by", "notpresent"));
        assertDoesNotThrow(() -> manager.addParams(list));

        //These are default parameters, that should not be possible to add
        list.clear();
        list.add(new QueryParam("show-fields", "all"));
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("format", "xml"));
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));
    }

    /**
     * Tests of {@link GuardianAPIManager#callAPI(String)}. It calls the TheGuardianAPI approximately 72 times,
     * as it also implements a stress test.
     */
    @Test
    public void callAPI() {
        List<QueryParam> list = new ArrayList<>();
        GuardianAPIManager man = new GuardianAPIManager(new KongAPICaller(), "");

        //Test with only the api-key
        list.add(new QueryParam("api-key", GuardianAPIManagerTest.key));
        man.addParams(list);
        assertDoesNotThrow(() -> man.callAPI(DownloadHandlerTest.resources_url+"manager/"));

        //With a path that is not valid
        assertThrows(IllegalArgumentException.class, () -> man.callAPI("./notexists/"));

        //Without the caller
        GuardianAPIManager other = new GuardianAPIManager(null, "");
        assertThrows(IllegalArgumentException.class, () -> other.callAPI("./output/"));

        //Tests with all valid parameters
        list.clear();
        list.add(new QueryParam("from-date", "2011-10-12"));
        list.add(new QueryParam("to-date", "2014-10-12"));
        list.add(new QueryParam("pages", "2"));
        list.add(new QueryParam("page-size", "34"));
        list.add(new QueryParam("q", "\"solar energy\""));
        list.add(new QueryParam("order-by", "newest"));
        assertDoesNotThrow(() -> man.addParams(list));
        assertDoesNotThrow(() -> man.callAPI(DownloadHandlerTest.resources_url+"manager/"));

        //Change one parameter at a time
        list.clear();
        list.add(new QueryParam("from-date", "1012-10-01"));
        assertDoesNotThrow(() -> man.addParams(list));
        assertDoesNotThrow(() -> man.callAPI(DownloadHandlerTest.resources_url+"manager/"));

        list.clear();
        list.add(new QueryParam("to-date", "3067-02-28"));
        assertDoesNotThrow(() -> man.addParams(list));
        assertDoesNotThrow(() -> man.callAPI(DownloadHandlerTest.resources_url+"manager/"));

        list.clear();
        list.add(new QueryParam("pages", "7"));
        assertDoesNotThrow(() -> man.addParams(list));
        assertDoesNotThrow(() -> man.callAPI(DownloadHandlerTest.resources_url+"manager/"));

        //We also change pages to diminish the number of calls made
        list.clear();
        list.add(new QueryParam("pages", "1"));
        list.add(new QueryParam("page-size", "143"));
        assertDoesNotThrow(() -> man.addParams(list));
        assertDoesNotThrow(() -> man.callAPI(DownloadHandlerTest.resources_url+"manager/"));

        list.clear();
        list.add(new QueryParam("q", "kingdom"));
        assertDoesNotThrow(() -> man.addParams(list));
        assertDoesNotThrow(() -> man.callAPI(DownloadHandlerTest.resources_url+"manager/"));

        list.clear();
        list.add(new QueryParam("order-by", "oldest"));
        assertDoesNotThrow(() -> man.addParams(list));
        assertDoesNotThrow(() -> man.callAPI(DownloadHandlerTest.resources_url+"manager/"));

        //Stress test.
        GuardianAPIManager stressed = new GuardianAPIManager(new KongAPICaller(), "");

        list.clear();
        list.add(new QueryParam("api-key", GuardianAPIManagerTest.key));
        list.add(new QueryParam("page-size", "200"));
        list.add(new QueryParam("q", ""));
        list.add(new QueryParam("pages", "50"));
        assertDoesNotThrow(() -> stressed.addParams(list));
        assertDoesNotThrow(() -> stressed.callAPI(DownloadHandlerTest.resources_url+"manager/stressed/"));
    }



    /**
     * Tests of {@link GuardianAPIManager#copy()} adding various parameters.
     */
    @Test
    public void copy()
    {
        GuardianAPIManager g = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        assertEquals(g, g.copy());

        //With parameters
        GuardianAPIManager g2 = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        List<QueryParam> list = new ArrayList<>();

        list.add(new QueryParam("from-date", "2200-11-23"));
        g2.addParams(list);
        assertNotEquals(g2, g.copy());
        assertEquals(g2, g2.copy());

        list.clear();
        list.add(new QueryParam("to-date", "3067-02-28"));
        assertDoesNotThrow(() -> g2.addParams(list));
        assertEquals(g2, g2.copy());

        list.clear();
        list.add(new QueryParam("pages", "7"));
        assertDoesNotThrow(() -> g2.addParams(list));
        assertEquals(g2, g2.copy());

        list.clear();
        list.add(new QueryParam("page-size", "143"));
        assertDoesNotThrow(() -> g2.addParams(list));
        assertEquals(g2, g2.copy());

        list.clear();
        list.add(new QueryParam("q", "kingdom"));
        assertDoesNotThrow(() -> g2.addParams(list));
        assertEquals(g2, g2.copy());

        list.clear();
        list.add(new QueryParam("order-by", "oldest"));
        assertDoesNotThrow(() -> g2.addParams(list));
        assertEquals(g2, g2.copy());

        list.clear();
        list.add(new QueryParam("api-key", GuardianAPIManagerTest.key));
        assertDoesNotThrow(() -> g2.addParams(list));
        assertEquals(g2, g2.copy());

        GuardianAPIManager g3 = (GuardianAPIManager) g2.copy();

        //With invalid parameters
        list.clear();
        list.add(new QueryParam("api-key", null));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertEquals(g3, g2.copy());

        list.clear();
        list.add(null);
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertEquals(g3, g2.copy());

        list.clear();
        list.add(new QueryParam(null, "null"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertEquals(g3, g2.copy());

        list.clear();
        list.add(new QueryParam("pages", "-3"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertEquals(g3, g2.copy());

        list.clear();
        list.add(new QueryParam("page-size", "201"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertEquals(g3, g2.copy());

        list.clear();
        list.add(new QueryParam("from-date", "1234-23-22"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertEquals(g3, g2.copy());

        list.clear();
        list.add(new QueryParam("from-date", "1900-02-29"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertEquals(g3, g2.copy());

        list.clear();
        list.add(new QueryParam("from-date", "2001-02-29"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertEquals(g3, g2.copy());

        list.clear();
        list.add(new QueryParam("to-date", "1934.23.22"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertEquals(g3, g2.copy());
    }

    /**
     * Tests of {@link GuardianAPIManager#equals(Object)} adding various parameters.
     */
    @Test
    public void equals()
    {
        //It is also tested in copy()
        GuardianAPIManager g = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        assertTrue(g.equals(g));

        GuardianAPIManager g2 = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");

        List<QueryParam> list = new ArrayList<>();
        list.add(new QueryParam("from-date", "2200-11-23"));
        g2.addParams(list);
        assertFalse(g.equals(g2));

        g.addParams(list);

        //With an invalid parameter
        list.clear();
        list.add(new QueryParam("from-date", "1934.23.22"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertTrue(g.equals(g2));

        list.clear();
        list.add(new QueryParam("pages", "-3"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertTrue(g.equals(g2));

        list.clear();
        list.add(new QueryParam("page-size", "230"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertTrue(g.equals(g2));

        list.clear();
        list.add(new QueryParam("to-date", "2001-02-29"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertTrue(g.equals(g2));

        list.clear();
        list.add(new QueryParam("fromdate", "1934-02-22"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertTrue(g.equals(g2));

        list.clear();
        list.add(new QueryParam(null, "1934.23.22"));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertTrue(g.equals(g2));

        list.clear();
        list.add(new QueryParam("from-date", null));
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertTrue(g.equals(g2));

        list.clear();
        list.add(null);
        assertThrows(IllegalArgumentException.class,() -> g2.addParams(list));
        assertTrue(g.equals(g2));

        //With valid parameters
        list.clear();
        list.add(new QueryParam("to-date", "1934-02-22"));
        assertDoesNotThrow(() -> g2.addParams(list));
        assertFalse(g.equals(g2));

        GuardianAPIManager finalG = g;
        assertDoesNotThrow(() -> finalG.addParams(list));
        assertTrue(finalG.equals(g2));

        list.clear();
        list.add(new QueryParam("api-key", "13"));
        assertDoesNotThrow(() -> g2.addParams(list));
        assertFalse(finalG.equals(g2));

        assertDoesNotThrow(() -> finalG.addParams(list));
        assertTrue(finalG.equals(g2));

        //With invalid objects
        assertFalse(g.equals(23));
        assertFalse(g.equals(null));

        //With managers initialized with null
        assertFalse(g.equals(new GuardianAPIManager(null, null)));

        g = new GuardianAPIManager(null, "");
        assertFalse(g.equals(new GuardianAPIManager(null, null)));
        assertTrue(g.equals(new GuardianAPIManager(null, "")));

        g = new GuardianAPIManager(new KongAPICaller(), null);
        assertFalse(g.equals(new GuardianAPIManager(null, null)));
        assertFalse(g.equals(new GuardianAPIManager(new KongAPICaller(), "")));
        assertTrue(g.equals(new GuardianAPIManager(new KongAPICaller(), null)));

        g = new GuardianAPIManager(new KongAPICaller(), "");
        assertFalse(g.equals(new GuardianAPIManager(null, "")));
        assertFalse(g.equals(new GuardianAPIManager(new KongAPICaller(), null)));
        assertTrue(g.equals(new GuardianAPIManager(new KongAPICaller(), "")));
    }

    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created.
     */
    private GuardianAPIManagerTest() {}

}
