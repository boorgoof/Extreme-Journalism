package it.unipd.dei.dbdc.deserialization;


import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.JsonArticleDeserializer;
import it.unipd.dei.dbdc.serializers.SerializersContainer;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Class that tests {@link DeserializersContainer}.
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Order(1)
public class DeserializersContainerTest {
    // todo Fixare le properties. il path intendo
    private static final String deserializers_properties = "src/test/resources/DeserializationTest/properties/deserializers.properties";

    @Test @Order(1)
    public void getSpecificFields() {
        String[] expectedFields = {"id", "apiUrl", "headline", "bodyText", "webPublicationDate", "publication", "sectionName" };

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

    /**
     * This utility function creates articles for testing {@link DeserializersContainer#getDeserializer(String)}
     *
     * @return list of {@link Article}
     */
    private static List<Article> expectedArticles() {
        List<Article> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "sourceSet 1", "Source 1"));
        return articles;
    }

    /**
     * Tests {@link SerializersContainer#getSerializer(String)} with valid and invalid inputs.
     *
     */
    @Test
    public void getDeserializer() {


        // there is no need to set the fields because I use singleton (I already did it)
        assertDoesNotThrow( () ->
        {
            // The test verifies that the container returns the Deserializer corresponding to the requested format
            DeserializersContainer container = DeserializersContainer.getInstance(deserializers_properties);
            Deserializer deserializer = container.getDeserializer("json");
            assertTrue(deserializer instanceof JsonArticleDeserializer);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> container.getDeserializer("html"));
            System.out.println(exception.getMessage());

        });
    }


    /**
     * Tests {@link SerializersContainer#getFormats()}
     *
     */
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