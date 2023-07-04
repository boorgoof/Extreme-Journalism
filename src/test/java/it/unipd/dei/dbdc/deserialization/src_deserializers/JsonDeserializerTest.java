package it.unipd.dei.dbdc.deserialization.src_deserializers;
import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
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

import static org.junit.jupiter.api.Assertions.*;

@Disabled

public class JsonDeserializerTest {

    @Test
    public void getFields() {

        JsonDeserializer deserializer = new JsonDeserializer();

        // todo mettere i campi giusti
        String[] expectedFields = {"id", "webUrl", "headline", "bodyText", "webPublicationDate", "webUrl", "webUrl"}; // da modificare
        String[] fields = deserializer.getFields();

        assertArrayEquals(expectedFields, fields);
    }

    @Test
    public void setFields() {

        String[] newfields = {"ID", "Link", "Titolo", "Testo", "Data", "FonteSet", "Fonte"};
        JsonDeserializer deserializer = new JsonDeserializer();
        deserializer.setFields(newfields);
        assertArrayEquals(newfields, deserializer.getFields());

        String[] newfields2 = {"ID", "Link", "Titolo", "Testo", "Data", "Fonte"};
        deserializer.setFields(newfields2);
        assertArrayEquals(newfields2, deserializer.getFields());

    }

    private static Stream<Arguments> deserializeParameters() {
        return Stream.of(
                Arguments.of(createTestArticles1(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles1.json"),
                Arguments.of(createTestArticles2(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles2.json"),
                Arguments.of(createTestArticles3(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles3.json"),
                Arguments.of(createTestArticles4(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles4.json"),
                Arguments.of(createTestArticles5(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles5.json"),
                Arguments.of(createTestArticles6(), "src/test/resources/DeserializationTest/deserializersTest/jsonTest/Articles6.json")
        );
    }
    @ParameterizedTest
    @MethodSource("deserializeParameters")
    public void deserialize(List<Article> expectedArticles, String filePath) {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        deserializer.setFields(fileFields);
        File file = new File (filePath);

        try {
            List<UnitOfSearch> articles = deserializer.deserialize(file);
            assertNotNull(articles);
            assertFalse(articles.isEmpty());
            assertEquals(expectedArticles.size(), articles.size());
            assertEquals(expectedArticles, articles);


        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }

    @Test
    public void deserialize_other_cases() {

        JsonDeserializer deserializer = new JsonDeserializer();
        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        deserializer.setFields(fileFields);

        IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( null));
        System.out.println(exception1.getMessage());

        // gli viene dato un file che non esistente
        File nonExistentFile = new File("src/test/resources/DeserializationTest/deserializersTest/csvTest/nonExistentFile.json");
        IllegalArgumentException exception2 = assertThrows(IllegalArgumentException.class, () -> deserializer.deserialize( nonExistentFile));
        System.out.println(exception2.getMessage());

        // con un file vuoto non c'è errore semplicemnte non deserializza nulla
        File emptyFile = new File("src/test/resources/DeserializationTest/deserializersTest/jsonTest/emptyArticles.json");
        try {
            List<UnitOfSearch> articles = deserializer.deserialize(emptyFile);
            assertTrue(articles.isEmpty());

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

        // gli viene dato un file che non ha articoli al suo interno, non c'è errore semplicemente non deserializza nulla
        File errorFile = new File("src/test/resources/DeserializationTest/deserializersTest/jsonTest/noArticles.json");
        try {
            List<UnitOfSearch> articles = deserializer.deserialize(errorFile);
            assertTrue(articles.isEmpty());

        } catch (IOException e) {
            fail("Errore durante la lettura del file JSON: " + e.getMessage());
        }

    }


    private static List<Article> createTestArticles1() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        return articles;
    }

    private static List<Article> createTestArticles2() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2", "Source 2"));
        return articles;
    }


    // TEST FILE JSON CON CAMPI NULL.
    private static List<Article> createTestArticles3() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article(null, null,null,null,null,null, null));
        articles.add(new Article(null, null,null,null,null,null,null));
        articles.add(new Article(null, null,null,null,null,null,null));
        return articles;
    }




    private static List<Article> createTestArticles4() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","sourceSet 1",null));
        articles.add(new Article("ID 1", "URL 1", null, "Body 1", "Date 1","sourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", null,"sourceSet 1","Source 1"));
        return articles;
    }



    // TEST FILE JSON CON formattazione sbagliata.
    // Di fatto seguono i campi successivi all' ID.
    // se vengono posti due ID affiuncati il file json mi sta dicendo che ci sono due Articoli con due ID diversi ma con lo stesso contenuto.
    // si tratta di un errore di formattazione fornito dall'utente. l'interpretazione del file però a mio avviso è pertinente.
    private static List<Article> createTestArticles5() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 2", "URL 2", "Title 2", "Body 2", "Date 2","sourceSet 2","Source 2"));
        articles.add(new Article("ID 5", "URL 5", "Title 5", "Body 5", "Date 5","sourceSet 5","Source 5"));
        return articles;
    }




    // TEST FILE JSON CON CHIAVI MANCANTI. ATTENZIONE L'ID NON PUO ESSERE MANCANTE altrimenti  non viene caricato articolo
    private static List<Article> createTestArticles6() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1",null,null));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1",null,null));
        return articles;
    }

}
