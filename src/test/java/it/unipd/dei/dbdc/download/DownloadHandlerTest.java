package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManagerTest;
import it.unipd.dei.dbdc.tools.PathManagerTest;
import org.junit.jupiter.api.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link DownloadHandler}.
 * It uses functions to redirect output of the tested class, and provides input with a specific function.
 * As this class is one of the last to be processed, the {@link APIContainer} has already been initialized, so
 * it should not throw any {@link java.io.IOException}.
 */
@Order(7)
public class DownloadHandlerTest {

    /**
     * The path to the folder where there will be all the download resources.
     * It uses the {@link PathManagerTest#resources_folder} and adds download.
     */
    public final static String resources_url = PathManagerTest.resources_folder +"download/";

    /**
     * Useful function to change the input from System.in to the String specified as a parameter.
     */
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    /**
     * To set the System.out to a {@link ByteArrayOutputStream} so that we don't see the output during the tests.
     */
    @BeforeEach
    public void setUpOutput() {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }



    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    /**
     * Tests the download part with various valid and invalid inputs.
     */
    @Test
    public void download()
    {
        //The container is initialized with trueDownload.properties by all the test classes that use it
        //This is done only if we want to test this class alone.
        assertDoesNotThrow(() -> DownloadHandler.download(resources_url+"trueDownload.properties", resources_url+"trueApi.properties"));
        assertDoesNotThrow(() -> DownloadHandler.download(resources_url+"falseDownload.properties", resources_url+"trueApi.properties"));

        //Tests with valid download.properties
        assertDoesNotThrow( () ->
        {
            assertEquals("./database/TheGuardianAPI", DownloadHandler.download(resources_url+"trueDownload.properties", resources_url+"trueApi.properties"));
            assertEquals("./database/Test", DownloadHandler.download(null, resources_url+"trueApiTest.properties"));
        });

        //Tests with invalid api.properties, but valid things specified by the user
        assertDoesNotThrow( () ->
        {
            provideInput("TheGuardianAPI\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 1\npage-size 120\napi-key "+ GuardianAPIManagerTest.key+"\nquit");
            assertEquals("./database/TheGuardianAPI", DownloadHandler.download(null, null));

            provideInput("Test\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 1\npage-size 120\napi-key "+ GuardianAPIManagerTest.key+"\nquit");
            assertEquals("./database/Test", DownloadHandler.download(null, resources_url+"falseApi.properties"));

            provideInput("Test\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 1\npage-size 120\napi-key "+ GuardianAPIManagerTest.key+"\nquit");
            assertEquals("./database/Test", DownloadHandler.download(null, resources_url+"falseApi.properties"));

            provideInput("Test\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 1\npage-size 120\napi-key "+ GuardianAPIManagerTest.key+"\nquit");
            assertEquals("./database/Test", DownloadHandler.download(null, resources_url+"falseApi2.properties"));

            provideInput("Test\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 1\npage-size 120\napi-key "+ GuardianAPIManagerTest.key+"\nquit");
            assertEquals("./database/Test", DownloadHandler.download(null, resources_url+"falseApi3.properties"));

            provideInput("Test\nq kingdom\nfrom-date 2003-12-23\nto-date 2002-12-23\npages 1\npage-size 120\napi-key "+ GuardianAPIManagerTest.key+"\nquit");
            assertEquals("./database/Test", DownloadHandler.download(null, resources_url+"notexistent.properties"));

            //Tests with different inputs
            provideInput("Test\nTheGuardianAPI\nq kingdom\napi-key "+ GuardianAPIManagerTest.key+"\nquit");
            assertEquals("./database/Test", DownloadHandler.download(null, null));

            provideInput("api-key "+ GuardianAPIManagerTest.key+"\nquit\nTheGuardianAPI\napi-key "+ GuardianAPIManagerTest.key+"\nquit");
            assertEquals("./database/TheGuardianAPI", DownloadHandler.download(null, null));

            provideInput("TheGuardianAPI\napi-key "+ GuardianAPIManagerTest.key+"\npages 3\npage-size 120\nquit");
            assertEquals("./database/TheGuardianAPI", DownloadHandler.download(null, null));

            provideInput("TheGuardianAP\napi-key "+ GuardianAPIManagerTest.key+"\nquit\nTest\napi-key "+GuardianAPIManagerTest.key+"\nquit");
            assertEquals("./database/Test", DownloadHandler.download(null, null));

            provideInput("TheGuardianAPI\napi-key "+ GuardianAPIManagerTest.key+"\npages 2\nq \"solar energy\"\nquit");
            assertEquals("./database/TheGuardianAPI", DownloadHandler.download(null, null));

            provideInput("Test\napi-key "+GuardianAPIManagerTest.key+"\nfrom-date 1200-12-23\nquit");
            assertEquals("./database/Test", DownloadHandler.download(null, null));

            provideInput("Test\napi-key "+GuardianAPIManagerTest.key+"\nfrom-date 1200.12-23\nquit\nTheGuardianAPI\napi-key "+ GuardianAPIManagerTest.key+"\nquit\n");
            assertEquals("./database/TheGuardianAPI", DownloadHandler.download(null, null));
        });
    }
}
