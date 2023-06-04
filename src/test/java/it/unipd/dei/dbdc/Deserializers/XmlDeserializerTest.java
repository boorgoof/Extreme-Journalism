package it.unipd.dei.dbdc.Deserializers;

import it.unipd.dei.dbdc.Serializers.XmlSerializer;
import it.unipd.dei.dbdc.Serializers.XmlSerializerTest;
import org.junit.Test;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class XmlDeserializerTest {

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

        XmlDeserializer deserializer = new XmlDeserializer();

        try {

            List<Article> articles = deserializer.deserialize("src/test/deserializersTest/xmlTest/Articles1.xml"); // mi funziona solo con path preciso
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


    /*
         ERRORE ASPETTATO. SE VOLETE POSSIAMO FARE CHE SI ASPETTA UNA LISTA VUOTA MA PER ME é MEGLIO ERRORE
         il file XML ha una struttura complessa che richiede una deserializzazione personalizzata,
         Per deserializzare questo tipo di file si puo' estendere la classe XmlDeserializer  e personalizzare il processo di deserializzazione per gestire ù
         una struttura specifica del file XML o comunque strutture più complesse.
     */
    @Test
    public void deserialize_case2() {
        XmlDeserializer deserializer = new XmlDeserializer();

        try {
            List<Article> articles = deserializer.deserialize("src/test/deserializersTest/xmlTest/Articles2.xml");
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles2(), articles);
        } catch (IOException e) {

            // Se si verifica un'eccezione, verifica se è l'eccezione desiderata
            assertTrue(e instanceof IOException);
            return;
        }

        // Se non viene sollevata alcuna eccezione, il test fallisce
        fail("Errore: nessuna eccezione è stata sollevata durante la lettura del file XML");
    }
    private static List<Article> createTestArticles3() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(null, null,null,null,null,null, null));
        articles.add(new Article(null, null,null,null,null,null,null));
        articles.add(new Article(null, null,null,null,null,null,null));
        return articles;
    }

    @Test
    public void deserialize_case3() {

        XmlDeserializer deserializer = new XmlDeserializer();

        try {

            List<Article> articles = deserializer.deserialize("src/test/deserializersTest/xmlTest/Articles3.xml"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles3(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file XML: " + e.getMessage());
        }

    }
    private static List<Article> createTestArticles4() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", null, "Title 1", "Body 1", "Date 1","sourceSet 1",null));
        articles.add(new Article("ID 1", "URL 1", null, "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", null,"sourceSet 1","Source 1"));
        return articles;
    }
    @Test
    public void deserialize_case4() {

        XmlDeserializer deserializer = new XmlDeserializer();

        try {

            List<Article> articles = deserializer.deserialize("src/test/deserializersTest/xmlTest/Articles4.xml"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles4(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file XML: " + e.getMessage());
        }

    }

    private static List<Article> createTestArticles5() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","",""));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","",""));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","",""));
        return articles;
    }
    @Test
    public void deserialize_case5() {

        XmlDeserializer deserializer = new XmlDeserializer();

        try {

            List<Article> articles = deserializer.deserialize("src/test/deserializersTest/xmlTest/Articles5.xml"); // mi funziona solo con path preciso
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(3, articles.size());
            assertEquals(createTestArticles5(), articles);

        } catch (IOException e) {
            fail("Errore durante la lettura del file XML: " + e.getMessage());
        }

    }
}