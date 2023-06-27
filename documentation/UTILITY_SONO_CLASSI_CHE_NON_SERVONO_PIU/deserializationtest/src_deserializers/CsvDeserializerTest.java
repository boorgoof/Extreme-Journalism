package it.unipd.dei.dbdc.deserialization.src_deserializers;

import it.unipd.dei.dbdc.search.Article;
import it.unipd.dei.dbdc.search.interfaces.UnitOfSearch;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.ParameterizedTest;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@Disabled

public class CsvDeserializerTest {

    @Test
    public void getFields() {

        CsvDeserializer deserializer = new CsvDeserializer();

        String[] expectedFields = {"Identifier","URL","Title","Body","Date","Source Set","Source"};
        String[] fields = deserializer.getFields();

        assertArrayEquals(expectedFields, fields);
    }

    @Test
    public void setFields() {

        CsvDeserializer deserializer = new CsvDeserializer();

        String[] newFields = {"ID", "Link", "Titolo", "Testo", "Data", "Fonte", "Set di fonti"};
        deserializer.setFields(newFields);

        String[] fields = deserializer.getFields();
        assertArrayEquals(newFields, fields);
    }
/*
    @ParameterizedTest
    @MethodSource("testParameters")
    public void deserialize_withParameters(List<Article> expectedArticles, String filePath) {

        File file = new File(filePath);
        CsvDeserializer deserializer = new CsvDeserializer();

        try {
            List<UnitOfSearch> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(expectedArticles.size(), articles.size());
            assertEquals(expectedArticles, articles);
        } catch (IOException e) {
            fail("Errore durante la lettura del file CSV: " + e.getMessage());
        }
    }

    private static Stream<Object[]> testParameters() {
        List<Object[]> parameters = new ArrayList<>();
        parameters.add(new Object[]{createTestArticles1(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles1.csv"});
        parameters.add(new Object[]{createTestArticles2(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles2.csv"});
        parameters.add(new Object[]{createTestArticles3(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles3.csv"});
        parameters.add(new Object[]{createTestArticles4(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles4.csv"});
        parameters.add(new Object[]{createTestArticles5(), "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles5.csv"});
        // Aggiungi altri set di parametri per eseguire il test con input diversi
        return parameters.stream();
    }
    */

    @ParameterizedTest
    @MethodSource("testParameters")
    public void deserialize_withParameters(List<Article> expectedArticles, String filePath) {
        File file = new File(filePath);
        CsvDeserializer deserializer = new CsvDeserializer();

        try {
            List<UnitOfSearch> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(expectedArticles.size(), articles.size());
            assertEquals(expectedArticles, articles);
        } catch (IOException e) {
            fail("Errore durante la lettura del file CSV: " + e.getMessage());
        }
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


    /*
    @Test
    public void deserialize_case1() {

        String csvFileTest = "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles1.csv";
        File file = new File (csvFileTest);
        CsvDeserializer deserializer = new CsvDeserializer();

        try {

            List<UnitOfSearch> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles1(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file CSV: " + e.getMessage());
        }

    }

    @Test
    public void deserialize_case2() {

        String csvFileTest = "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles2.csv";
        File file = new File (csvFileTest);
        CsvDeserializer deserializer = new CsvDeserializer();

        try {

            List<UnitOfSearch> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles2(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file CSV: " + e.getMessage());
        }

    }

    @Test
    public void deserialize_case3() {

        String csvFileTest = "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles3.csv";
        File file = new File (csvFileTest);
        CsvDeserializer deserializer = new CsvDeserializer();

        try {

            List<UnitOfSearch> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles3(), articles);


        } catch (IOException e) {
            fail("Errore durante la lettura del file CSV: " + e.getMessage());
        }

    }

    @Test
    public void deserialize_case4() {

        String csvFileTest = "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles4.csv";
        File file = new File (csvFileTest);
        CsvDeserializer deserializer = new CsvDeserializer();

        try {

            List<UnitOfSearch> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles4(), articles);


        } catch (IOException e) {
            fail("Errore durante la lettura del file CSV: " + e.getMessage());
        }

    }


    @Test
    public void deserialize_case5() {

        String csvFileTest = "src/test/resources/DeserializationTest/deserializersTest/csvTest/Articles5.csv";
        File file = new File (csvFileTest);
        CsvDeserializer deserializer = new CsvDeserializer();

        try {

            List<UnitOfSearch> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles5(), articles);


        } catch (IOException e) {
            fail("Errore durante la lettura del file CSV: " + e.getMessage());
        }

    }
  */

}