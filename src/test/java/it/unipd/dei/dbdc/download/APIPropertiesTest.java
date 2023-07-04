package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.download.src_api_managers.TestManager.TestManager;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManager;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManagerTest;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIParams;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class APIPropertiesTest {

    @Test
    public void readAPIProperties()
    {
        APIManager manager;

        //Set accessible the params of the GuardianAPIManager
        Field params = null;
        try {
            params = GuardianAPIManager.class.getDeclaredField("params");
            params.setAccessible(true);
        } catch (NoSuchFieldException e) {
            fail("Error during the reflection: check the source code");
        }

        //Test with everything right
        Map<String, Object> list = new HashMap<>();
        list.put("api-key", GuardianAPIManagerTest.key);
        list.put("from-date", "1904-12-12");
        list.put("to-date", "2001-12-04");
        list.put("page-size", 11);
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

        //Takes the manager returned and reads its parameters. We check with two different files
        try {
            //The container is initialized with trueDownload.properties by all the test classes that use it
            manager = APIProperties.readProperties(DownloadHandlerTest.resources_url+"trueApiTest.properties", DownloadHandlerTest.resources_url + "trueDownload.properties");
            TestManager t = (TestManager) manager;
            GuardianAPIParams par = t.params;
            ArrayList<Map<String, Object>> parameters = par.getParams();
            assertEquals(expected, parameters);

            manager = APIProperties.readProperties(DownloadHandlerTest.resources_url+"trueApi.properties", null);
            par = (GuardianAPIParams) params.get(manager);
            parameters = par.getParams();
            assertEquals(expected, parameters);
        } catch (IOException e) {
            fail("Error in the reading of the properties");
        } catch (IllegalAccessException e) {
            fail("Error during the reflection: check the source code");
        }

        //Tests with false api properties
        assertThrows(IllegalArgumentException.class, () -> APIProperties.readProperties(DownloadHandlerTest.resources_url + "falseApi.properties", null));
        assertThrows(IllegalArgumentException.class, () -> APIProperties.readProperties(DownloadHandlerTest.resources_url + "falseApi2.properties", null));
        assertThrows(IllegalArgumentException.class, () -> APIProperties.readProperties(DownloadHandlerTest.resources_url + "falseApi3.properties", null));

        //Test with not existent api properties
        assertThrows(IOException.class, () -> APIProperties.readProperties(DownloadHandlerTest.resources_url + "NotExistent.properties", null));

        //Test with null
        assertDoesNotThrow(() -> assertNull(APIProperties.readProperties(null, null)));

        //Test with other true download properties
        assertDoesNotThrow(() -> APIProperties.readProperties(DownloadHandlerTest.resources_url + "trueAPI.properties", DownloadHandlerTest.resources_url + "defaultDownload.properties"));

        params.setAccessible(false);
    }
}
