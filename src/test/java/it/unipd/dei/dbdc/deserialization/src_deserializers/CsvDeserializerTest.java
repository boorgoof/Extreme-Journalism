package it.unipd.dei.dbdc.deserialization.src_deserializers;

import it.unipd.dei.dbdc.analysis.Article;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.ParameterizedTest;


import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link CsvArticleDeserializer}.
 */
@Order(7)
public class CsvDeserializerTest {

    /**
     * The function sets the default fields for {@link CsvArticleDeserializer}
     */
    @AfterEach
    public void setOriginalFields()  {

        CsvArticleDeserializer deserializer = new CsvArticleDeserializer();

        String[] defaultFields = {"Identifier", "URL", "Title", "Body", "Date", "Source Set", "Source"};
        deserializer.setFields(defaultFields);
    }

    /**
     * Tests {@link CsvArticleDeserializer#getFields()}
     *
     */
    @Test
    public void getFields() {

        CsvArticleDeserializer deserializer = new CsvArticleDeserializer();

        String[] expectedFields = {"Identifier","URL","Title","Body","Date","Source Set","Source"};
        String[] fields = deserializer.getFields();

        assertArrayEquals(expectedFields, fields);
    }

    /**
     * Tests {@link CsvArticleDeserializer#setFields(String[])}  with various valid and invalid inputs.
     *
     */
    @Test
    public void setFields() {

        CsvArticleDeserializer deserializer = new CsvArticleDeserializer();

        String[] newFields = {"ID", "Link", "Titolo", "Testo", "Data", "Fonte", "Set di fonti"};
        deserializer.setFields(newFields);
        assertArrayEquals(newFields, deserializer.getFields());

        // It is possible to provide only one field to be taken into account for deserization, but the other values in the array are empty strings
        String[] newFields2 = {"Identifier", "", "", "", "","",""};
        deserializer.setFields(newFields2);
        assertArrayEquals(newFields2, deserializer.getFields());
        // to check the real correctness
        assertDoesNotThrow(() -> {

            List<Serializable> articles = deserializer.deserialize(new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles1.csv"));
            assertEquals(createTestArticles6(), articles);

        });

        // --Cases where exceptions are thrown--

        // More fields are provided than defined by the Article class
        String[] newFields3 = {"ID", "Link", "Titolo", "Testo", "Data", "Fonte", "Set di fonti", "cover"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> deserializer.setFields(newFields3));

        //Fewer fields are provided than those defined by the Article class
        String[] newFields4 = {"ID"};
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> deserializer.setFields(newFields4));

    }

