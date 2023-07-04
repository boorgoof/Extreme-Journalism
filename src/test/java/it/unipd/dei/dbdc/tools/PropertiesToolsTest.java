package it.unipd.dei.dbdc.tools;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class PropertiesToolsTest {

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

            assertNotNull(PropertiesTools.getOutProperties(path+"download/defaultDownload.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/falseApi.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/falseApi2.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/falseApi3.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/falseDownload.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/falseDownload2.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/trueApi.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/trueApiTest.properties"));
            assertNotNull(PropertiesTools.getOutProperties(path+"download/trueDownload.properties"));
            //TODO: completa con altre properties di serializers/deserializers
        });

        //Test with not existent properties
        assertThrows(IOException.class, () -> PropertiesTools.getOutProperties("./notexist.properties"));
        assertThrows(IOException.class, () -> PropertiesTools.getOutProperties(null));
        assertThrows(IOException.class, () -> PropertiesTools.getOutProperties(""));
    }

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
}
