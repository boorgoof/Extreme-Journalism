package it.unipd.dei.dbdc.tools;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link GeneralProperties}.
 */
@Order(7)
public class GeneralPropertiesTest {

    /**
     * The folder of the resources for the tests.
     */
    private static final String resources_url = PathManagerTest.resources_folder+"generalproperties/";

    /**
     * Tests the reading of the properties with various inputs, all files that are inside the resources' folder.
     * The tests are done with various valid or invalid inputs
     */
    @Test
    public void testTotal(){
        //Test with default properties
        assertDoesNotThrow( () ->
        {
            //Null
            GeneralProperties props = new GeneralProperties(null);
            assertEquals(50, props.getWordsCount());
            assertEquals("xml", props.getCommonFormat());

            //Not existent
            props = new GeneralProperties("");
            assertEquals(50, props.getWordsCount());
            assertEquals("xml", props.getCommonFormat());

            //Not existent
            props = new GeneralProperties("not.properties");
            assertEquals(50, props.getWordsCount());
            assertEquals("xml", props.getCommonFormat());

            //Valid properties
            props = new GeneralProperties(resources_url+"default.properties");
            assertEquals(50, props.getWordsCount());
            assertEquals("xml", props.getCommonFormat());

            //Valid properties
            props = new GeneralProperties(resources_url+"true.properties");
            assertEquals(25, props.getWordsCount());
            assertEquals("json", props.getCommonFormat());
        });

        assertThrows(IOException.class, () -> new GeneralProperties(resources_url+"false.properties"));
        assertThrows(IOException.class, () -> new GeneralProperties(resources_url+"false2.properties"));
        assertThrows(IOException.class, () -> new GeneralProperties(resources_url+"false3.properties"));
    }
}
