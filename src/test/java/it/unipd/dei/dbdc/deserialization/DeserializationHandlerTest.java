package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.search.Article;
import it.unipd.dei.dbdc.search.interfaces.UnitOfSearch;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
// HANDLER per mdoificare i fields devo mettere container public
public class DeserializationHandlerTest {
    private static final String deserializers_properties = "deserializers.properties";
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
            DeserializationHandler handler = new DeserializationHandler(deserializers_properties);
            handler.getFolderFiles("src/test/resources/DeserializationTest/handlerTest/Database", files);

            assertEquals(expectedAllFiles(), files);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void deleteUnavailableFiles() {

        try {
            Set<File> files = new HashSet<>();
            DeserializationHandler handler = new DeserializationHandler("deserializers.properties");

            handler.getFolderFiles("src/test/resources/DeserializationTest/handlerTest/Database", files);
            Set<File> rejectedFiles = handler.deleteUnavailableFiles(files);

            assertEquals(expectedRejectedFiles(), rejectedFiles);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getDeserializationFiles() {

        try {
            Set<File> files = new HashSet<>();
            DeserializationHandler handler = new DeserializationHandler("deserializers.properties");

            Set<File> deserializationFiles = handler.getDeserializationFiles("src/test/resources/DeserializationTest/handlerTest/Database");

            assertEquals(expectedCorrectFiles(), deserializationFiles);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<UnitOfSearch> expectedDeserializeFile() {
        List<UnitOfSearch> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));

        return articles;
    }


    @Test
    void testDeserializeFile() {

        try {
            String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
            Set<File> files = new HashSet<>();
            DeserializationHandler handler = new DeserializationHandler("deserializers.properties");
            handler.container.setSpecificFields("json", fileFields);
            List<UnitOfSearch> deserializationFiles = handler.deserializeFile(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.json"));

            assertEquals(expectedDeserializeFile(), deserializationFiles);

        } catch (IOException e) {
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

            handler.container.setSpecificFields("json", fileFields);
            List<UnitOfSearch> deserializationFolder = handler.deserializeFolder("src/test/resources/DeserializationTest/handlerTest/Database");


            assertEquals(expectedDeserializeFolder(), deserializationFolder);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}