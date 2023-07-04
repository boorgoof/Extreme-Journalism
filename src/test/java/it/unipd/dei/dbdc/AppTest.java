package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.download.APIContainer;
import it.unipd.dei.dbdc.download.DownloadHandlerTest;
import it.unipd.dei.dbdc.tools.PathManagerTest;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test for simple App.
 */
@Order(7)
@Disabled
public class AppTest
{
    @Test
    public void mainTestErr() {
        //The container is initialized with trueDownload.properties by all the test classes that use it
        assertDoesNotThrow(() -> APIContainer.getInstance(DownloadHandlerTest.resources_url+"trueDownload.properties"));

        deleteFilesOut();
        //Test of help, it is done here because we want to see the errors produced (as the output is too long and may vary a lot)
        App.main(new String[]{"-h"});
        //We don't check the output of this, only if there is something in error
        assertEquals("", getError());

        File[] files = new File("./output").listFiles();
        assertNotNull(files);
        assertEquals(0, files.length);

        //Errors

        //No action specified
        App.main(new String[]{});
        String expected_err = "ERROR - parsing command line:" +
                "Missing required option: [-h Print help, -d Download files from the selected API, -a Analyze the top 50 terms of the selected files, -da Download files from the selected API and analysis the top 50 terms of those files]" +
                "The program has been terminated because there was no action to perform specified.";
        assertEquals(expected_err, getError());

        //General properties not valid
        App.main(new String[]{"-a", "-genpf", PathManagerTest.resources_folder + "download/trueApiTest.properties"});
        expected_err += "The program has been terminated because the file general.properties was not found, or the properties passed by the user were not valid: Error in the format of the properties";
        assertEquals(expected_err, getError());

        //No file specified to serialize
        App.main(new String[]{"-a"});
        expected_err += "Error: there is no file to serialize.";
        assertEquals(expected_err, getError());

        deleteFilesOut();
    }

    @Test
    public void mainTestOut() {
        //Right actions

        //Download
        App.main(new String[]{"-d", "-apf", PathManagerTest.resources_folder+"download/trueApi.properties"});
        String expected_out = "Entering the download part..." +
                "API selected correctly..." +
                "You can find the downloaded files in the format in which they were downloaded in ./database/TheGuardianAPI" +
                "Exiting the download part..." +
                "Entering the deserialization of ./database/TheGuardianAPI..." +
                "Exiting the deserialization part..." +
                "Entering the serialization part..." +
                "Exiting the serialization part. You can find the serialized file in ./output/serialized.xml..." +
                "Everything went correctly." +
                "Thank you for choosing our application, we hope to see you soon.";
        assertEquals(expected_out, getOutput());

        File[] files = new File("./output").listFiles();
        assertNotNull(files);
        assertEquals(1, files.length);
        File out = files[0];
        assertEquals("serialized.xml", out.getName());

        deleteFilesOut();

        //Analysis
        App.main(new String[]{"-a", "-path", PathManagerTest.resources_folder+"DeserializationTest/deserializersTest/csvTest"});
        expected_out += "Entering the deserialization of "+PathManagerTest.resources_folder+"DeserializationTest/deserializersTest/csvTest..." +
                "Exiting the deserialization part..." +
                "Entering the serialization part..." +
                "Exiting the serialization part. " +
                "You can find the serialized file in ./output/serialized.xml..." +
                "Entering the deserialization of ./output/serialized.xml..." +
                "Exiting the deserialization part..." +
                "Entering the analysis part..." +
                "Exiting the analysis part. " +
                "You can find the resulting file in./output/output.txt" +
                "Everything went correctly.Thank you for choosing our application, we hope to see you soon.";
        assertEquals(expected_out, getOutput());

        files = new File("./output").listFiles();
        assertNotNull(files);
        assertEquals(2, files.length);
        out = files[1];
        assertEquals("serialized.xml", out.getName());
        out = files[0];
        assertEquals("output.txt", out.getName());

        deleteFilesOut();

        //Download and analysis
        App.main(new String[]{"-da", "-apf", PathManagerTest.resources_folder+"download/trueApi.properties"});
        expected_out += "Entering the download part..." +
                "API selected correctly..." +
                "You can find the downloaded files in the format in which they were downloaded in ./database/TheGuardianAPI" +
                "Exiting the download part..." +
                "Entering the deserialization of ./database/TheGuardianAPI..." +
                "Exiting the deserialization part..." +
                "Entering the serialization part..." +
                "Exiting the serialization part. " +
                "You can find the serialized file in ./output/serialized.xml..." +
                "Entering the deserialization of ./output/serialized.xml..." +
                "Exiting the deserialization part..." +
                "Entering the analysis part..." +
                "Exiting the analysis part. " +
                "You can find the resulting file in./output/output.txt" +
                "Everything went correctly.Thank you for choosing our application, we hope to see you soon.";
        assertEquals(expected_out, getOutput());

        files = new File("./output").listFiles();
        assertNotNull(files);
        assertEquals(2, files.length);
        out = files[1];
        assertEquals("serialized.xml", out.getName());
        out = files[0];
        assertEquals("output.txt", out.getName());

        deleteFilesOut();

    }

    //Utility function that clears the folder of ./output. The PathManager function is not used because it could have problems
    private void deleteFilesOut()
    {
        File[] files = new File("./output").listFiles();
        assertNotNull(files);
        for (File f : files)
        {
            f.delete();
        }
    }

    //The output
    private ByteArrayOutputStream testOut;
    //The error output
    private ByteArrayOutputStream testErr;

    //To set the output to testOut
    @BeforeEach
    public void setUpErrorOutput() {
        testOut = new ByteArrayOutputStream();
        testErr = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));
        System.setErr(new PrintStream(testErr));
    }

    //To get the output
    private String getError() {
        String out = testErr.toString();
        out = out.replace("\r", "");
        out = out.replace("\n", "");
        return out;
    }

    //To get the output
    private String getOutput() {
        String out = testOut.toString();
        out = out.replace("\r", "");
        out = out.replace("\n", "");
        return out;
    }

    //To restore the output
    @AfterEach
    public void restoreSystemErrorOutput() {
        System.setErr(System.err);
        System.setOut(System.out);
    }
}
