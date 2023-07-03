package it.unipd.dei.dbdc.serializers.src_serializers;


import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.src_deserializers.JsonDeserializer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.XmlDeserializer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
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
    public void serialize(List<UnitOfSearch> expectedArticles, String filePath) {

        XmlSerializer serializer = new XmlSerializer();
        try {
            // serializzo
            serializer.serialize(expectedArticles, filePath);

        } catch (IOException e) {
            fail("Errore nella serializzazione degli articoli: " + e.getMessage());
        }

        // verifico che sia corretta tanto so che i deserializzatori sono corretti

        XmlDeserializer deserializer = new XmlDeserializer();

        try {

            File file = new File(filePath);
            List<UnitOfSearch> articles = deserializer.deserialize(file);

            assertEquals(expectedArticles.size(), articles.size());
            assertEquals(expectedArticles, articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

    @Test
    public void serializeError() {

        XmlSerializer serializer = new XmlSerializer();

        String filePath = "src/test/resources/SerializationTest/serializersTest/xmlTest/ArticlesError.xml";
        NullPointerException exception1 = assertThrows(NullPointerException.class, () -> serializer.serialize(createTestArticles1(), null));
        System.out.println(exception1.getMessage());

        assertDoesNotThrow(() -> serializer.serialize(null, filePath));

        String filePath2 = "src/test/resources/SerializationTest/serializersTest/xmlTest";
        assertDoesNotThrow(() -> serializer.serialize(createTestArticles1(), filePath2));

    }

    private static List<UnitOfSearch> createTestArticles1() {
        List<UnitOfSearch> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        return articles;
    }

    private static List<UnitOfSearch> createTestArticles2() {
        List<UnitOfSearch> articles = new ArrayList<>();
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        return articles;
    }

    private static List<UnitOfSearch> createTestArticles3() {
        List<UnitOfSearch> articles = new ArrayList<>();
        articles.add(new Article("", "","","","","", ""));
        articles.add(new Article("", "","","","","",""));
        articles.add(new Article("", "","","","","",""));
        return articles;
    }

    private static List<UnitOfSearch> createTestArticles4() {
        List<UnitOfSearch> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1",""));
        articles.add(new Article("ID 1", "URL 1", "", "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "","sourceSet 1","Source 1"));
        return articles;
    }




}