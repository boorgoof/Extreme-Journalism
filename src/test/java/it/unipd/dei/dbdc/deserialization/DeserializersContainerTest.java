package it.unipd.dei.dbdc.deserialization;


import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.JsonArticleDeserializer;
import it.unipd.dei.dbdc.serializers.SerializersContainer;
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Class that tests {@link DeserializersContainer}.
 */

// manca test con properties falsa

@Order(1)
public class DeserializersContainerTest {

    /**
     * The path to an appropriate properties file used by {@link DeserializersContainerTest} tests
     */
    private static final String deserializers_properties = "src/test/resources/DeserializationTest/properties/deserializers.properties";

    /**
     * The function sets the default fields for the JSON and CSV deserializer used by {@link DeserializersContainer}
     */
    @AfterEach
    public void setOriginalFields()  {

        String[] jsonDefaultFields = {"id", "apiUrl", "headline", "bodyText", "webPublicationDate", "publication", "sectionName" };
        String[] csvDefaultFields = {"Identifier", "URL", "Title", "Body", "Date", "Source Set", "Source"};
        DeserializersContainer container = null;
        try {
            container = DeserializersContainer.getInstance(deserializers_properties);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // sets the default fields
        container.setSpecificFields("json", jsonDefaultFields);
        container.setSpecificFields("csv", csvDefaultFields);

    }

    /**
     * Check for cases where an incorrect properties file is passed {@link DeserializersContainer}
     */
    @Test
    public void notCorrectIstance() {

        try {

            // test with a non-existing properties file. it works because it takes the default one
            String nonExistentFile_properties = "src/test/resources/DeserializationTest/properties/nonExistentFile.properties";
            DeserializersContainer container = DeserializersContainer.getInstance(nonExistentFile_properties);

            //Tests with invalid serializers.properties (wrong classes)
            String false_deserializers_properties = "src/test/resources/DeserializationTest/properties/falseDeserializers.properties";
            assertThrows(IOException.class, () -> DeserializationProperties.readDeserializersProperties(false_deserializers_properties));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Tests {@link DeserializersContainer#getSpecificFields(String)}
     *
     */
    @Test
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

    /**
     * Tests {@link DeserializersContainer#setSpecificFields(String, String[])}
     *
     */
    @Test
    public void setSpecificFields() {

        String[] fileFields = {"id" , "url" , "title" , "body" , "date" , "sourceSet", "source"};
        try {

            DeserializersContainer container = DeserializersContainer.getInstance(deserializers_properties);
            container.setSpecificFields("json", fileFields);
            String[] containerFields = container.getSpecificFields("json");
            assertEquals(containerFields,fileFields);

            // Selecting a deserializer that doesn't implement field specification
            assertThrows(IllegalArgumentException.class, () -> container.setSpecificFields("xml", fileFields));

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

            // Selecting a deserializer that is not present in the container
            assertThrows(IllegalArgumentException.class, () -> container.getDeserializer("html"));


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


    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created.
     */
    private DeserializersContainerTest() {}
}