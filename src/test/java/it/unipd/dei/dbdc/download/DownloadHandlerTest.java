package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManagerTest;
import it.unipd.dei.dbdc.tools.PathManagerTest;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
public class DownloadHandlerTest {

    public final static String resources_url = PathManagerTest.resources_folder +"download/";

    //Useful function to change the input from System.in to the String specified as a parameter
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    //Function to restore the standard input
    @AfterAll
    public static void restoreSystemInputOutput() {
        System.setIn(System.in);
    }

    @Test
    public void download()
    {
        //The container is initialized with trueDownload.properties by all the test classes that use it
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
