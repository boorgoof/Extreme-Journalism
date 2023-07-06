package it.unipd.dei.dbdc.serializers.src_serializers;


import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.deserialization.src_deserializers.XmlArticleDeserializer;
import it.unipd.dei.dbdc.download.DownloadProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link XmlSerializer}.
 */
public class XmlSerializerTest {
    /**
     * Several parameters with which to test the function {@link XmlSerializerTest#serialize(List, String)}.
     * The different test cases are defined by:
     * {@link XmlSerializerTest#createTestArticles1()},
     * {@link XmlSerializerTest#createTestArticles2()},
     * {@link XmlSerializerTest#createTestArticles3()},
     * {@link XmlSerializerTest#createTestArticles4()}.
     *
     */
    private static Stream<Arguments> serializeParameters() {
        return Stream.of(
                Arguments.of(createTestArticles1(), "src/test/resources/SerializationTest/serializersTest/xmlTest/Articles1.xml"),
                Arguments.of(createTestArticles2(),  "src/test/resources/SerializationTest/serializersTest/xmlTest/Articles2.xml"),
                Arguments.of(createTestArticles3(),  "src/test/resources/SerializationTest/serializersTest/xmlTest/Articles3.xml"),
                Arguments.of(createTestArticles4(),  "src/test/resources/SerializationTest/serializersTest/xmlTest/Articles4.xml")
        );
    }

    /**
    * Tests {@link XmlSerializer#serialize(List, File)} with different parameters defined by {@link XmlSerializerTest#serializeParameters}
    *
     */
    @ParameterizedTest
    @MethodSource("serializeParameters")
    public void serialize(List<Serializable> expectedArticles, String filePath) {

        // To test the correct functioning of the serializers I use the deserialization (the deserializers have already been tested properly)
        File xmlFile = new File(filePath);
        // Serialization
        try {

            XmlSerializer serializer = new XmlSerializer();

            serializer.serialize(expectedArticles, xmlFile);

        } catch (IOException e) {
            fail("Error during serialization: " + e.getMessage());
        }

        // To verify that the content of the articles has been serialized correctly.
        // I should get the same objects Articles with deserialising the serialized file
        try {

            XmlArticleDeserializer deserializer = new XmlArticleDeserializer();
            List<Serializable> articles = deserializer.deserialize(xmlFile);

            assertEquals(expectedArticles.size(), articles.size());
            assertEquals(expectedArticles, articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

    /**
     * Tests {@link XmlSerializer#serialize(List, File)} with null parameters
     */
    @Test
    public void serializeError() {

        XmlSerializer serializer = new XmlSerializer();
        String messageError1 = "The xmlFile file cannot be null";
        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> serializer.serialize(createTestArticles1(), null), messageError1);

        File xmlFile = new File("src/test/resources/SerializationTest/serializersTest/xmlTest/ArticlesError.xml");
        String messageError2 = "The list of UnitOfSearch cannot be null";
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> serializer.serialize(null, xmlFile), messageError2);

    }
    /**
     * The function creates articles for testing {@link XmlSerializer#serialize(List, File)}.
     *
     * @return list of {@link Serializable} objects that are instances of {@link Article} to serialize. Three Article objects with all fields initialized
     */
    private static List<Serializable> createTestArticles1() {
        List<Serializable> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        return articles;
    }
    /**
     * The function creates articles for testing {@link XmlSerializer#serialize(List, File)}
     *
     * @return list of {@link Serializable} objects that are instances of {@link Article} to serialize.
     *         Three Article objects with all fields initialized
     */
    private static List<Serializable> createTestArticles2() {
        List<Serializable> articles = new ArrayList<>();
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        return articles;
    }
    /**
     * The function creates articles for testing {@link XmlSerializer#serialize(List, File)}
     *
     * @return list of {@link Serializable} objects that are instances of {@link Article} to serialize.
     *         Three Article objects with all fields initialized with an empty {@link String}
     */
    private static List<Serializable> createTestArticles3() {
        List<Serializable> articles = new ArrayList<>();
        articles.add(new Article("", "","","","","", ""));
        articles.add(new Article("", "","","","","",""));
        articles.add(new Article("", "","","","","",""));
        return articles;
    }
    /**
     * The function creates articles for testing {@link XmlSerializer#serialize(List, File)}
     *
     * @return list of {@link Serializable} objects that are instances of {@link Article} to serialize.
     *         Three Article objects with some fields initialized and others initialized with an empty {@link String}
     */
    private static List<Serializable> createTestArticles4() {
        List<Serializable> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1",""));
        articles.add(new Article("ID 1", "URL 1", "", "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "","sourceSet 1","Source 1"));
        return articles;
    }


}