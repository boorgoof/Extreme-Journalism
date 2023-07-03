package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.download.DownloadHandlerTest;
import it.unipd.dei.dbdc.download.DownloadProperties;
import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.download.src_api_managers.TestManager.TestManager;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;
import it.unipd.dei.dbdc.serializers.src_serializers.XmlSerializer;
import it.unipd.dei.dbdc.tools.PathManagerTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
public class SerializationPropertiesTest {

    @Test
    void readSerializersProperties() {
    }
}


/*@Test
    public void readSerializersProperties() {

        Serializer xmlSerializer = new XmlSerializer();

        assertDoesNotThrow(() -> {
            //Tests with default properties
            Map<String, Serializer> defaultSerializers = SerializationProperties.readSerializersProperties(null);
            assertEquals(1, defaultSerializers.size());
            assertEquals(xmlSerializer, defaultSerializers.get("xml"));
/*
            //Tests with a valid serializers.properties
            final HashMap<String, APIManager> obtained;
            obtained = DownloadProperties.readProperties(resources_url+"serializers.properties");
            assertEquals(1, obtained.size());
            assertEquals(xmlSerializer, obtained.get("xml"));


        });
    /*
        //Tests with invalid download.properties (wrong keys or classes)
        assertThrows(IOException.class, () -> DownloadProperties.readProperties(DownloadHandlerTest.resources_url + "falseDownload.properties"));
        assertThrows(IOException.class, () -> DownloadProperties.readProperties(DownloadHandlerTest.resources_url+"falseDownload2.properties"));

        //Test with other properties, with TestManager
        assertDoesNotThrow( () -> {
            final HashMap<String, APIManager> finalObt = DownloadProperties.readProperties(DownloadHandlerTest.resources_url + "trueDownload.properties");
            assertEquals(2, finalObt.size());
            assertEquals(guardianAPIManager, finalObt.get("TheGuardianAPI"));
            assertTrue(finalObt.get("Test") instanceof TestManager);
        });
    */