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


    private static List<Article> createTestArticles1() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","Source 1"));
        return articles;
    }

    // TEST FILE SEMPLICE
    @Test
    public void deserialize_case1() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "source"};
        deserializer.setFields(fileFields);

        try {

            List<Article> articles = deserializer.deserialize("src/test/deserializersTest/jsonTest/Articles1.json"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles1(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }
    private static List<Article> createTestArticles2() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","Source 2"));
        return articles;
    }

    // TEST FILE JSON CON COSE ANNIDATE
    @Test
    public void deserialize_case2() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "source"};
        deserializer.setFields(fileFields);

        try {
            List<Article> articles = deserializer.deserialize("src/test/deserializersTest/jsonTest/Articles2.json"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles2(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

    // TEST FILE JSON CON CAMPI NULL. boh non mi va credo sia per l'equals in article.
    private static List<Article> createTestArticles3() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(null, null,null,null,null,null));
        articles.add(new Article(null, null,null,null,null,null));
        articles.add(new Article(null, null,null,null,null,null));
        return articles;
    }
    @Test
    public void deserialize_case3() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "source"};
        deserializer.setFields(fileFields);

        try {
            List<Article> articles = deserializer.deserialize("src/test/deserializersTest/jsonTest/Articles3.json");
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles3(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

    private static List<Article> createTestArticles4() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1",null));
        articles.add(new Article("ID 1", "URL 1", null, "Body 1", "Date 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", null,"Source 1"));
        return articles;
    }


    // TEST FILE JSON CON CHIAVI MANCANTI. ATTENZIONE L'ID NON PUO ESSERE MANCANTE altrimenti errore. non viene caricato articolo
    @Test
    public void deserialize_case4() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "source"};
        deserializer.setFields(fileFields);

        try {
            List<Article> articles = deserializer.deserialize("src/test/deserializersTest/jsonTest/Articles4.json"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles4(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }
}