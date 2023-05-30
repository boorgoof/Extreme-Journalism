package it.unipd.dei.dbdc.Deserializers;

import junit.framework.AssertionFailedError;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonDeserializerTest {

    @Test
    public void getFields() {

        JsonDeserializer deserializer = new JsonDeserializer();

        String[] expectedFields = {"id", "webUrl", "headline", "bodyText", "webPublicationDate", "webUrl" }; // da modificare
        String[] fields = deserializer.getFields();

        assertArrayEquals(expectedFields, fields);
    }

    @Test
    public void setFields() {
        JsonDeserializer deserializer = new JsonDeserializer();

        String[] newFields = {"ID", "Link", "Titolo", "Testo", "Data", "Fonte"};
        deserializer.setFields(newFields);

        String[] fields = deserializer.getFields();
        assertArrayEquals(newFields, fields);
    }


    private static List<Article> createTestArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","Source 1"));
        return articles;
    }

    // TEST FILE SEMPLICE
    @Test
    public void deserializeSimpleJson() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "source"};
        deserializer.setFields(fileFields);

        try {

            List<Article> articles = deserializer.deserialize("D:\\ingengeria software\\eis-final\\src\\test\\deserializersTest\\Articles1.json"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }
    /*
    // TEST FILE JSON CON COSE ANNIDATE
    @Test
    public void deserializeComplexJson() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "source"};
        deserializer.setFields(fileFields);

        try {
            List<Article> articles = deserializer.deserialize("D:\\ingengeria software\\eis-final\\src\\test\\deserializersTest\\Articles1.json"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

   */
    private static List<Article> createTestArticles2() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1",null));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","Source 1"));
        return articles;
    }

    // TEST FILE JSON CON CHIAVI MANCANTI
    @Test
    public void deserializeNullJson() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "source"};
        deserializer.setFields(fileFields);

        try {
            List<Article> articles = deserializer.deserialize("D:\\ingengeria software\\eis-final\\src\\test\\deserializersError\\ArticlesError1.json"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles2(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

    // TEST FILE JSON CON CAMPI NULL

}