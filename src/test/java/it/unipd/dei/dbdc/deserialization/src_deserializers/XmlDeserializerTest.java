package it.unipd.dei.dbdc.deserialization.src_deserializers;

import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import static org.junit.jupiter.api.Assertions.*;

import it.unipd.dei.dbdc.deserialization.DeserializationProperties;
import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;


@Disabled
public class XmlDeserializerTest {

    private static List<Article> treeArticles() {
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
    public void deserialize_particular_cases() {

        XmlDeserializer deserializer = new XmlDeserializer();

        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( null));
        System.out.println(exception1.getMessage());

        // gli viene dato un file che non esistente
        File nonExistentFile = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/nonExistentFile.xml");
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( nonExistentFile));
        System.out.println(exception2.getMessage());


        File emptyFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/emptyArticles.xml");
        IOException exception3 = assertThrows(IOException.class, () -> deserializer.deserialize((emptyFile)));
        System.out.println(exception3.getMessage());

        assertDoesNotThrow(() -> {
            File emptyCorrectFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/emptyNoError.xml");
            List<UnitOfSearch> articles = deserializer.deserialize(emptyCorrectFile);
            assertTrue(articles.isEmpty());
        });


        assertDoesNotThrow(() -> {
            File noArticlesFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/noArticles.xml");
            List<UnitOfSearch> articles = deserializer.deserialize(noArticlesFile);
            assertFalse(articles.isEmpty());
        });

        // non lancia errore ma non è in grado di deserializzare
        File treeFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/treeArticles.xml");
        try {
            List<UnitOfSearch> articles = deserializer.deserialize(treeFile);
            assertNotEquals(treeArticles(), articles);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        /*
        // albero semplice con piu cose annidate come una scatola va bene
        assertDoesNotThrow(() -> {
            File correctTreeFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/correctTreeArticles.xml");
            File wrongTreeFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/wrongTreeArticles.xml");
            List<UnitOfSearch> articles1 = deserializer.deserialize(wrongTreeFile);
            List<UnitOfSearch> articles2 = deserializer.deserialize(correctTreeFile);

            assertEquals(treeArticles(), articles2);
        });
/*
        // se è un xml ad albero complesso non è in grado di farlo al contrario di json o csv
        File wrongTreeFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/wrongTreeArticles.xml");
        IOException exception4 = assertThrows(IOException.class, () -> deserializer.deserialize((wrongTreeFile)));
        System.out.println(exception4.getMessage());
*/

        /*
        File noArticlesFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/noArticles.xml");
        IOException exception5 = assertThrows(IOException.class, () -> deserializer.deserialize((noArticlesFile)));
        System.out.println(exception5.getMessage());

        File emptyFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/emptyArticles.xml");
        IOException exception4 = assertThrows(IOException.class, () -> deserializer.deserialize((emptyFile)));
        System.out.println(exception4.getMessage());

        // questo è comunque un file vuoto ma ha la struttura corretta quindi va bene
        assertDoesNotThrow(() -> {
            File emptyCorrectFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/emptyNoError.xml");
            List<UnitOfSearch> articles = deserializer.deserialize(emptyCorrectFile);
            assertTrue(articles.isEmpty());
        });

        // se è un xml ad albero non è in grado di farlo al contrario di json o csv
        File treeFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/treeArticles.xml");
        IOException exception3 = assertThrows(IOException.class, () -> deserializer.deserialize((treeFile)));
        System.out.println(exception3.getMessage());
        */

        // file con struttura corretta ma non ci sono articoli
        assertDoesNotThrow(() -> {
            File noArticlesFile = new File("src/test/resources/DeserializationTest/deserializersTest/xmlTest/noArticles.xml");
            List<UnitOfSearch> articles = deserializer.deserialize(noArticlesFile);
            assertFalse(articles.isEmpty());
        });
    }



    private static Stream<Arguments> testParameters() {
        return Stream.of(
                Arguments.of(createTestArticles1(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles1.xml"),
                Arguments.of(createTestArticles2(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles2.xml"),
                Arguments.of(createTestArticles3(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles3.xml"),
                Arguments.of(createTestArticles4(), "src/test/resources/DeserializationTest/deserializersTest/xmlTest/Articles4.xml"),
                Arguments.of(createTestArticles5(), "src/test/resources/SerializationTest/serializersTest/xmlTest/Articles5.xml")
        );
    }

    @ParameterizedTest
    @MethodSource("testParameters")
    public void deserialize(List<Article> expectedArticles, String filePath) {
        XmlDeserializer deserializer = new XmlDeserializer();

        assertDoesNotThrow(() -> {

            File file = new File(filePath);
            List<UnitOfSearch> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(expectedArticles.size(), articles.size());
            assertEquals(expectedArticles, articles);

        });

        /*
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
        */

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

    private static List<UnitOfSearch> createTestArticles5() {
        List<UnitOfSearch> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        return articles;
    }

}