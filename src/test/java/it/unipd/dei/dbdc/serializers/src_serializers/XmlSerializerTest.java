package it.unipd.dei.dbdc.serializers.src_serializers;


import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.deserialization.src_deserializers.XmlArticleDeserializer;
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

public class XmlSerializerTest {

    // per testare il corretto funzionamento utilizzo la deserializzazione che utilizzo i deserializzatori ( che so che sono corretti avendoli testati a dovere);
// Teniamo la convezioe che "" e null sono la stessa cosa altrimenti impazzisco

    private static Stream<Arguments> serializeParameters() {
        return Stream.of(
                Arguments.of(createTestArticles1(), "src/test/resources/SerializationTest/serializersTest/xmlTest/Articles1.xml"),
                Arguments.of(createTestArticles2(),  "src/test/resources/SerializationTest/serializersTest/xmlTest/Articles2.xml"),
                Arguments.of(createTestArticles3(),  "src/test/resources/SerializationTest/serializersTest/xmlTest/Articles3.xml"),
                Arguments.of(createTestArticles4(),  "src/test/resources/SerializationTest/serializersTest/xmlTest/Articles4.xml")
        );
    }
    @ParameterizedTest
    @MethodSource("serializeParameters")
    public void serialize(List<Serializable> expectedArticles, String filePath) {

        XmlSerializer serializer = new XmlSerializer();
        try {
            // serializzo
            File xmlFile = new File(filePath);
            serializer.serialize(expectedArticles, xmlFile);

        } catch (IOException e) {
            fail("Errore nella serializzazione degli articoli: " + e.getMessage());
        }

        // verifico che sia corretta tanto so che i deserializzatori sono corretti

        XmlArticleDeserializer deserializer = new XmlArticleDeserializer();

        try {

            File file = new File(filePath);
            List<Serializable> articles = deserializer.deserialize(file);

            assertEquals(expectedArticles.size(), articles.size());
            assertEquals(expectedArticles, articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

    @Test
    public void serializeError() {

        XmlSerializer serializer = new XmlSerializer();

        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> serializer.serialize(createTestArticles1(), null));
        System.out.println(exception1.getMessage());

        File xmlFile = new File("src/test/resources/SerializationTest/serializersTest/xmlTest/ArticlesError.xml");
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> serializer.serialize(null, xmlFile));
        System.out.println(exception2.getMessage());
        
    }

    private static List<Serializable> createTestArticles1() {
        List<Serializable> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        return articles;
    }

    private static List<Serializable> createTestArticles2() {
        List<Serializable> articles = new ArrayList<>();
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        return articles;
    }

    private static List<Serializable> createTestArticles3() {
        List<Serializable> articles = new ArrayList<>();
        articles.add(new Article("", "","","","","", ""));
        articles.add(new Article("", "","","","","",""));
        articles.add(new Article("", "","","","","",""));
        return articles;
    }

    private static List<Serializable> createTestArticles4() {
        List<Serializable> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1",""));
        articles.add(new Article("ID 1", "URL 1", "", "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "","sourceSet 1","Source 1"));
        return articles;
    }


}