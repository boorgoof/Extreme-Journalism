package it.unipd.dei.dbdc.serializers;


import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.DeserializersContainer;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;
import org.junit.jupiter.api.Test;
import it.unipd.dei.dbdc.deserialization.DeserializationHandler;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SerializersContainerTest {
    private static final String serializers_properties = "src/test/resources/SerializationTest/properties/serializers.properties";
    private static List<UnitOfSearch> articlesToSerialize() {
        List<UnitOfSearch> articles = new ArrayList<>();
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1", "SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));
        articles.add(new Article("ID 1", "URL 1", "Title 1", "Body 1", "Date 1","SourceSet 1","Source 1"));

        return articles;
    }
    @Test
    public void getSerializer() {

        try {
            SerializersContainer container = SerializersContainer.getInstance(serializers_properties);
            Serializer serializer1 = container.getSerializer("xml");

            File xmlFile = new File("src/test/resources/SerializationTest/ContainerTest/Articles1.xml");
            serializer1.serialize(articlesToSerialize(), xmlFile);

            IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> container.getSerializer("html"));
            System.out.println(exception1.getMessage());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @Test
    public void getFormats() {

        Set<String> expectedFormats = new HashSet<>();
        expectedFormats.add("xml");

        try {
            SerializersContainer container = SerializersContainer.getInstance(serializers_properties);
            assertEquals(expectedFormats, container.getFormats());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}