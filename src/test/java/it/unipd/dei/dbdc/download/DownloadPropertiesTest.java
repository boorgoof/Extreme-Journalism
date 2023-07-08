package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.interfaces.APIManager;
import it.unipd.dei.dbdc.download.src_api_managers.TestManager.TestManager;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManager;
import it.unipd.dei.dbdc.download.src_callers.KongAPICaller;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link DownloadProperties}.
 */
@Order(7)
public class DownloadPropertiesTest {
    /**
     * Tests {@link DownloadProperties#readProperties(String)} with various valid and invalid inputs.
     */
    @Test
    public void readProperties()
    {
        GuardianAPIManager guardianAPIManager = new GuardianAPIManager(new KongAPICaller(), "TheGuardianAPI");

        assertDoesNotThrow(() -> {
            //Tests with default properties
            final HashMap<String, APIManager> obt2 = DownloadProperties.readProperties(null);
            assertEquals(1, obt2.size());
            assertEquals(guardianAPIManager, obt2.get("TheGuardianAPI"));

            //Tests with a valid download.properties
            final HashMap<String, APIManager> obtained;
            obtained = DownloadProperties.readProperties(DownloadHandlerTest.resources_url+"defaultDownload.properties");
            assertEquals(1, obtained.size());
            assertEquals(guardianAPIManager, obtained.get("TheGuardianAPI"));
        });

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

    }

    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created.
     */
    private DownloadPropertiesTest() {}
}
