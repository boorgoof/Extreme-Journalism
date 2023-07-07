package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.CsvArticleDeserializer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.JsonArticleDeserializer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.XmlArticleDeserializer;
import it.unipd.dei.dbdc.download.DownloadHandler;
import it.unipd.dei.dbdc.download.src_api_managers.TheGuardianAPI.GuardianAPIManagerTest;
import it.unipd.dei.dbdc.serializers.SerializationProperties;
import org.junit.jupiter.api.*;

import java.io.*;
import java.lang.reflect.Field;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
// HANDLER per mdoificare i fields devo mettere container public

public class DeserializationHandlerTest {
    private static final String deserializers_properties = "src/test/resources/DeserializationTest/properties/deserializers.properties";
    private static Field cont_field;


    @BeforeAll
    public static void initialize()
    {
        try {
            cont_field = DeserializationHandler.class.getDeclaredField("container");
        } catch (NoSuchFieldException e) {
            fail("Errore nella reflection");
        }
        cont_field.setAccessible(true);
    }

    @AfterAll
    public static void terminate()
    {
        cont_field.setAccessible(true);
    }
   /*

    private static Set<File> expectedAllFiles() {
        Set<File> files = new HashSet<>();
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.csv"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.json"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.xml"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/Articles1.html"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.txt"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles2.txt"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/Articles1.pdf"));
        return files;
    }
    private static Set<File> expectedRejectedFiles() {
        Set<File> files = new HashSet<>();
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.txt"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles2.txt"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/Articles1.pdf"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/Articles1.html"));
        return files;
    }
    private static Set<File> expectedCorrectFiles() {
        Set<File> files = new HashSet<>();
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.csv"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.json"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.xml"));
        return files;
    }

    @Test
    void getFolderFiles() {

        try {
            Set<File> files = new HashSet<>();
            String folderPath = "src/test/resources/DeserializationTest/handlerTest/Database";
            DeserializationHandler handler = new DeserializationHandler(deserializers_properties);

            handler.getFolderFiles(folderPath, files);
            assertEquals(expectedAllFiles(), files);

            IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> handler.getFolderFiles(null, files));
            System.out.println(exception1.getMessage());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> handler.getFolderFiles(folderPath, null));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void deleteUnavailableFiles() {

        try {
            Set<File> files = new HashSet<>();
            DeserializationHandler handler = new DeserializationHandler(deserializers_properties);
            handler.getFolderFiles("src/test/resources/DeserializationTest/handlerTest/Database", files);

            // qui
            Set<File> rejectedFiles = handler.deleteUnavailableFiles(files);
            assertEquals(expectedRejectedFiles(), rejectedFiles);

            // caso in cui gli passo null
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> handler.deleteUnavailableFiles(null));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getDeserializationFiles() {

        try {
            Set<File> files = new HashSet<>();
            DeserializationHandler handler = new DeserializationHandler(deserializers_properties);
            String folderDatabase = "src/test/resources/DeserializationTest/handlerTest/Database";
            Set<File> deserializationFiles = handler.getDeserializationFiles(folderDatabase);
            assertEquals(expectedCorrectFiles(), deserializationFiles);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> handler.getDeserializationFiles(null));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
*/

