package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.tools.PathManager;
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
@Order(14)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest
{

    /**
     * Initializes by setting the database folder to the one that this class will access
     *
     */
    @BeforeAll
    public static void initialize()
    {
        PathManager.setDatabaseFolder(folder+"database/");
    }

    /**
     * Restores the output and database folders
     *
     */
    @AfterAll
    public static void end()
    {
        PathManager.setOutputFolder("./output/");
        PathManager.setDatabaseFolder("./database/");
    }

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
     * This is the second function that is executed.
     * Checks the output of the application if everything goes right. It is done by checking if the output file is the one we expect.
     * The output file is only tested for the ny times articles, as the ones downloaded from the TheGuardianAPI can vary a lot.
     * It uses {@link PathManagerTest#readFile(String)} to read the output file.
     */
    @Order(2)
    @Test
    public void mainTestOut() {

        PathManager.setOutputFolder(folder+"output_theguardian/");

        //DOWNLOAD OF ARTICLES
        App.main(new String[]{"-d", "-apf", folder+"api.properties"});

        File[] files = new File(folder+"output_theguardian/").listFiles();
        assertNotNull(files);
        File out = files[1];
        assertEquals("serialized.xml", out.getName());

        //Analysis of the downloaded files
        App.main(new String[]{"-a"});

        files = new File(folder+"output_theguardian/").listFiles();
        assertNotNull(files);
        assertEquals(2, files.length);
        out = files[1];
        assertEquals("serialized.xml", out.getName());

        out = files[0];
        assertEquals("output.txt", out.getName());
        //We don't test the output as the output can vary depending on what is in the database of the TheGuardian

        PathManager.setOutputFolder(folder+"output_nytimes/");

        //Analysis and "download" of nytimes
        App.main(new String[]{"-da", "-path", folder+"nytimes_articles_v2"});

        files = new File(folder+"output_nytimes/").listFiles();
        assertNotNull(files);
        assertEquals(2, files.length);
        out = files[1];
        assertEquals("serialized.xml", out.getName());
        out = files[0];
        assertEquals("output.txt", out.getName());
        assertEquals(PathManagerTest.readFile(folder+"output_nytimes_v2.txt"), PathManagerTest.readFile(folder+"output_nytimes/output.txt"));

    }

    /**
     * This is the first function that is executed.
     * Checks some of the errors that are thrown when something goes wrong.
     */
    @Order(1)
    @Test
    public void mainTestErr() {

        //The APIContainer is initialized with trueDownload.properties by all the test classes that use it
        //HELP
        App.main(new String[]{"-h"});
        assertEquals("", getError());

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

        //The APIContainer and DeserializersContainer and SerializersContainer have already been initialized, so we can't try to pass illegal properties to them (they use the Singleton design pattern).

    }

    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created.
     */
    private AppTest() {}
}
