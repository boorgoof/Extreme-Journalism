package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import it.unipd.dei.dbdc.download.src_callers.KongAPICallerTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.fail;

public class CallAPIThreadTest {

    @Test
    public void call()
    {
        Map<String, Object> specified_fields = new HashMap<>();

        CallAPIThread c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), "thread.json", specified_fields);

        try {
            try {
                c.call();
                fail("La call ha avuto successo anche senza parametri");
            } catch (IllegalArgumentException e) {
                //Intentionally left blank
            }

            specified_fields.put("api-key", KongAPICallerTest.key);
            c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), "thread.json", specified_fields);

            try {
                c.call();
            } catch (IllegalArgumentException e) {
                fail("La call non ha avuto successo");
            }

            specified_fields.put("from-date", "2011-10-12");
            c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), "thread.json", specified_fields);

            try {
                c.call();
            } catch (IllegalArgumentException e) {
                fail("La call non ha avuto successo");
            }

            specified_fields.put("to-date", "2014-10-12");
            c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), "thread.json", specified_fields);

            try {
                c.call();
            } catch (IllegalArgumentException e) {
                fail("La call non ha avuto successo");
            }

            specified_fields.put("show-fields", "all");
            c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), "thread.json", specified_fields);

            try {
                c.call();
            } catch (IllegalArgumentException e) {
                fail("La call non ha avuto successo");
            }

            specified_fields.put("format", "xml");
            c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), "thread.json", specified_fields);

            try {
                c.call();
            } catch (IllegalArgumentException e) {
                fail("La call non ha avuto successo");
            }

            specified_fields.put("page", 2);
            c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), "thread.json", specified_fields);

            try {
                c.call();
            } catch (IllegalArgumentException e) {
                fail("La call non ha avuto successo");
            }

            specified_fields.put("page-size", 3);
            c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), "thread.json", specified_fields);

            try {
                c.call();
            } catch (IllegalArgumentException e) {
                fail("La call non ha avuto successo");
            }

            specified_fields.put("q", "\"solar energy\"");
            c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), "thread.json", specified_fields);

            try {
                c.call();
            } catch (IllegalArgumentException e) {
                fail("La call non ha avuto successo");
            }

            specified_fields.put("q", "kingdom");
            c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), "thread.json", specified_fields);

            try {
                c.call();
            } catch (IllegalArgumentException e) {
                fail("La call non ha avuto successo");
            }

            specified_fields.put("notreal", 34);
            c = new CallAPIThread(new KongAPICaller(), GuardianAPIInfo.getDefaultURL(), "thread.json", specified_fields);

            try {
                c.call();
            } catch (IllegalArgumentException e) {
                fail("La call non ha avuto successo, cioè non ha ignorato i parametri errati");
            }
        }
        catch (IOException e)
        {
            fail("Errore nell'apertura del file");
        }
    }
}
