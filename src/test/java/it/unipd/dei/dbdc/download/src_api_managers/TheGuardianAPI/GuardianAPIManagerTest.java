package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.DownloadHandlerTest;
import it.unipd.dei.dbdc.download.QueryParam;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GuardianAPIManagerTest {

    public static String key = "21b5c154-934c-4a4e-b2f5-64adbd68af5f";
    private static final GuardianAPIManager manager = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
    @Test
    public void getName() {
        assertEquals("TheGuardianAPI", manager.getName());
        assertEquals("test", (new GuardianAPIManager(null, "test")).getName());
    }

    @Test
    public void getFormattedParams() {
        //The function GuardianAPIInfo.getFormattedParams is tested in GuardianAPIInfo
        assertEquals(GuardianAPIInfo.getFormattedParams(), manager.getFormattedParams());
    }

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

        list.clear();
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

        //Stress test
        GuardianAPIManager stressed = new GuardianAPIManager(new KongAPICaller(), "");

        list.clear();
        list.add(new QueryParam("api-key", GuardianAPIManagerTest.key));
        list.add(new QueryParam("page-size", "200"));
        list.add(new QueryParam("q", ""));
        list.add(new QueryParam("pages", "50"));
        assertDoesNotThrow(() -> stressed.addParams(list));
        assertDoesNotThrow(() -> stressed.callAPI(DownloadHandlerTest.resources_url+"manager/stressed/"));
    }


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

}
