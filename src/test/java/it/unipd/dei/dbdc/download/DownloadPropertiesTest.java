package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.download.src_api_managers.TestManager.TestManager;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManager;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class DownloadPropertiesTest {
    @Test
    public void readAPIContainerProperties()
    {
        HashMap<String, APIManager> obtained;
        GuardianAPIManager guardianAPIManager = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");
        try {
            //Tests with a valid download.properties
            obtained = DownloadProperties.readAPIContainerProperties(DownloadHandlerTest.resources_url+"defaultDownload.properties");
            assertEquals(1, obtained.size());
            assertEquals(guardianAPIManager, obtained.get("TheGuardianAPI"));

            //Tests with default properties
            obtained = DownloadProperties.readAPIContainerProperties(null);
            assertEquals(1, obtained.size());
            assertEquals(guardianAPIManager, obtained.get("TheGuardianAPI"));
        } catch (IOException | ClassCastException e) {
            fail("Failed the reading of the defaultDownload.properties");
        }

        //Tests with invalid download.properties
        assertThrows(IOException.class, () -> DownloadProperties.readAPIContainerProperties(DownloadHandlerTest.resources_url + "falseDownload.properties"));

        assertThrows(IOException.class, () -> DownloadProperties.readAPIContainerProperties(DownloadHandlerTest.resources_url+"falseDownload2.properties"));

        //Test with other properties
        try {
            obtained = DownloadProperties.readAPIContainerProperties(DownloadHandlerTest.resources_url+"trueDownload.properties");
            assertEquals(obtained.size(), 2);
            assertEquals(guardianAPIManager, obtained.get("TheGuardianAPI"));
            assertEquals(new TestManager(new KongAPICaller(), "Test"), obtained.get("Test"));
        } catch (IOException e) {
            fail("Failed the reading of the trueDownload.properties");
        }

        //TODO: more tests?
    }
}
