package it.unipd.dei.dbdc.deserializers.src_deserializers;
import it.unipd.dei.dbdc.search.Article;
import it.unipd.dei.dbdc.search.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.src_deserializers.JsonDeserializer;
import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonDeserializerTest {

    @Test
    public void getFields() {

        JsonDeserializer deserializer = new JsonDeserializer();

        String[] expectedFields = {"id", "webUrl", "headline", "bodyText", "webPublicationDate", "webUrl", "webUrl"}; // da modificare
        String[] fields = deserializer.getFields();

        assertArrayEquals(expectedFields, fields);
    }

    @Test
    public void setFields_case1() {
        JsonDeserializer deserializer = new JsonDeserializer();

        String[] newFields = {"ID", "Link", "Titolo", "Testo", "Data", "FonteSet", "Fonte"};
        deserializer.setFields(newFields);

        assertArrayEquals(newFields, deserializer.getFields());

    }
    @Test
    public void setFields_case2() {
        JsonDeserializer deserializer = new JsonDeserializer();

        String[] newFields2 = {"ID", "Link", "Titolo", "Testo", "Data", "Fonte"};
        deserializer.setFields(newFields2);

        assertArrayEquals(newFields2, deserializer.getFields());
    }


    private static List<Article> createTestArticles1() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1","Source 1"));
        return articles;
    }

    // TEST FILE SEMPLICE
    @Test
    public void deserialize_case1() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        deserializer.setFields(fileFields);

        try {

            List<UnitOfSearch> articles = deserializer.deserialize("src/test/deserializersTest/jsonTest/Articles1.json"); // mi funziona solo con path preciso
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
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        return articles;
    }

    // TEST FILE JSON CON COSE ANNIDATE
    @Test
    public void deserialize_case2() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        deserializer.setFields(fileFields);

        try {
            List<UnitOfSearch> articles = deserializer.deserialize("src/test/deserializersTest/jsonTest/Articles2.json"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles2(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

    // TEST FILE JSON CON CAMPI NULL.
    private static List<Article> createTestArticles3() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(null, null,null,null,null,null, null));
        articles.add(new Article(null, null,null,null,null,null,null));
        articles.add(new Article(null, null,null,null,null,null,null));
        return articles;
    }
    @Test
    public void deserialize_case3() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        deserializer.setFields(fileFields);

        try {
            List<UnitOfSearch> articles = deserializer.deserialize("src/test/deserializersTest/jsonTest/Articles3.json");
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            List<Article> articles2 = createTestArticles3();
            assertEquals(articles2, articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

    private static List<Article> createTestArticles4() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1",null));
        articles.add(new Article("ID 1", "URL 1", null, "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", null,"sourceSet 1","Source 1"));
        return articles;
    }


    // TEST FILE JSON CON CHIAVI MANCANTI. ATTENZIONE L'ID NON PUO ESSERE MANCANTE altrimenti errore. non viene caricato articolo
    @Test
    public void deserialize_case4() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        deserializer.setFields(fileFields);

        try {
            List<UnitOfSearch> articles = deserializer.deserialize("src/test/deserializersTest/jsonTest/Articles4.json"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles4(), articles);


        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

    private static List<Article> createTestArticles5() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 5", "URL 5", "Title 5", "Body 5", "Date 5","sourceSet 5","Source 5"));
        return articles;
    }


    // TEST FILE JSON CON formattazione sbagliata.
    // Di fatto seguono i campi successivi all' ID.
    // se vengono posti due ID affiuncati il file json mi sta dicendo che ci sono due Articoli con due ID diversi ma con lo stesso contenuto.
    // si tratta di un errore di formattazione fornito dall'utente. l'interpretazione del file però a mio avviso è pertinente.
    @Test
    public void deserialize_case5() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        deserializer.setFields(fileFields);

        try {
            List<UnitOfSearch> articles = deserializer.deserialize("src/test/deserializersTest/jsonTest/Articles5.json"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles5(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

    private static List<Article> createTestArticles6() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1",null,null));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1",null,null));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1",null,null));
        return articles;
    }
    @Test
    public void deserialize_case6() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" };
        deserializer.setFields(fileFields);

        try {
            List<UnitOfSearch> articles = deserializer.deserialize("src/test/deserializersTest/jsonTest/Articles6.json"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            List<Article> articles2 = createTestArticles6();
            assertEquals(createTestArticles6(), articles);


        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }
}

/*
    List<UnitOfSearch> objects = new ArrayList<>(articles);
    XmlSerializer serializer = new XmlSerializer();
    serializer.serialize(objects,"src/test/deserializersTest/xmlTest/Articles5.xml");
 */