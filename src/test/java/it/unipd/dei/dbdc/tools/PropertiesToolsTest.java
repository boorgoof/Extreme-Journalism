package it.unipd.dei.dbdc.tools;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link PropertiesTools}.
 */
@Order(7)
public class PropertiesToolsTest {

    /**
     * Tests the {@link PropertiesTools#getProperties(String, String)} with various valid or invalid inputs.
     */
    @Test
    public void getProperties()
    {
        String path = PathManagerTest.resources_folder;
        assertDoesNotThrow(() ->
        {
            //Tests with valid external properties and invalid default
            assertNotNull(PropertiesTools.getProperties(null,path+"generalproperties/false.properties"));
            assertNotNull(PropertiesTools.getProperties(null,path+"download/falseApi.properties"));
            assertNotNull(PropertiesTools.getProperties(null,path+"generalproperties/default.properties"));
            assertNotNull(PropertiesTools.getProperties(null,path+"download/trueDownload.properties"));

            //Tests with valid default properties and invalid out
            assertNotNull(PropertiesTools.getProperties("analyze.properties",null));
            assertNotNull(PropertiesTools.getProperties("download.properties",path+"download"));
            assertNotNull(PropertiesTools.getProperties("general.properties",path+"didi.properties"));
            assertNotNull(PropertiesTools.getProperties("serializers.properties",path+"generalproperties/trueDownload4564646.properties"));
            assertNotNull(PropertiesTools.getProperties("deserializers.properties",""));
        });

        //Tests with invalid default and invalid out
        assertThrows(IOException.class, () -> PropertiesTools.getProperties("ae.properties",null));
        assertThrows(IOException.class, () -> PropertiesTools.getProperties(".properties",path+"download"));
        assertThrows(IOException.class, () -> PropertiesTools.getProperties(null,path+"didi.properties"));
        assertThrows(IOException.class, () -> PropertiesTools.getProperties("",path+"generalproperties/trueDownload4564646.properties"));
        assertThrows(IOException.class, () -> PropertiesTools.getProperties("dese.properties",""));
    }

    /**
     * Tests the {@link PropertiesTools#getOutProperties(String)} with various valid or invalid inputs.
     */
    @Test
    public void getOutProperties()
    {
        //Test with some external properties
        String path = PathManagerTest.resources_folder;
        assertDoesNotThrow(() ->
        {
            assertNotNull(PropertiesTools.getOutProperties(path+"generalproperties/false.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"generalproperties/false2.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"generalproperties/false3.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"generalproperties/default.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"generalproperties/true.properties"));

            assertNotNull(PropertiesTools.getOutProperties(path+"download/falseApi.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/falseApi2.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/falseApi3.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/falseDownload.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/falseDownload2.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/trueApi.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/trueApiTest.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/trueDownload.properties"));

            assertNotNull(PropertiesTools.getOutProperties(path+"DeserializationTest/properties/deserializers.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"SerializationTest/properties/serializers.properties"));
        });

        //Test with not existent properties
        assertThrows(IOException.class, () -> PropertiesTools.getOutProperties("./notexist.properties"));
        assertThrows(IOException.class, () -> PropertiesTools.getOutProperties(null));
        assertThrows(IOException.class, () -> PropertiesTools.getOutProperties(""));
    }

    /**
     * Tests the {@link PropertiesTools#getDefaultProperties(String)} with various valid or invalid inputs.
     */
    @Test
    public void getDefaultProperties()
    {
        //Test with default properties
        assertDoesNotThrow(() ->
        {
            assertNotNull(PropertiesTools.getDefaultProperties("analyze.properties"));
            assertNotNull(PropertiesTools.getDefaultProperties("deserializers.properties"));
            assertNotNull(PropertiesTools.getDefaultProperties("download.properties"));
            assertNotNull(PropertiesTools.getDefaultProperties("general.properties"));
            assertNotNull(PropertiesTools.getDefaultProperties("serializers.properties"));
        });

        //Test with not existent properties
        assertThrows(IOException.class, () -> PropertiesTools.getDefaultProperties("notexist.properties"));
        assertThrows(IOException.class, () -> PropertiesTools.getDefaultProperties(null));
        assertThrows(IOException.class, () -> PropertiesTools.getDefaultProperties(""));
    }

    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created.
     */
    private PropertiesToolsTest() {}
}