    /**
     * Several parameters with which to test the function {@link XmlDeserializerTest#deserialize(List, String)} .
     * The different test cases are defined by:
     * {@link CsvDeserializerTest#createTestArticles1()},
     * {@link CsvDeserializerTest#createTestArticles2()},
     * {@link CsvDeserializerTest#createTestArticles3()},
     * {@link CsvDeserializerTest#createTestArticles4()},
     * {@link CsvDeserializerTest#createTestArticles5()},
     *
     */
    private static Stream<Arguments> deserializeParameters() {
        return Stream.of(
                Arguments.of(createTestArticles1(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles1.csv"),
                Arguments.of(createTestArticles2(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles2.csv"),
                Arguments.of(createTestArticles3(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles3.csv"),
                Arguments.of(createTestArticles4(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles4.csv"),
                Arguments.of(createTestArticles5(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles5.csv")
        );
    }

    /**
     * Tests {@link CsvArticleDeserializer#deserialize(File)}  with different parameters defined by {@link CsvDeserializerTest#deserializeParameters()}
     *
     * @param expectedArticles the {@link List}of {@link Article} expected from deserialization
     * @param filePath the file path to deserialize
     */
    @ParameterizedTest
    @MethodSource("deserializeParameters")
    public void deserialize(List<Article> expectedArticles, String filePath) {
        File file = new File(filePath);
        CsvArticleDeserializer deserializer = new CsvArticleDeserializer();

        assertDoesNotThrow(() -> {

            List<Serializable> articles = deserializer.deserialize(file);
            assertTrue(articles.get(1) instanceof Article);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(expectedArticles.size(), articles.size());
            assertEquals(expectedArticles, articles);

        });

    }

    /**
     * Tests {@link CsvArticleDeserializer#deserialize(File)} in particular cases
     *
     */
    @Test
    public void deserialize_particular_cases() {

        CsvArticleDeserializer deserializer = new CsvArticleDeserializer();

        // null is passed as input
        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( null));

        // Input file does not exist
        File nonExistentFile = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/nonExistentFile.csv");
        assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( nonExistentFile));



        assertDoesNotThrow(() -> {

            // Deserializing an empty CSV file
            File emptyFile = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/emptyArticles.csv");
            List<Serializable> articles = deserializer.deserialize(emptyFile);
            assertTrue(articles.isEmpty());

            // The file does not specify Article objects
            File noArticlesFile = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/noArticles.csv");
            articles = deserializer.deserialize(noArticlesFile);
            assertTrue(articles.isEmpty());

            // The file does not specify Article objects. And the records are empty
            File noArticlesFile2 = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/noArticles2.csv");
            articles = deserializer.deserialize(noArticlesFile2);
            assertTrue(articles.isEmpty());

            // correct header but empty cvs records
            File noArticlesFile3 = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/noArticles3.csv");
            articles = deserializer.deserialize(noArticlesFile3);
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
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        return articles;
    }

    /**
     * This utility function creates articles for testing {@link CsvArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with some fields null (fields associated with null values were not present in the header of the CSV file)
     */
    private static List<Article> createTestArticles2() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", null, "Title 1", "Body 1", "Date 1","SourceSet 1",null));
        articles.add(new Article("ID 1", null, "Title 1", "Body 1", "Date 1","SourceSet 1",null));
        articles.add(new Article("ID 1", null, "Title 1", "Body 1", "Date 1","SourceSet 1",null));
        return articles;
    }
    /**
     * This utility function creates articles for testing {@link CsvArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with some fields null. In particular, Date is not present in the header
     *         In the test the fields are in a different order than the one specified by default by {@link CsvArticleDeserializer}
     *         However, the deserialization is correct
     */
    private static List<Article> createTestArticles3() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", null,"SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", null,"SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", null,"SourceSet 1","Source 1"));
        return articles;
    }

    /**
     * This utility function creates articles for testing {@link CsvArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with some fields initialized and others initialized with an empty {@link String}
     *         Fields are initialized to an empty {@link String} if they are specified in the file header, that item's record in the csv is empty
     */
    private static List<Article> createTestArticles4() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","","Source 1"));
        return articles;
    }

    /**
     * This utility function creates articles for testing {@link CsvArticleDeserializer#deserialize(File)}.
     *
     * @return list of {@link Article}. Three Article objects with some fields initialized, some initialized with an empty {@link String} and some initialized with a null value
     *         The fields of an article object are initialized to null if they are not specified in the header of the CSV file.
     *         Fields are initialized to an empty {@link String} if they are specified in the file header, that item's record is empty
     */
    private static List<Article> createTestArticles5() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "", "Body 1", "",null,"Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "", "",null,"Source 1"));
        articles.add(new Article("", "URL 1", "Title 1", "Body 1", "Date 1",null,"Source 1"));
        return articles;
    }
    /**
     * This utility function creates articles for testing {@link CsvArticleDeserializer#getFields()}
     *
     * @return list of {@link Article}.
     */
    private static List<Article> createTestArticles6() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", null, null, null, null,null,null));
        articles.add(new Article("ID 1", null, null, null, null,null,null));
        articles.add(new Article("ID 1", null, null, null, null,null,null));
        return articles;
    }

    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created.
     */
    private CsvDeserializerTest() {}
}