package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManager;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIParams;
import it.unipd.dei.dbdc.download.src_callers.KongAPICallerTest;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@Disabled
public class APIPropertiesTest {

    private final static String resources_url = "./src/test/resources/download/";
    @Test
    public void readAPIProperties()
    {
        APIManager manager;

        Field params = null;
        try {
            params = GuardianAPIManager.class.getDeclaredField("params");
            params.setAccessible(true);
        } catch (NoSuchFieldException e) {
            fail("Reflection non avvenuta in maniera corretta");
        }

        Map<String, Object> list = new HashMap<>();
        list.put("api-key", KongAPICallerTest.key);
        list.put("from-date", "1904-12-12");
        list.put("to-date", "2001-12-04");
        list.put("page-size", 134);
        list.put("q", "\"solar energy\"");
        list.put("order-by", "newest");
        list.put("format", "json");
        list.put("show-fields", "bodyText,headline");

        ArrayList<Map<String, Object>> expected = new ArrayList<>(3);
        for (int i = 0; i<3; i++)
        {
            Map<String, Object> a = new HashMap<>(list);
            a.put("page", (i+1));
            expected.add(a);
        }

        try {
            manager = APIProperties.readAPIProperties(resources_url+"trueApi.properties");
            GuardianAPIParams par = (GuardianAPIParams) params.get(manager);
            ArrayList<Map<String, Object>> parameters = par.getParams();
            assertEquals(parameters, expected);
        } catch (IOException e) {
            fail("Properties corrette non lette in maniera corretta");
        } catch (IllegalAccessException e) {
            fail("Reflection non eseguita in maniera corretta");
        }

        try {
            APIProperties.readAPIProperties(resources_url+"falseApi.properties");
            fail("Properties non corrette lette come se fossero corrette");

        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }
        catch (IOException e) {
            fail("Properties file non trovato");
        }

        try {
            APIProperties.readAPIProperties(resources_url+"falseApi2.properties");
            fail("Properties non corrette lette come se fossero corrette");
        } catch (IllegalArgumentException e) {
            //Intentionally left blank
        }
        catch (IOException e)
        {
            fail("Properties file non trovato");
        }

        try {
            APIProperties.readAPIProperties(resources_url+"nonesisto.properties");
        } catch (IllegalArgumentException | IOException e) {
            fail("Properties di default non lette correttamente");
        }

    }
}
