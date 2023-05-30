package it.unipd.dei.dbdc.Deserializers;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.IOException;
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

    public void dummyArticles(){

    }

    @Test
    public void deserialize() {

        JsonDeserializer deserializer = new JsonDeserializer();
        try {
            List<Article> articles = deserializer.deserialize("");
            assertNotNull(articles);
            assertFalse(articles.isEmpty());

            // Assicurati che il numero di articoli deserializzati sia corretto
           //assertEquals(expectedNumberOfArticles, articles.size());
            // Effettua ulteriori asserzioni sui dati degli articoli deserializzati se necessario

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }
}