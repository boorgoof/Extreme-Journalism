package it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI;

import it.unipd.dei.dbdc.download.QueryParam;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
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

        GuardianAPIParams par1 = new GuardianAPIParams();
        assertEquals(par1, new GuardianAPIParams(par1));

        par1.addParam(new QueryParam("no", "exist"));
        assertEquals(par1, new GuardianAPIParams(par1));
    }

    @Test
    public void equals()
    {
        //They are equals through the copy constructor
        GuardianAPIParams par = new GuardianAPIParams();
        par.addParam(new QueryParam("api-key", "ugo"));
        par.addParam(new QueryParam("q", "kingdom"));
        par.addParam(new QueryParam("pages", "2"));
        par.addParam(new QueryParam("page-size", "3"));
        par.addParam(new QueryParam("from-date", "1200-11-12"));
        GuardianAPIParams par2 = new GuardianAPIParams(par);
        assertTrue(par2.equals(par));

        //They are not equals if they have different params
        GuardianAPIParams par1 = new GuardianAPIParams();
        assertFalse(par.equals(par1));
        par1.addParam(new QueryParam("no", "exist"));
        assertFalse(par.equals(par1));

        //They are equals with the same params
        par1.addParam(new QueryParam("api-key", "ugo"));
        par1.addParam(new QueryParam("q", "kingdom"));
        par1.addParam(new QueryParam("pages", "2"));
        par1.addParam(new QueryParam("page-size", "3"));
        par1.addParam(new QueryParam("from-date", "1200-11-12"));
        par.addParam(new QueryParam("no", "exist"));
        assertTrue(par.equals(par1));

        //Changing params, they are not equals
        par1.addParam(new QueryParam("api-key", "ananoa"));
        assertFalse(par.equals(par1));

        par1.addParam(new QueryParam("api-key", ""));
        assertFalse(par.equals(par1));
    }

    @Test
    public void addParam()
    {
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

            specified_fields.clear();
            tester = new GuardianAPIParams();

            //Parameters that have a default value, and should not be put into the field
            tester.addParam(new QueryParam("q", "test"));
            assertEquals(specified_fields, objectFields.get(tester));

            tester.addParam(new QueryParam("pages", "3"));
            assertEquals(specified_fields, objectFields.get(tester));

            tester.addParam(new QueryParam("page-size", "12"));
            assertEquals(specified_fields, objectFields.get(tester));

            tester.addParam(new QueryParam("api-key", "12345"));
            assertEquals(specified_fields, objectFields.get(tester));

            //Add parameters that are not default ones
            tester.addParam(new QueryParam("from-date", "1987-12-23"));
            specified_fields.put("from-date", "1987-12-23");
            assertEquals(specified_fields, objectFields.get(tester));

            tester.addParam(new QueryParam("to-date", "1987-12-23"));
            specified_fields.put("to-date", "1987-12-23");
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

        //Invalid pages or page-size
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("page-size", "-2")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("page-size", "201")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("page-size", "ajaio")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("pages", "afjpia")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("pages", "-3")));

        //Null parameters
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam(null, "1987-02-12")));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(new QueryParam("to-date", null)));
        assertThrows(IllegalArgumentException.class, () -> finalTester.addParam(null));
    }

    @Test
    public void getParams() {
        GuardianAPIParams tester = new GuardianAPIParams();
        Map<String, Object> specified_fields = new HashMap<>();

        //Without the api-key
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
        tester.addParam(new QueryParam("pages", "3"));

        //Adds also default values, these should be overwritten
        tester.addParam(new QueryParam("show-fields", "all"));
        tester.addParam(new QueryParam("format", "xml"));

        //Adds other not real parameters
        tester.addParam(new QueryParam("fake", "test"));
        tester.addParam(new QueryParam("another", "fake"));
        tester.addParam(new QueryParam("this", "fails"));
        specified_fields.put("fake", "test");
        specified_fields.put("another", "fake");
        specified_fields.put("this", "fails");

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
