package it.unipd.dei.dbdc.deserialization;


import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(1)
public class DeserializersContainerTest {
    // todo Fixare le properties. il path intendo
    private static final String deserializers_properties = "src/test/resources/DeserializationTest/properties/deserializers.properties";

    @Test @Order(1)
    public void getSpecificFields() {
        String[] expectedFields = {"id", "webUrl", "headline", "bodyText", "webPublicationDate", "webUrl", "webUrl"}; // da modificare

        try {
            DeserializersContainer container = DeserializersContainer.getInstance(deserializers_properties);
            String[] josnContainerFields = container.getSpecificFields("json");

            assertArrayEquals(expectedFields, josnContainerFields);

            String[] xmlContainerFields = container.getSpecificFields("xml");
            assertArrayEquals(xmlContainerFields, null);

            String[] htmlContainerFields = container.getSpecificFields("html");
            assertArrayEquals(xmlContainerFields, null);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test @Order(2)
    public void setSpecificFields() {

        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        try {

            DeserializersContainer container = DeserializersContainer.getInstance(deserializers_properties);
            container.setSpecificFields("json", fileFields);
            String[] containerFields = container.getSpecificFields("json");
            assertEquals(containerFields,fileFields);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> container.setSpecificFields("xml", fileFields));
            System.out.println(exception.getMessage());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private static List<Article> expectedArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        return articles;
    }
    @Test
    public void getDeserializer() {

        assertDoesNotThrow( () ->
        {
            DeserializersContainer container = DeserializersContainer.getInstance(deserializers_properties);

            // non serve perche tanto uso singleton
            //String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
            //container.setSpecificFields("json", fileFields);

            Deserializer deserializer = container.getDeserializer("json");

            File jsonFile = new File("src/test/resources/DeserializationTest/containerTest/Articles1.json");
            List<Serializable> deserializedArticles = deserializer.deserialize(jsonFile);

            assertEquals(expectedArticles(), deserializedArticles);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> container.getDeserializer("html"));
            System.out.println(exception.getMessage());

        });
    }


    @Test
    public void getFormats() {

        Set<String> expectedFormats = new HashSet<>();
        expectedFormats.add("xml");
        expectedFormats.add("csv");
        expectedFormats.add("json");
       assertDoesNotThrow( () -> {
            DeserializersContainer container = DeserializersContainer.getInstance(deserializers_properties);
            assertEquals(expectedFormats, container.getFormats());
        });
    }


}