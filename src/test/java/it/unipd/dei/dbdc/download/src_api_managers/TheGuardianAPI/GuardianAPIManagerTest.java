package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.QueryParam;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import it.unipd.dei.dbdc.download.src_callers.KongAPICallerTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class GuardianAPIManagerTest {

    private static GuardianAPIManager manager = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");

    @Test
    public void getName() {
        assertEquals(manager.getName(), "TheGuardianAPI");
    }

    @Test
    public void getParams() {
        assertEquals(GuardianAPIInfo.getFormattedParams(), manager.getParams());
    }

    @Test
    public void addParams()
    {
        List<QueryParam> list = new ArrayList<>();
        try
        {
            manager.addParams(null);
            fail("Added null params");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

        manager.addParams(list);

        list.add(new QueryParam("ugo", "gianni"));

        try {
            manager.addParams(list);
            fail("Added not correct params");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

        list.clear();
        list.add(new QueryParam("api-key", "false"));
        list.add(new QueryParam("from-date", "2011-10-12"));
        list.add(new QueryParam("to-date", "2014-10-12"));
        list.add(new QueryParam("pages", "13"));
        list.add(new QueryParam("page-size", "34"));
        list.add(new QueryParam("q", "\"solar energy\""));
        list.add(new QueryParam("order-by", "newest"));

        manager.addParams(list);

        list.add(new QueryParam("show-fields", "all"));
        list.add(new QueryParam("format", "xml"));
        try {
            manager.addParams(list);
            fail("Added not correct params");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

    }

    @Test
    public void callAPI() {

        List<QueryParam> list = new ArrayList<>();
        GuardianAPIManager man = new GuardianAPIManager(new KongAPICaller(), "");

        list.add(new QueryParam("api-key", KongAPICallerTest.key));
        man.addParams(list);

        try {
            man.callAPI("./database/");
        }
        catch (IOException | IllegalArgumentException e)
        {
            fail();
        }

        GuardianAPIManager other = new GuardianAPIManager(null, null);

        try {
            other.callAPI("./output/");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }
        catch (IOException e)
        {
            fail();
        }

        try {
            man.callAPI("./nonesiste/");
            fail();
        }
        catch (IllegalArgumentException e)
        {
            fail();
        }
        catch (IOException e)
        {
            //Intentionally left blank
        }

        //TODO: test con altri parametri
    }


}