    /**
     * This utility function creates articles for testing {@link DeserializationHandler#deserializeFile(File)}.
     *
     * @return list of {@link Article}. They are all the {@link Article} objects that are expected to be deserialized from the file
     */
    private static List<Article> expectedDeserializeFile() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));

        return articles;
    }

    /**
     * Tests {@link DeserializationHandler#deserializeFile(File)}
     *
     */
    @Test
    void testDeserializeFile() {
        try {

            // sets the properties file
            DeserializationHandler.setProperties(deserializers_properties);

            // sets the fields of the json file
            String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
            DeserializersContainer container = (DeserializersContainer) cont_field.get(DeserializationHandler.class);
            container.setSpecificFields("json", fileFields);

            // deserialization of the file
            String filePath = "src/test/resources/DeserializationTest/handlerTest/Database/Articles1.json";
            List<Serializable> deserializedFile = DeserializationHandler.deserializeFile(new File(filePath));
            assertEquals(expectedDeserializeFile(), deserializedFile);

            // case I pass it null as input
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> DeserializationHandler.deserializeFile(null));
            System.out.println(exception);

        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * This utility function creates articles for testing {@link DeserializationHandler#deserializeFolder(String)}.
     * This function is used in {@link DeserializationHandlerTest#deserializedArticleFolder()}
     *
     * @return list of {@link File}. They are all the files that are expected to be deserialized
     */
    private static Set<File> expectedCorrectFolderFiles() {
        Set<File> files = new HashSet<>();
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.csv"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.json"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.xml"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/Articles1.json"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/Articles1.csv"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/Articles1.xml"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/database3/Articles1.json"));
        return files;
    }

    /**
     * This utility function creates articles for testing {@link DeserializationHandler#deserializeFolder(String)}.
     *
     * @return list of {@link Serializable} objects that are instances of {@link Article}. They are  all the {@link Article} objects expected from deserializing the test folder
     */
    private static List<Serializable> deserializedArticleFolder() {

        Set<File> expectedFiles = expectedCorrectFolderFiles();
        //System.out.println(expectedFiles.size());
        List<Serializable> articles = new ArrayList<>();
        for(int i = 0; i < expectedFiles.size(); i++){
            articles.addAll(expectedDeserializeFile());
        }
        return articles;
    }

    /**
     * Tests {@link DeserializationHandler#deserializeFolder(String)}
     *
     */
    @Test
    void testDeserializeFolder() {

        try {

            String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
            DeserializationHandler.setProperties(deserializers_properties);

            // sets the fields of the json file, it is necessary because in the file they are different from the default ones
            DeserializersContainer container = (DeserializersContainer) cont_field.get(DeserializationHandler.class);
            container.setSpecificFields("json", fileFields);

            List<Serializable> DeserializedFolder = DeserializationHandler.deserializeFolder("src/test/resources/DeserializationTest/handlerTest/Database");
            assertEquals(deserializedArticleFolder(), DeserializedFolder);

            // caso in cui vengono usate le properties di default

            // case I pass it null as input
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> DeserializationHandler.deserializeFolder(null));
            System.out.println(exception);

        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

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
    /*
    @BeforeEach
    public void setUpOutput() {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }

    /**
     * To restore the System.out and System.in
     */
    @AfterEach
    public void restoreSystemInputOutput() {
        System.setIn(System.in);
        System.setOut(System.out);
    }

    /**
     * Tests {@link DeserializationHandler#deserializerSetFields()} with various valid and invalid inputs.
     *
     */
    @Test
    void deserializerSetFields() {

        // valid case: json
        assertDoesNotThrow(() -> {

            String[] newFields = {"id1" , "url1" , "title1" , "body1" , "date1" , "sourceSet1", "source1"};
            provideInput("json\nid1\nurl1\ntitle1\nbody1\ndate1\nsourceSet1\nsource1");
            DeserializationHandler.deserializerSetFields();

            DeserializersContainer container = (DeserializersContainer) cont_field.get(DeserializationHandler.class);
            assertArrayEquals(container.getSpecificFields("json"),newFields);
        });

        // valid case: csv
        assertDoesNotThrow(() -> {

            String[] newFields = {"id2" , "url2" , "title2" , "body2" , "date2" , "sourceSet2", "source2"};
            provideInput("csv\nid2\nurl2\ntitle2\nbody2\ndate2\nsourceSet2\nsource2");
            DeserializationHandler.deserializerSetFields();

            DeserializersContainer container = (DeserializersContainer) cont_field.get(DeserializationHandler.class);
            assertArrayEquals(container.getSpecificFields("csv"),newFields);
        });

        // invalid case: xml
        provideInput("xml\nid1\nurl1\ntitle1\nbody1\ndate1\nsourceSet1\nsource1");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, DeserializationHandler::deserializerSetFields);
        System.out.println(exception);

    }

}