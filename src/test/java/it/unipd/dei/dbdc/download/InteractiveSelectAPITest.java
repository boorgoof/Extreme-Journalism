package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManager;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class InteractiveSelectAPITest {

    @Test
    public void askAPIName()
    {
        //Test with everything right
        GuardianAPIManager expected = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        ArrayList<QueryParam> list = new ArrayList<>();
        list.add(new QueryParam("q", "kingdom"));
        list.add(new QueryParam("api-key", "ugo"));
        list.add(new QueryParam("from-date", "2003-12-23"));
        list.add(new QueryParam("to-date", "2002-12-23"));
        list.add(new QueryParam("pages", "12"));
        list.add(new QueryParam("page-size", "120"));
        expected.addParams(list);

        Scanner sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\napi-key ugo\nquit");
        InteractiveSelectAPI toTest = null;
        try {
            toTest = new InteractiveSelectAPI(sc, null);
        } catch (IOException e) {
            fail("Error in the launch of the exceptions");
        }
        APIManager obtained = toTest.askParams(toTest.askAPIName());
        assertEquals(expected, obtained);

        //Test with the name invalid
        sc = new Scanner("TheGuardianAPIfalse\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\napi-key ugo\nquit");
        try {
            toTest = new InteractiveSelectAPI(sc, null);
        } catch (IOException e) {
            fail("Error in the launch of the exceptions");
        }
        obtained = toTest.askParams(toTest.askAPIName());
        assertNull(obtained);

        //Test with an entry that is invalid
        sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\nanother entry\nquit");
        try {
            toTest = new InteractiveSelectAPI(sc, null);
        } catch (IOException e) {
            fail("Error in the launch of the exceptions");
        }
        obtained = toTest.askParams(toTest.askAPIName());
        assertNull(obtained);

        //Test with an entry with a valid key and invalid value
        sc = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003.12.23\nto-date 2002-12-23\npages 12\npage-size 120\nquit");
        try {
            toTest = new InteractiveSelectAPI(sc, null);
        } catch (IOException e) {
            fail("Error in the launch of the exceptions");
        }
        obtained = toTest.askParams(toTest.askAPIName());
        assertNull(obtained);

        //Test with a line with a key without value
        expected = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        list.clear();
        list.add(new QueryParam("q", "kingdom"));
        list.add(new QueryParam("from-date", "2003-12-23"));
        list.add(new QueryParam("to-date", "2002-12-23"));
        list.add(new QueryParam("pages", "12"));
        list.add(new QueryParam("page-size", "120"));
        expected.addParams(list);

        Scanner sc1 = new Scanner("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 12\npage-size 120\napi-key\nquit");
        try {
            toTest = new InteractiveSelectAPI(sc1, null);
        } catch (IOException e) {
            fail("Error in the launch of the exceptions");
        }
        APIManager newObtained = toTest.askParams(toTest.askAPIName());
        assertEquals(expected, newObtained);
    }
}
