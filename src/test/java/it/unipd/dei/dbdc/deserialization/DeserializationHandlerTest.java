package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.CsvArticleDeserializer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.JsonArticleDeserializer;
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

    private static List<Article> expectedDeserializeFile() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));

        return articles;
    }


    @Test
    void testDeserializeFile() {
        try {

            Set<File> files = new HashSet<>();
            DeserializationHandler handler = new DeserializationHandler(deserializers_properties);

            // si settano i fields del file
            String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
            DeserializersContainer container = (DeserializersContainer) cont_field.get(handler);
            container.setSpecificFields("json", fileFields);

            String filePath = "src/test/resources/DeserializationTest/handlerTest/Database/Articles1.json";
            List<Serializable> deserializationFiles = handler.deserializeFile(new File(filePath));

            assertEquals(expectedDeserializeFile(), deserializationFiles);

            // caso null
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> handler.deserializeFile(null));
            System.out.println(exception);

        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<UnitOfSearch> expectedDeserializeFolder() {

        Set<File> expectedFiles = expectedCorrectFiles();
        //System.out.println(expectedFiles.size());
        List<UnitOfSearch> articles = new ArrayList<>();
        for(int i = 0; i < expectedFiles.size(); i++){
            articles.addAll(expectedDeserializeFile());
        }
        return articles;
    }
    @Test
    void testDeserializeFolder() {
        try {
            Set<File> files = new HashSet<>();
            String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
            DeserializationHandler handler = new DeserializationHandler(deserializers_properties);

            DeserializersContainer container = (DeserializersContainer) cont_field.get(handler);
            container.setSpecificFields("json", fileFields);

            List<Serializable> deserializationFolder = handler.deserializeFolder("src/test/resources/DeserializationTest/handlerTest/Database");
            assertEquals(expectedDeserializeFolder(), deserializationFolder);

            // caso null
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> handler.deserializeFolder(null));
            System.out.println(exception);

        } catch (IOException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    //Function to restore the standard input
    @AfterAll
    public static void restoreSystemInputOutput() {
        System.setIn(System.in);
    }

    /**
     * To set the System.out to a {@link ByteArrayOutputStream} so that we don't see the output during the tests.
     */
    @BeforeEach
    public void setUpOutput() {
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
    }

    /**
     * To restore the System.out
     */
    @AfterEach
    public void restoreSystemOutput() {
        System.setOut(System.out);
    }

    @Test
    void deserializerSetFields() {

        assertDoesNotThrow(() -> {

            DeserializationHandler handler = new DeserializationHandler(deserializers_properties);
            String[] fileFields = {"id1" , "url1" , "title1" , "body1" , "date1" , "sourceSet1", "source1"};
            provideInput("json\nid1\nurl1\ntitle1\nbody1\ndate1\nsourceSet1\nsource1");
            handler.deserializerSetFields();

            DeserializersContainer container = (DeserializersContainer) cont_field.get(handler);
            assertArrayEquals(container.getSpecificFields("json"),fileFields);
        });

    }


}