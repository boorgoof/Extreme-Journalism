package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.tools.PathManagerTest;
import org.junit.jupiter.api.*;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * System tests, that check if everything works fine and the results. It redirects the System.err to
 * a {@link ByteArrayOutputStream} that we can check.
 * It is the last test to be run, as specified in junit-platform.properties.
 * It uses functions to redirect the error output and normal output of the system.
 */
@Order(8)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest
{
    /**
     * The {@link ByteArrayOutputStream} that will contain the error output
     */
    private ByteArrayOutputStream testErr;

    /**
     * To set the System.err to a {@link ByteArrayOutputStream} that we can check.
     * It also sets the System.out to a {@link ByteArrayOutputStream} so that we don't see the output during the tests.
     */
    @BeforeEach
    public void setUpErrorOutput() {
        testErr = new ByteArrayOutputStream();
        System.setErr(new PrintStream(testErr));
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }

    /**
     * Function to get the errors, without the \n ad \r
     */
    private String getError() {
        String err = testErr.toString();
        err = err.replace("\r", "");
        err = err.replace("\n", "");
        return err;
    }

    /**
     * To restore the System.err and System.out
     */
    @AfterEach
    public void restoreSystemErrorOutput() {
        System.setErr(System.err);
        System.setOut(System.out);
    }

    /**
     * The folder of the system tests.
     */
    private final static String folder = PathManagerTest.resources_folder+"system_tests/";

    /**
     * This is the first function that is executed.
     * Checks the output of the application if everything goes right. It is done by checking if there are any
     * errors and if the output file is the one we expect.
     * The output files are not tested for the downloaded articles as the articles can vary a lot depending on
     * what is inside the database of TheGuardian
     * It uses {@link PathManagerTest#readFile(String)} to read the output file.
     */
    @Order(1)
    @Test
    public void mainTestOut() {
        //The APIContainer is initialized with trueDownload.properties by all the test classes that use it

        deleteFilesOut();

        //HELP
        App.main(new String[]{"-h"});
        assertEquals("", getError());

        File[] files = new File("./output").listFiles();
        assertNotNull(files);
        assertEquals(0, files.length);

        //DOWNLOAD OF ARTICLES
        App.main(new String[]{"-d", "-apf", folder+"api.properties"});
        assertEquals("", getError());

        files = new File("./output").listFiles();
        assertNotNull(files);
        assertEquals(1, files.length);
        File out = files[0];
        assertEquals("serialized.xml", out.getName());

        //Analysis of the downloaded files
        App.main(new String[]{"-a"});
        assertEquals("", getError());

        files = new File("./output").listFiles();
        assertNotNull(files);
        assertEquals(2, files.length);
        out = files[1];
        assertEquals("serialized.xml", out.getName());

        out = files[0];
        assertEquals("output.txt", out.getName());
        //We don't test the output as the output can vary depending on what is in the database of the TheGuardian
        deleteFilesOut();

        //Analysis and download of nytimes
        App.main(new String[]{"-da", "-path", folder+"nytimes_articles_v2"});
        assertEquals("", getError());

        files = new File("./output").listFiles();
        assertNotNull(files);
        assertEquals(2, files.length);
        out = files[1];
        assertEquals("serialized.xml", out.getName());
        assertEquals(PathManagerTest.readFile(folder+"serialized_nytimes_v2.xml"), PathManagerTest.readFile(files[1].getPath()));
        out = files[0];
        assertEquals("output.txt", out.getName());
        assertEquals(PathManagerTest.readFile(folder+"output_nytimes_v2.txt"), PathManagerTest.readFile(files[0].getPath()));

        deleteFilesOut();

        //Analysis and download of theGuardian
        App.main(new String[]{"-da", "-path", folder+"theguardian_articles_v1"});
        assertEquals("", getError());

        files = new File("./output").listFiles();
        assertNotNull(files);
        assertEquals(2, files.length);
        out = files[1];
        assertEquals("serialized.xml", out.getName());
        assertEquals(PathManagerTest.readFile(folder+"serialized_theguardian_v1.xml"), PathManagerTest.readFile(files[1].getPath()));

        out = files[0];
        assertEquals("output.txt", out.getName());
        assertEquals(PathManagerTest.readFile(folder+"output_theguardian_v1.txt"), PathManagerTest.readFile(files[0].getPath()));

        deleteFilesOut();
    }

    /**
     * This is the second function that is executed.
     * Checks the errors that are thrown when something goes wrong.
     */
    @Order(2)
    @Test
    public void mainTestErr() {
        //The APIContainer is initialized with trueDownload.properties by all the test classes that use it

        //Errors
        //No action specified
        App.main(new String[]{});
        String expected_err = "ERROR - parsing command line:" +
                "Missing required option: [-h Print help, -d Download files from the selected API or serializes the files indicated in -path, -a Analyze the top terms of the common format file, -da Download files from the selected API and analysis the top terms of those files]" +
                "The program has been terminated because there was no action to perform specified.";
        assertEquals(expected_err, getError());

        //General properties not valid
        App.main(new String[]{"-a", "-genpf", PathManagerTest.resources_folder + "download/trueApiTest.properties"});
        expected_err += "The program has been terminated because the file general.properties was not found, or the properties passed by the user were not valid: Error in the format of the properties";
        assertEquals(expected_err, getError());

        //There is no serialized file
        App.main(new String[]{"-a"});
        expected_err += "Error: it was required the analysis of a file that do not exist";
        assertEquals(expected_err, getError());

        //The APIContainer and DeserializersContainer and SerializersContainer have already been initialized, so we can't try to pass illegal properties to them (they use the Singleton design pattern).

        //Analysis properties not valid
        App.main(new String[]{"-da", "-path", folder+"nytimes_articles_v2", "-anapf", PathManagerTest.resources_folder + "download/trueApiTest.properties"});
        expected_err += "The program has been terminated for an error in the analysis: There is no class with the name null";
        assertEquals(expected_err, getError());

        deleteFilesOut();
    }

    /**
     * Utility function that clears the folder of ./output. The PathManager function is not used because it could have problems.
     */
    private void deleteFilesOut()
    {
        File[] files = new File("./output").listFiles();
        assertNotNull(files);
        for (File f : files)
        {
            f.delete();
        }
    }

}
