package it.unipd.dei.dbdc.deserialization.src_deserializers;

import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@Disabled
public class XmlDeserializerTest {

    private static List<Article> createTestArticlesError() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        return articles;
    }

    /*
         ERRORE ASPETTATO. SE VOLETE POSSIAMO FARE CHE SI ASPETTA UNA LISTA VUOTA MA PER ME é MEGLIO ERRORE
         il file XML ha una struttura complessa che richiede una deserializzazione personalizzata,
         Per deserializzare questo tipo di file si puo' estendere la classe XmlDeserializer  e personalizzare il processo di deserializzazione per gestire ù
         una struttura specifica del file XML o comunque strutture più complesse.
     */

    @Test
    public void deserializeExpetedError() {

        XmlDeserializer deserializer = new XmlDeserializer();
        File file = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/ArticlesError.xml");
        IOException e = assertThrows(IOException.class, () -> deserializer.deserialize((file)));
        System.out.println(e.getMessage());


        /*
        try {
            File file = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/ArticlesError.xml");

            List<UnitOfSearch> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals( createTestArticlesError().size(), articles.size());
            assertEquals(createTestArticlesError(), articles);

        } catch (IOException e) {
            // Se si verifica un'eccezione, verifica se è l'eccezione desiderata

            assertTrue(true);
            return;
        }
        // Se non viene sollevata alcuna eccezione, il test fallisce
        fail("nessuna eccezione è stata sollevata durante la lettura del file XML. Si aspettava una eccezione IOException");
        */

    }



    private static Stream<Arguments> testParameters() {
        return Stream.of(
                Arguments.of(createTestArticles1(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles1.xml"),
                Arguments.of(createTestArticles2(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles2.xml"),
                Arguments.of(createTestArticles3(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles3.xml"),
                Arguments.of(createTestArticles4(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles4.xml"));
    }

    @ParameterizedTest
    @MethodSource("testParameters")
    public void deserialize(List<Article> expectedArticles, String filePath) {
        XmlDeserializer deserializer = new XmlDeserializer();

        try {

            File file = new File(filePath);
            List<UnitOfSearch> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(expectedArticles.size(), articles.size());
            assertEquals(expectedArticles, articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file XML: " + e.getMessage());
        }
    }


    private static List<Article> createTestArticles1() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1","Source 1"));
        return articles;
    }

    private static List<Article> createTestArticles2() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(null, null,null,null,null,null, null));
        articles.add(new Article(null, null,null,null,null,null,null));
        articles.add(new Article(null, null,null,null,null,null,null));
        return articles;
    }


    private static List<Article> createTestArticles3() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", null, "Title 1", "Body 1", "Date 1","sourceSet 1",null));
        articles.add(new Article("ID 1", "URL 1", null, "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", null,"sourceSet 1","Source 1"));
        return articles;
    }


    private static List<Article> createTestArticles4() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","",""));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","",""));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","",""));
        return articles;
    }

}