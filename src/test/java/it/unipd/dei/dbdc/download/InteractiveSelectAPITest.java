package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManager;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class InteractiveSelectAPITest {

    @Order(1)
    @Test
    public void initialize()
    {
        //The container is initialized with trueDownload.properties by all the test classes that use it
        assertDoesNotThrow(() -> new InteractiveSelectAPI(null, DownloadHandlerTest.resources_url+"trueDownload.properties"));
        assertDoesNotThrow(() -> new InteractiveSelectAPI(null, null));

        assertDoesNotThrow(() -> new InteractiveSelectAPI(null, DownloadHandlerTest.resources_url+"falseDownload.properties"));
        assertDoesNotThrow(() -> new InteractiveSelectAPI(null, DownloadHandlerTest.resources_url+"falseDownload2.properties"));
    }
    @Order(2)
    @Test
    public void aksAPIName()
    {
        assertDoesNotThrow(() -> {
            final Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\napi-key ugo\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            assertEquals("TheGuardianAPI", finalTest.askAPIName());
        });

        assertDoesNotThrow(() -> {
            final Scanner sc = new Scanner("TI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\napi-key ugo\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            assertEquals("TI", finalTest.askAPIName());
        });

        assertDoesNotThrow(() -> {
            final Scanner sc = new Scanner("Test\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\napi-key ugo\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            assertEquals("Test", finalTest.askAPIName());
        });
    }

    @Order(3)
    @Test
    public void askParams()
    {
        assertDoesNotThrow(() -> new InteractiveSelectAPI(null, DownloadHandlerTest.resources_url+"trueDownload.properties"));

        //Test with everything right
        assertDoesNotThrow(() -> {
            GuardianAPIManager expected = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
            ArrayList<QueryParam> list = new ArrayList<>();
            list.add(new QueryParam("q", "kingdom"));
            list.add(new QueryParam("api-key", "ugo"));
            list.add(new QueryParam("from-date", "2003-12-23"));
            list.add(new QueryParam("to-date", "2002-12-23"));
            list.add(new QueryParam("pages", "12"));
            list.add(new QueryParam("page-size", "120"));
            expected.addParams(list);

            final Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\napi-key ugo\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertEquals(expected, obtained);
        });

        //Test with an invalid entry
        assertDoesNotThrow(() -> {
            final Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\ninvalid key\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertNull(obtained);
        });

        //Test with an invalid name
        assertDoesNotThrow(() -> {
            final Scanner sc = new Scanner("TheGuardi\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\nanother entry\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertNull(obtained);
        });

        //Test with an entry with a valid key and invalid value
        assertDoesNotThrow(() -> {
            final Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003.12.23\nto-date 2002-12-23\npages 12\npage-size 120\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertNull(obtained);
        });

        assertDoesNotThrow(() -> {
            final Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages -1\npage-size 120\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertNull(obtained);
        });

        assertDoesNotThrow(() -> {
            final Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 201\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertNull(obtained);
        });

        assertDoesNotThrow(() -> {
            final Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date aaaaa\npages 12\npage-size 120\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertNull(obtained);
        });

        assertDoesNotThrow(() -> {
            final Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages aaa\npage-size 120\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertNull(obtained);
        });

        assertDoesNotThrow(() -> {
            final Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size aaaa\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertNull(obtained);
        });

        //Test with a line with a key without value
        assertDoesNotThrow(() -> {
            GuardianAPIManager expected = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
            ArrayList<QueryParam> list = new ArrayList<>();
            list.add(new QueryParam("q", "kingdom"));
            list.add(new QueryParam("from-date", "2003-12-23"));
            list.add(new QueryParam("to-date", "2002-12-23"));
            list.add(new QueryParam("pages", "12"));
            list.add(new QueryParam("page-size", "120"));
            expected.addParams(list);

            final Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\napi-key\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertEquals(expected, obtained);
        });

        assertDoesNotThrow(() -> {
            GuardianAPIManager expected = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
            ArrayList<QueryParam> list = new ArrayList<>();
            list.add(new QueryParam("api-key", "ugo"));
            list.add(new QueryParam("from-date", "2003-12-23"));
            list.add(new QueryParam("to-date", "2002-12-23"));
            list.add(new QueryParam("pages", "12"));
            list.add(new QueryParam("page-size", "120"));
            expected.addParams(list);

            final Scanner sc = new Scanner("TheGuardianAPI\nq \nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\napi-key ugo\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertEquals(expected, obtained);
        });

        //Test with a quit at the beginning
        assertDoesNotThrow(() -> {
            GuardianAPIManager expected = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
            ArrayList<QueryParam> list = new ArrayList<>();
            list.add(new QueryParam("q", "kingdom"));
            expected.addParams(list);

            final Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nquit\nto-date 2002-12-23\npages 12\npage-size 120\napi-key\nquit");
            final InteractiveSelectAPI finalTest = new InteractiveSelectAPI(sc, null);
            APIManager obtained = finalTest.askParams(finalTest.askAPIName());
            assertEquals(expected, obtained);
        });
    }
}
