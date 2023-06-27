package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.QueryParam;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class GuardianAPIParamsTest {
    public static Field objectFields;
    @BeforeAll
    public static void setAccessible() {
        try {
            objectFields = GuardianAPIParams.class.getDeclaredField("specified_params");
        } catch (NoSuchFieldException e) {
            fail("Error during reflection");
        }
        objectFields.setAccessible(true);
    }

    @Test
    public void copy()
    {
        GuardianAPIParams par = new GuardianAPIParams();
        par.addParam(new QueryParam("api-key", "ugo"));
        par.addParam(new QueryParam("q", "kingdom"));
        par.addParam(new QueryParam("pages", "2"));
        par.addParam(new QueryParam("page-size", "3"));
        par.addParam(new QueryParam("from-date", "1200-11-12"));
        assertEquals(par, new GuardianAPIParams(par));

        par = new GuardianAPIParams();
        assertEquals(par, new GuardianAPIParams(par));

        par.addParam(new QueryParam("no", "exist"));
        assertEquals(par, new GuardianAPIParams(par));
    }

    @Test
    public void addParam() {

        GuardianAPIParams tester = new GuardianAPIParams();
        Map<String, Object> specified_fields = new HashMap<>();
        try {
            //Both empty
            assertEquals(specified_fields, objectFields.get(tester));

            //With some fake parameters
            tester.addParam(new QueryParam("fake", "test"));
            specified_fields.put("fake", "test");
            assertEquals(specified_fields, objectFields.get(tester));

            tester.addParam(new QueryParam("another", "fake"));
            specified_fields.put("another", "fake");
            assertEquals(specified_fields, objectFields.get(tester));

            tester.addParam(new QueryParam("this", "fails"));
            specified_fields.put("this", "fake");
            assertNotEquals(specified_fields, objectFields.get(tester));

        } catch (IllegalAccessException e) {
            fail("Error during reflection");
        }

        //Test params that are legal
        specified_fields = new HashMap<>();
        tester = new GuardianAPIParams();
        try {
            assertEquals(specified_fields, objectFields.get(tester));

            tester.addParam(new QueryParam("q", "test"));
            assertEquals(specified_fields, objectFields.get(tester));

            tester.addParam(new QueryParam("pages", "3"));
            assertEquals(specified_fields, objectFields.get(tester));

            tester.addParam(new QueryParam("page-size", "12"));
            assertEquals(specified_fields, objectFields.get(tester));

            tester.addParam(new QueryParam("api-key", "12345"));
            assertEquals(specified_fields, objectFields.get(tester));

        } catch (IllegalAccessException e) {
            fail("Non e' stato reso accessibile il field");
        }

        //Test of dates
        GuardianAPIParams finalTester = tester;
        assertDoesNotThrow( () ->
        {
            finalTester.addParam(new QueryParam("from-date", "1987-12-23"));
            finalTester.addParam(new QueryParam("to-date", "1987-12-23"));
            finalTester.addParam(new QueryParam("from-date", "2001-01-31"));
            finalTester.addParam(new QueryParam("to-date", "2001-01-31"));
            finalTester.addParam(new QueryParam("from-date", "2021-02-27"));
            finalTester.addParam(new QueryParam("to-date", "2021-02-27"));
            finalTester.addParam(new QueryParam("from-date", "1200-01-31"));
            finalTester.addParam(new QueryParam("to-date", "1200-01-31"));
            finalTester.addParam(new QueryParam("from-date", "1111-01-31"));
            finalTester.addParam(new QueryParam("to-date", "1111-01-31"));
            finalTester.addParam(new QueryParam("from-date", "2001-08-01"));
            finalTester.addParam(new QueryParam("to-date", "2001-08-01"));
            finalTester.addParam(new QueryParam("to-date", "2000-02-29"));
        });

        //Invalid format
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("to-date", "1987/10/30")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("to-date", "1987.10.30")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("to-date", "19871030")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("to-date", "10-1987-30")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("to-date", "30-10-1987")));
        //Invalid date
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("to-date", "1987-02-36")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("to-date", "2001-02-29")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("to-date", "1987-09-31")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("to-date", "1987-02-36")));

    }

    @Test
    public void getParams() {
        GuardianAPIParams tester = new GuardianAPIParams();
        Map<String, Object> specified_fields = new HashMap<>();

        //Without parameters
        assertThrows(IllegalArgumentException.class, tester::getParams);

        //Adds some parameters and get params, but illegally because there is no api-key
        tester.addParam(new QueryParam("fake", "test"));
        tester.addParam(new QueryParam("another", "fake"));
        tester.addParam(new QueryParam("this", "fails"));
        specified_fields.put("fake", "test");
        specified_fields.put("another", "fake");
        specified_fields.put("this", "fails");
        assertThrows(IllegalArgumentException.class, tester::getParams);

        //Adds parameters that are the default ones
        tester.addParam(new QueryParam("api-key", "notakey"));
        specified_fields.put("api-key", "notakey");
        specified_fields.put("page-size", 200);
        specified_fields.put("q", "\"nuclear power\"");
        specified_fields.put("show-fields", "bodyText,headline");
        specified_fields.put("format", "json");

        ArrayList<Map<String, Object>> expected = new ArrayList<>(5);
        for (int i = 0; i<5; i++)
        {
            Map<String, Object> map = new HashMap<>(specified_fields);
            map.put("page", (i+1));
            expected.add(i, map);
        }
        assertEquals(expected, tester.getParams());

        //Change default params
        tester.addParam(new QueryParam("page-size", "34"));
        specified_fields.put("page-size", 34);
        tester.addParam(new QueryParam("q", "kingdom"));
        specified_fields.put("q", "kingdom");

        tester.addParam(new QueryParam("show-fields", "all"));
        tester.addParam(new QueryParam("format", "xml"));
        tester.addParam(new QueryParam("pages", "3"));

        expected = new ArrayList<>(3);
        for (int i = 0; i<3; i++)
        {
            Map<String, Object> map = new HashMap<>(specified_fields);
            map.put("page", (i+1));
            expected.add(i, map);
        }
        assertEquals(expected, tester.getParams());
    }

    @AfterAll
    public static void setInaccessible() {
        objectFields.setAccessible(false);
    }
}
