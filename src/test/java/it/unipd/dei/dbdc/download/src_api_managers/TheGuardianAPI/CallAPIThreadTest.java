package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.DownloadHandlerTest;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import it.unipd.dei.dbdc.download.src_callers.KongAPICallerTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class CallAPIThreadTest {

    @Test
    public void run()
    {
        //Test without parameters
        Map<String, Object> specified_fields = new HashMap<>();
        CallAPIThread c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        //Test with every parameter, added one at time
        specified_fields.put("api-key", KongAPICallerTest.key);
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        //Test with date
        specified_fields.put("from-date", "2011-10-12");
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("to-date", "2014-10-12");
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("show-fields", "all");
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("format", "xml");
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("page", 2);
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("page-size", 3);
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("q", "\"solar energy\"");
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        specified_fields.put("q", "kingdom");
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        //Try to put a not valid field. This should not throw an exception, but only ignore it
        specified_fields.put("notreal", 34);
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertDoesNotThrow(c::run);

        //Invalid default url
        c = new CallAPIThread(new KongAPICaller(), "invalidurl", DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        //Invalid path
        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"notExistent/"+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        //Tries with null
        c = new CallAPIThread(null, GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        c = new CallAPIThread(new KongAPICaller(), null, DownloadHandlerTest.resources_url+"thread.json", specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), null, specified_fields);
        assertThrows(IllegalArgumentException.class, c::run);

        c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), DownloadHandlerTest.resources_url+"thread.json", null);
        assertThrows(IllegalArgumentException.class, c::run);
    }
}
