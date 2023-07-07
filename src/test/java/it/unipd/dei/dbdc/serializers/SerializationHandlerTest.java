package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.DeserializationHandler;
import it.unipd.dei.dbdc.deserialization.DeserializersContainer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.XmlArticleDeserializer;
import it.unipd.dei.dbdc.serializers.src_serializers.XmlSerializer;
import it.unipd.dei.dbdc.serializers.src_serializers.XmlSerializerTest;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link SerializationHandler}.
 */
@Order(7)
public class SerializationHandlerTest {
    
    /**
     * This utility function creates articles for testing {@link SerializationHandler#serializeObjects(List, File)}
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
     * Tests {@link SerializationHandler#serializeObjects(List, File)}.  with various valid and invalid inputs.
     *
     */
    @Test
    public void serializeObjects() {
        try {

            String serializers_properties = "src/test/resources/SerializationTest/properties/serializers.properties";
            SerializationHandler.setProperties(serializers_properties);

            // Serializing the list of Serializable defined by articlesToSerialize()
            File serializeFile = new File("src/test/resources/SerializationTest/handlerTest/Articles1.xml");
            SerializationHandler.serializeObjects(articlesToSerialize(), serializeFile);

            // To verify that the content of the articles has been serialized correctly.
            // I should get the same objects Articles with deserialising the serialized file
            XmlArticleDeserializer deserializer = new XmlArticleDeserializer();
            List<Serializable> articles = deserializer.deserialize(serializeFile);
            assertEquals(articlesToSerialize().size(), articles.size());
            assertEquals(articlesToSerialize(), articles);


            // --Cases where exceptions are thrown--

            // Tests in which a serialization in an unavailable format is requested. The serializer is not present in the container
            File serializeFile2 = new File("src/test/resources/SerializationTest/handlerTest/Articles2.html");
            assertThrows(IllegalArgumentException.class, () -> SerializationHandler.serializeObjects(articlesToSerialize(), serializeFile2));

            // Test with input null file
            assertThrows(IllegalArgumentException.class, () -> SerializationHandler.serializeObjects(articlesToSerialize(), null));

            // Test with null list
            assertThrows(IllegalArgumentException.class, () -> SerializationHandler.serializeObjects(null, serializeFile2));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}