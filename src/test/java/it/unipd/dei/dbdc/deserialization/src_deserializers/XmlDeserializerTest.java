package it.unipd.dei.dbdc.deserialization.src_deserializers;

import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


/**
 * Class that tests {@link XmlArticleDeserializer}.
 */
@Order(7)
public class XmlDeserializerTest {

    /**
     * Several parameters with which to test the function {@link XmlDeserializerTest#deserialize(List, String)} .
     * The different test cases are defined by:
     * {@link XmlDeserializerTest#createTestArticles1()},
     * {@link XmlDeserializerTest#createTestArticles2()},
     * {@link XmlDeserializerTest#createTestArticles3()},
     * {@link XmlDeserializerTest#createTestArticles4()},
     * {@link XmlDeserializerTest#createTestArticles5()},
     *
     */
    private static Stream<Arguments> deserializeParameters() {
        return Stream.of(
                Arguments.of(createTestArticles1(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles1.xml"),
                Arguments.of(createTestArticles2(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles2.xml"),
                Arguments.of(createTestArticles3(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles3.xml"),
                Arguments.of(createTestArticles4(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles4.xml"),
                Arguments.of(createTestArticles5(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles5.xml")

        );
    }

    /**
     * Tests {@link XmlArticleDeserializer#deserialize(File)}  with different parameters defined by {@link XmlDeserializerTest#deserializeParameters()}
     *
     * @param expectedArticles {@link List} of {@link Article} expected from deserialization
     * @param filePath The file path to deserialize
     */
    @ParameterizedTest
    @MethodSource("deserializeParameters")
    public void deserialize(List<Article> expectedArticles, String filePath) {
        XmlArticleDeserializer deserializer = new XmlArticleDeserializer();

        assertDoesNotThrow(() -> {

            File file = new File(filePath);
            List<Serializable> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(expectedArticles.size(), articles.size());
            assertEquals(expectedArticles, articles);

        });

    }

    /**
     * This utility function creates articles for testing {@link XmlArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with all fields initialized
     */
    private static List<Article> createTestArticles1() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1","Source 1"));
        return articles;
    }

    /**
     * This utility function creates articles for testing {@link XmlArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with all fields null.
     *         There are no articles in the XML file (all tags associated with fields of an Article object are missing)
     *         The basic structure of the file is still present.
     */
    private static List<Article> createTestArticles2() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(null, null,null,null,null,null, null));
        articles.add(new Article(null, null,null,null,null,null,null));
        articles.add(new Article(null, null,null,null,null,null,null));
        return articles;
    }

    /**
     * This utility function creates articles for testing {@link XmlArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with some fields null. Some tags are missing in the XML file
     */
    private static List<Article> createTestArticles3() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", null, "Title 1", "Body 1", "Date 1","sourceSet 1",null));
        articles.add(new Article("ID 1", "URL 1", null, "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", null,"sourceSet 1","Source 1"));
        return articles;
    }

    /**
     * This utility function creates articles for testing {@link XmlArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with some fields initialized and others initialized with an empty {@link String}
     *         Fields are initialized to an empty {@link String} if the tags are present in the xml file but nothing is written inside
     */
    private static List<Article> createTestArticles4() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","",""));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","",""));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","",""));
        return articles;
    }


    /**
     * This utility function creates articles for testing {@link XmlArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with all fields initialized. The file has tags in a different order than the case in {@link XmlDeserializerTest#createTestArticles1()}
     */
    private static List<Article> createTestArticles5() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1","Source 1"));
        return articles;
    }

    private static List<Article> treeArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        return articles;
    }

    /**
     * Tests {@link XmlArticleDeserializer#deserialize(File)} in particular cases
     */
    @Test
    public void deserialize_particular_cases() {

        XmlArticleDeserializer deserializer = new XmlArticleDeserializer();
        // null is passed as input
        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( null));

        // Input file does not exist
        File nonExistentFile = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/nonExistentFile.xml");
        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( nonExistentFile));

        // Deserializing an empty XML file
        File emptyFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/emptyArticles.xml");
        assertThrows(IOException.class, () -> deserializer.deserialize((emptyFile)));


        // Deserialize an empty XML file but with a correct basic structure
        assertDoesNotThrow(() -> {
            File emptyCorrectFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/emptyNoError.xml");
            List<Serializable> articles = deserializer.deserialize(emptyCorrectFile);
            assertTrue(articles.isEmpty());
        });

        // The file does not specify Article objects
        assertDoesNotThrow(() -> {
            File noArticlesFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/noArticles.xml");
            List<Serializable> articles = deserializer.deserialize(noArticlesFile);
            assertFalse(articles.isEmpty());
        });

        // XML file with a tree structure
        assertDoesNotThrow(() -> {
            File treeFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/treeArticles.xml");
            List<Serializable> articles = deserializer.deserialize(treeFile);
            assertNotEquals(treeArticles(), articles);
        });

    }

    /**
     * To set the System.out to a {@link ByteArrayOutputStream} so that we don't see the output during the tests.
     */
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

}