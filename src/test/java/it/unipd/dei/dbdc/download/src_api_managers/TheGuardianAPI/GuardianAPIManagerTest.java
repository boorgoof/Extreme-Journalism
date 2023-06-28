package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.QueryParam;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import it.unipd.dei.dbdc.download.src_callers.KongAPICallerTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GuardianAPIManagerTest {

    //TODO: RIVEDI E METTI BENE I TESTS

    public static String key = "21b5c154-934c-4a4e-b2f5-64adbd68af5f";

    private static final GuardianAPIManager manager = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");

    @Test
    public void getName() {
        assertEquals(manager.getName(), "TheGuardianAPI");
    }

    @Test
    public void getFormattedParams() {
        assertEquals(GuardianAPIInfo.getFormattedParams(), manager.getFormattedParams());
    }

    @Test
    public void addParams()
    {
        //Check if it throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(null));

        List<QueryParam> list = new ArrayList<>();
        assertDoesNotThrow(() -> manager.addParams(list));

        list.add(new QueryParam("ugo", "gianni"));
        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        list.clear();
        list.add(new QueryParam("api-key", "false"));
        list.add(new QueryParam("from-date", "2011-10-12"));
        list.add(new QueryParam("to-date", "2014-10-12"));
        list.add(new QueryParam("pages", "13"));
        list.add(new QueryParam("page-size", "34"));
        list.add(new QueryParam("q", "\"solar energy\""));
        list.add(new QueryParam("order-by", "newest"));

        assertDoesNotThrow(() -> manager.addParams(list));

        list.add(new QueryParam("show-fields", "all"));
        list.add(new QueryParam("format", "xml"));

        assertThrows(IllegalArgumentException.class, () -> manager.addParams(list));

        //TODO: check altro, tipo se li aggiunge giusti
    }

    @Test
    public void callAPI() {

        List<QueryParam> list = new ArrayList<>();
        GuardianAPIManager man = new GuardianAPIManager(new KongAPICaller(), "");

        list.add(new QueryParam("api-key", GuardianAPIManagerTest.key));
        man.addParams(list);

        assertDoesNotThrow(() -> man.callAPI("./database/"));

        GuardianAPIManager other = new GuardianAPIManager(null, null);

        assertThrows(IllegalArgumentException.class, () ->other.callAPI("./output/"));

        assertThrows(IllegalArgumentException.class, () ->other.callAPI("./nonesiste/"));

        //TODO: test con altri parametri
    }


    @Test
    public void copy()
    {
        GuardianAPIManager g = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        assertEquals(g, g.copy());
        GuardianAPIManager g2 = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        List<QueryParam> list = new ArrayList<>();
        list.add(new QueryParam("from-date", "2200-11-23"));
        g2.addParams(list);
        assertNotEquals(g2, g.copy());
    }

    @Test
    public void equals()
    {
        GuardianAPIManager g = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        assertTrue(g.equals(g));

        GuardianAPIManager g2 = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        List<QueryParam> list = new ArrayList<>();
        list.add(new QueryParam("from-date", "2200-11-23"));
        g2.addParams(list);
        assertFalse(g.equals(g2));

        assertFalse(g.equals(23));
    }

}
