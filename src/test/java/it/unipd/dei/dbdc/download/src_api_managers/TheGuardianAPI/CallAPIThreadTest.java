package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.DownloadHandlerTest;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CallAPIThreadTest {

    private static final KongAPICaller caller = new KongAPICaller();
    private static final String key = GuardianAPIManagerTest.key;
    @Test
    public void run()
    {
        //Test without parameters
        Map<String, Object> specified_fields = new HashMap<>();
        CallAPIThread c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        //Test with every parameter, added one at time
        specified_fields.put("api-key", key);
        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        //Test with date
        specified_fields.put("from-date", "2011-10-12");
        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("to-date", "2014-10-12");
        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("show-fields", "all");
        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("format", "xml");
        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("page", 2);
        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("page-size", 3);
        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("q", "\"solar energy\"");
        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("q", "kingdom");
        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        //Try to put an invalid field. This should not throw an exception, but only ignore it
        specified_fields.put("notreal", 34);
        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        //Invalid default url
        c = new CallAPIThread(caller, "invalidurl", DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        c = new CallAPIThread(caller, null, DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        c = new CallAPIThread(caller, "", DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        //Invalid path
        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"notExistent/"+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), "", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), null, specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        c = new CallAPIThread(caller, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.xml", specified_fields);
        assertDoesNotThrow(c::run);

        //Tries with null caller
        c = new CallAPIThread(null, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        //Invalid parameters
        specified_fields.clear();
        specified_fields.put( "api-key", key);
        specified_fields.put( "page-size", 245);
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        specified_fields.clear();
        specified_fields.put( "api-key", key);
        specified_fields.put( "page-size", -34);
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        specified_fields.clear();
        specified_fields.put( "api-key", key);
        specified_fields.put( "page-size", 100);
        specified_fields.put( "page", 20);
        specified_fields.put( "q", "\"solar energy\"");
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        specified_fields.clear();
        specified_fields.put( "api-key", key);
        specified_fields.put( "page-size", 100);
        specified_fields.put( "page", -1);
        specified_fields.put( "q", "\"solar energy\"");
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        specified_fields.clear();
        specified_fields.put( "api-key", key);
        specified_fields.put( "order-by", "ugo");
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        specified_fields.clear();
        specified_fields.put( "api-key", key);
        specified_fields.put( "to-date", "3056.06.18");
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);
    }
}
