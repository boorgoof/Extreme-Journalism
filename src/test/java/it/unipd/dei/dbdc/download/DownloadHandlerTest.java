package it.unipd.dei.dbdc.download;

import it.unipd.dei.dbdc.tools.PathManagerTest;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

//FIXME: assertions have the first value that is the expected parameter
//FIXME: assertThrows
public class DownloadHandlerTest {

    public final static String resources_url = PathManagerTest.resources_folder +"download/";
    @Test
    public void download()
    {
        //Test with invalid download.properties
        assertThrows(IOException.class, () -> DownloadHandler.download(resources_url+"falseDownload.properties", resources_url+"trueApi.properties"));

        //Tests with valid download.properties
        try {
            assertEquals("./database/TheGuardianAPI", DownloadHandler.download(resources_url+"trueDownload.properties", resources_url+"trueApi.properties"));
            assertEquals("./database/Test", DownloadHandler.download(resources_url+"trueDownload.properties", resources_url+"trueApiTest.properties"));
        }
        catch (IOException e)
        {
            fail("Exception thrown even if download properties were right");
        }

        //FIXME: vedi se e' possibile fare come scritto sotto
        //More tests with false api properties are not possible because that would require the user to give some input.
    }
}
