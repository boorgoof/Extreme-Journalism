package it.unipd.dei.dbdc.deserialization.src_deserializers;

import it.unipd.dei.dbdc.analysis.Article;
import org.junit.jupiter.api.Disabled;
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

@Disabled

public class CsvDeserializerTest {

    @Test
    public void getFields() {

        CsvArticleDeserializer deserializer = new CsvArticleDeserializer();

        String[] expectedFields = {"Identifier","URL","Title","Body","Date","Source Set","Source"};
        String[] fields = deserializer.getFields();

        assertArrayEquals(expectedFields, fields);
    }

    @Test
    public void setFields() {

        CsvArticleDeserializer deserializer = new CsvArticleDeserializer();

        String[] newFields = {"ID", "Link", "Titolo", "Testo", "Data", "Fonte", "Set di fonti"};
        deserializer.setFields(newFields);
        assertArrayEquals(newFields, deserializer.getFields());


    }

    private static Stream<Arguments> testParameters() {
        return Stream.of(
                Arguments.of(createTestArticles1(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles1.csv"),
                Arguments.of(createTestArticles2(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles2.csv"),
                Arguments.of(createTestArticles3(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles3.csv"),
                Arguments.of(createTestArticles4(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles4.csv"),
                Arguments.of(createTestArticles5(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles5.csv")
        );
    }



    @ParameterizedTest
    @MethodSource("testParameters")
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

    @Test
    public void deserialize_particular_cases() {

        CsvArticleDeserializer deserializer = new CsvArticleDeserializer();

        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( null));
        System.out.println(exception1.getMessage());

        // gli viene dato un file che non esistente
        File nonExistentFile = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/nonExistentFile.csv");
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( nonExistentFile));
        System.out.println(exception2.getMessage());


        assertDoesNotThrow(() -> {

            // file vuoto
            File emptyFile = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/emptyArticles.csv");
            List<Serializable> articles = deserializer.deserialize(emptyFile);
            assertTrue(articles.isEmpty());

            // gli viene dato un file che non ha articoli al suo interno semplicemente non deserializza niente
            File noArticlesFile = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/noArticles.csv");
            articles = deserializer.deserialize(noArticlesFile);
            assertTrue(articles.isEmpty());

        });


    }



    // TEST FILE SEMPLICE
    private static List<Article> createTestArticles1() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        return articles;
    }

    // caso in cui alcuni campi non ci sono
    private static List<Article> createTestArticles2() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", null, "Title 1", "Body 1", "Date 1","SourceSet 1",null));
        articles.add(new Article("ID 1", null, "Title 1", "Body 1", "Date 1","SourceSet 1",null));
        articles.add(new Article("ID 1", null, "Title 1", "Body 1", "Date 1","SourceSet 1",null));
        return articles;
    }

    private static List<Article> createTestArticles3() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        return articles;
    }

    // Se è presente L'header ma non il valore segna a valore vuoto
    private static List<Article> createTestArticles4() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","","Source 1"));
        return articles;
    }

    // caso in cui alcuni campi sono in ordine non consueto, ma comunque consistenti in logica. ci sono dei campi mancanti ( sono accettati comunque con null). Ci sono campi senza header
    private static List<Article> createTestArticles5() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "", "Body 1", "",null,"Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "", "",null,"Source 1"));
        articles.add(new Article("", "URL 1", "Title 1", "Body 1", "Date 1",null,"Source 1"));
        return articles;
    }

}