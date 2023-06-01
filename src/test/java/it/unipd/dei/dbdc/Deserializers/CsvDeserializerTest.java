package it.unipd.dei.dbdc.Deserializers;

import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class CsvDeserializerTest {

    @Test
    public void getFields() {

        CsvDeserializer deserializer = new CsvDeserializer();

        String[] expectedFields = {"Identifier", "URL", "Title", "Body", "Date", "Source Set", "Source"};
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
    private static List<Article> createTestArticles1() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("Identifier", "URL", "Title", "Body", "Date",  "sourceSet ","Source"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1","Source 1"));
        return articles;
    }

    // TEST FILE SEMPLICE
    @Test
    public void deserialize_case1() {

        String csvFileTest = "src/test/deserializersTest/csvTest/Articles1.csv";
        CsvDeserializer deserializer = new CsvDeserializer();

        try {

            List<Article> articles = deserializer.deserialize(csvFileTest); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(4, articles.size());
            assertEquals(createTestArticles1(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file CSV: " + e.getMessage());
        }

    }

}