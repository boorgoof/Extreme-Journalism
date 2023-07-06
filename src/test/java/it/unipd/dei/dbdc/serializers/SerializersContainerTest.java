package it.unipd.dei.dbdc.serializers;


import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.DeserializersContainer;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;
import it.unipd.dei.dbdc.serializers.src_serializers.XmlSerializer;
import org.junit.jupiter.api.Test;
import it.unipd.dei.dbdc.deserialization.DeserializationHandler;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link SerializersContainer}.
 */
public class SerializersContainerTest {

    /**
     * This utility function creates articles for testing {@link SerializersContainer#getSerializer(String)}.
     *
     * @return list of {@link Serializable} objects that are instances of {@link Article} to serialize. Three Article objects with all fields initialized
     */
    private static List<Serializable> articlesToSerialize() {
        List<Serializable> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));

        return articles;
    }

    /**
     * Tests {@link SerializersContainer#getSerializer(String)} with valid and invalid inputs.
     *
     */
    @Test
    public void getSerializer() {

        try {

            // The test verifies that the container returns the Serializer corresponding to the requested format
            String serializers_properties = "src/test/resources/SerializationTest/properties/serializers.properties";
            SerializersContainer container = SerializersContainer.getInstance(serializers_properties);
            assertTrue(container.getSerializer("xml") instanceof XmlSerializer);


            // There is no serializer available for the requested format. The requested serializer is not contained in the container.
            String messageError = "The program is not yet able to serialize a file to the requested format";
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> container.getSerializer("html"));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Tests {@link SerializersContainer#getFormats()}
     *
     */
    @Test
    public void getFormats() {

        // formats that are actually present in the file propertiess
        Set<String> expectedFormats = new HashSet<>();
        expectedFormats.add("xml");

        try {
            String serializers_properties = "src/test/resources/SerializationTest/properties/serializers.properties";
            SerializersContainer container = SerializersContainer.getInstance(serializers_properties);
            assertEquals(expectedFormats, container.getFormats());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}