package it.unipd.dei.dbdc.deserialization.src_deserializers;
import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.deserialization.DeserializersContainer;
import it.unipd.dei.dbdc.serializers.src_serializers.XmlSerializerTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link JsonArticleDeserializer}.
 */
@Order(7)
public class JsonDeserializerTest {

    @AfterEach
    public void setOriginalFields()  {

        JsonArticleDeserializer deserializer = new JsonArticleDeserializer();

        String[] defaultFields = {"Identifier", "URL", "Title", "Body", "Date", "Source Set", "Source"};
        deserializer.setFields(defaultFields);
    }

    /**
     * Tests {@link JsonArticleDeserializer#getFields()}
     *
     */
    @Test
    public void getFields() {

        JsonArticleDeserializer deserializer = new JsonArticleDeserializer();

        String[] expectedFields =  {"id", "apiUrl", "headline", "bodyText", "webPublicationDate", "publication", "sectionName" };
        String[] fields = deserializer.getFields();

        assertArrayEquals(expectedFields, fields);
    }

    /**
     * Tests {@link JsonArticleDeserializer#setFields(String[])}  with various valid and invalid inputs.
     *
     */
    @Test
    public void setFields() {

        String[] newFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        JsonArticleDeserializer deserializer = new JsonArticleDeserializer();

        deserializer.setFields(newFields);
        assertArrayEquals(newFields, deserializer.getFields());

        // It is possible to provide only one field to be taken into account for deserization, but the other values in the array are empty strings
        String[] newFields2 = {"id", "", "", "", "","",""};
        deserializer.setFields(newFields2);
        assertArrayEquals(newFields2, deserializer.getFields());
        // to check the real correctness
        assertDoesNotThrow(() -> {

            List<Serializable> articles = deserializer.deserialize(new File("src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles1.json"));
            assertEquals(createTestArticles7(), articles);

        });

        // --Cases where exceptions are thrown--

        // More fields are provided than defined by the Article class
        String[] newFields3 = {"ID", "Link", "Titolo", "Testo", "Data", "sourceSet", "Set ", "cover"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> deserializer.setFields(newFields3));

        //Fewer fields are provided than those defined by the Article class
        String[] newFields4 = {"id"};
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> deserializer.setFields(newFields4));

    }
    /**
     * Several parameters with which to test the function {@link XmlSerializerTest#serialize(List, String)}.
     * The different test cases are defined by:
     * {@link JsonDeserializerTest#createTestArticles1()},
     * {@link JsonDeserializerTest#createTestArticles2()},
     * {@link JsonDeserializerTest#createTestArticles3()},
     * {@link JsonDeserializerTest#createTestArticles4()},
     * {@link JsonDeserializerTest#createTestArticles5()},
     * {@link JsonDeserializerTest#createTestArticles5()}
     *
     */
    private static Stream<Arguments> deserializeParameters() {
        return Stream.of(
                Arguments.of(createTestArticles1(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles1.json"),
                Arguments.of(createTestArticles2(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles2.json"),
                Arguments.of(createTestArticles3(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles3.json"),
                Arguments.of(createTestArticles4(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles4.json"),
                Arguments.of(createTestArticles5(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles5.json"),
                Arguments.of(createTestArticles6(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles6.json")
        );
    }

    /**
     * Tests {@link JsonArticleDeserializer#deserialize(File)}  with different parameters defined by {@link JsonDeserializerTest#deserializeParameters()}
     *
     */
    @ParameterizedTest
    @MethodSource("deserializeParameters")
    public void deserialize(List<Article> expectedArticles, String filePath) {

        JsonArticleDeserializer deserializer = new JsonArticleDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        deserializer.setFields(fileFields);
        File file = new File (filePath);

        assertDoesNotThrow(() -> {

            List<Serializable> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(expectedArticles.size(), articles.size());
            assertEquals(expectedArticles, articles);

        });

    }

    /**
     * Tests {@link JsonArticleDeserializer#deserialize(File)} in particular cases
     *
     */
    @Test
    public void deserialize_other_cases() {

        JsonArticleDeserializer deserializer = new JsonArticleDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        deserializer.setFields(fileFields);

        // null is passed as input
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( null));
        System.out.println(exception1.getMessage());

        // Input file does not exist
        File nonExistentFile = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/nonExistentFile.json");
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( nonExistentFile));
        System.out.println(exception2.getMessage());

        assertDoesNotThrow(() -> {

            // Deserializing an empty JSON file
            File emptyFile = new File("src/test/resources/DeserializationTest/deserializersTest/jsonTest/emptyArticles.json");
            List<Serializable> articles = deserializer.deserialize(emptyFile);
            assertTrue(articles.isEmpty());

            // The JSON file does not specify Article objects
            File noArticles = new File("src/test/resources/DeserializationTest/deserializersTest/jsonTest/noArticles.json");
            articles = deserializer.deserialize(noArticles);
            assertTrue(articles.isEmpty());
        });

    }

    /**
     * This utility function creates articles for testing {@link CsvArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with all fields initialized
     */
    private static List<Article> createTestArticles1() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        return articles;
    }

    /**
     * This utility function creates articles for testing {@link CsvArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with all fields initialized. The test is done with a json file with a tree structure
     */
    private static List<Article> createTestArticles2() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        return articles;
    }


    /**
     * This utility function creates articles for testing {@link CsvArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with all fields null. In the JSON file the values associated with the keys are set to null
     */
    private static List<Article> createTestArticles3() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(null, null,null,null,null,null, null));
        articles.add(new Article(null, null,null,null,null,null,null));
        articles.add(new Article(null, null,null,null,null,null,null));
        return articles;
    }


    /**
     * This utility function creates articles for testing {@link CsvArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with some fields null. Some keys are missing in the JSON file
     */
    private static List<Article> createTestArticles4() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1",null));
        articles.add(new Article("ID 1", "URL 1", null, "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", null,"sourceSet 1","Source 1"));
        return articles;
    }


    /**
     * This utility function creates articles for testing {@link CsvArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with all fields initialized. The test is performed with a JSON file that has wrong formatting (logically)
     *         The interpretation of the file is pertinent to the modalities explained in {@link JsonArticleDeserializer#deserialize(File)}
     */
    private static List<Article> createTestArticles5() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 5", "URL 5", "Title 5", "Body 5", "Date 5","sourceSet 5","Source 5"));
        return articles;
    }


    /**
     * This utility function creates articles for testing {@link CsvArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with some fields null.
     *         Some keys are missing in the JSON file.
     *         Attention the id cannot be missing otherwise the article will not be deserialized
     */
    private static List<Article> createTestArticles6() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1",null,null));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1",null,null));
        return articles;
    }

    /**
     * This utility function creates articles for testing {@link JsonArticleDeserializer#getFields()}
     *
     * @return list of {@link Article}.
     */
    private static List<Article> createTestArticles7() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", null, null, null, null,null,null));
        articles.add(new Article("ID 1", null, null, null, null,null,null));
        articles.add(new Article("ID 1", null, null, null, null,null,null));
        return articles;
    }

}
