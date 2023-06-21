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
            fail("Non e' presente un field di nome specified_params in GuardianAPIParams");
        }
        objectFields.setAccessible(true);
    }

    @Test
    public void addParam() {

        GuardianAPIParams tester = new GuardianAPIParams();
        Map<String, Object> specified_fields = new HashMap<>();

        try {
        assertEquals(specified_fields, objectFields.get(tester));

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
            fail("Non e' stato reso accessibile il field");
        }

        try
        {
            tester.addParam(new QueryParam("from-date", "1987-12-23"));
            tester.addParam(new QueryParam("to-date", "1987-12-23"));
            tester.addParam(new QueryParam("from-date", "2001-01-31"));
            tester.addParam(new QueryParam("to-date", "2001-01-31"));
            tester.addParam(new QueryParam("from-date", "2021-02-27"));
            tester.addParam(new QueryParam("to-date", "2021-02-27"));
            tester.addParam(new QueryParam("from-date", "1200-01-31"));
            tester.addParam(new QueryParam("to-date", "1200-01-31"));
            tester.addParam(new QueryParam("from-date", "1111-01-31"));
            tester.addParam(new QueryParam("to-date", "1111-01-31"));
            tester.addParam(new QueryParam("from-date", "2001-08-01"));
            tester.addParam(new QueryParam("to-date", "2001-08-01"));
            tester.addParam(new QueryParam("to-date", "2000-02-29"));
        }
        catch (IllegalArgumentException e)
        {
            fail("Data non messa in maniera corretta");
        }

        try
        {
            tester.addParam(new QueryParam("to-date", "1987/10/30"));
            fail("Data non messa in maniera corretta");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

        try
        {
            tester.addParam(new QueryParam("to-date", "1987.10.30"));
            fail("Data non messa in maniera corretta");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

        try
        {
            tester.addParam(new QueryParam("to-date", "19871030"));
            fail("Data non messa in maniera corretta");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

        try
        {
            tester.addParam(new QueryParam("to-date", "10-1987-30"));
            fail("Data non messa in maniera corretta");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

        try
        {
            tester.addParam(new QueryParam("to-date", "30-10-1987"));
            fail("Data non messa in maniera corretta");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

        try
        {
            tester.addParam(new QueryParam("from-date", "1987-02-36"));
            fail("Data non messa in maniera corretta");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

        try
        {
            tester.addParam(new QueryParam("to-date", "2001-02-29"));
            fail("Data non messa in maniera corretta");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

        try
        {
            tester.addParam(new QueryParam("from-date", "1987-09-31"));
            fail("Data non messa in maniera corretta");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

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
    }

    @Test
    public void getParams() {
        GuardianAPIParams tester = new GuardianAPIParams();
        Map<String, Object> specified_fields = new HashMap<>();

        // Lancio di eccezioni:
        try {
            tester.getParams();
            fail("Non lancia eccezioni se e' vuoto");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

        tester.addParam(new QueryParam("fake", "test"));
        tester.addParam(new QueryParam("another", "fake"));
        tester.addParam(new QueryParam("this", "fails"));
        specified_fields.put("fake", "test");
        specified_fields.put("another", "fake");
        specified_fields.put("this", "fails");

        try {
            tester.getParams();
            fail("Non lancia eccezioni se non ha la api-key");
        }
        catch (IllegalArgumentException e)
        {
            //Intentionally left blank
        }

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

        assertEquals(tester.getParams(), expected);

        //CAMBIO I PARAMETRI DI BASE:
        tester.addParam(new QueryParam("page-size", "34"));
        specified_fields.put("page-size", 34);

        tester.addParam(new QueryParam("q", "kingdom"));
        specified_fields.put("q", "kingdom");

        //Metto anche uno di quelli di default diverso: dovrebbe essere sovrascritto
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
        ArrayList<Map<String, Object>> myparams = tester.getParams();

        assertEquals(myparams, expected);
    }

    @AfterAll
    public static void setInaccessible() throws NoSuchFieldException {
        objectFields.setAccessible(false);
    }
}
