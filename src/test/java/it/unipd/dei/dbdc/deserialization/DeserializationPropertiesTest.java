package it.unipd.dei.dbdc.deserialization;


import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.CsvArticleDeserializer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.JsonArticleDeserializer;
import it.unipd.dei.dbdc.deserialization.src_deserializers.XmlArticleDeserializer;
import it.unipd.dei.dbdc.serializers.SerializationProperties;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
/**
 * Class that tests {@link DeserializationProperties}.
 */
@Order(7)
public class DeserializationPropertiesTest {
    /**
     * Tests {@link DeserializationProperties#readDeserializersProperties(String)}  with various valid and invalid inputs.
     *
     */
    @Test
    public void readDeserializersProperties() {


        Deserializer xmlSer = new JsonArticleDeserializer();

        assertDoesNotThrow(() -> {

            //Tests with default properties
            Map<String, Deserializer> defaultDeserializers = DeserializationProperties.readDeserializersProperties(null);
            assertEquals(3, defaultDeserializers.size());
            assertTrue( defaultDeserializers.get("csv") instanceof CsvArticleDeserializer);


            //Tests with a valid serializers.properties
            String deserializers_properties = "src/test/resources/DeserializationTest/properties/deserializers.properties";
            final Map<String, Deserializer> deserializers;
            deserializers = DeserializationProperties.readDeserializersProperties(deserializers_properties);
            assertEquals(3, deserializers.size());
            assertTrue(deserializers.get("json") instanceof JsonArticleDeserializer);

        });

        assertDoesNotThrow(() -> {

            // test with a non-existing properties file. it works because it takes the default one
            String nonExistentFile_properties = "src/test/resources/DeserializationTest/properties/nonExistentFile.properties";
            final Map<String, Deserializer> deserializers;
            deserializers = DeserializationProperties.readDeserializersProperties(nonExistentFile_properties);
            assertEquals(3, deserializers.size());
            assertTrue(deserializers.get("xml") instanceof XmlArticleDeserializer);


            // empty file properties.
            String empty_deserializers_properties = "src/test/resources/DeserializationTest/properties/emptyDeserializers.properties";
            final Map<String, Deserializer> deserializers2 = DeserializationProperties.readDeserializersProperties(empty_deserializers_properties);
            assertEquals(0, deserializers2.size());

        });


        //Tests with invalid serializers.properties (wrong classes)
        String false_deserializers_properties = "src/test/resources/DeserializationTest/properties/falseDeserializers.properties";
        IOException exception1 = assertThrows(IOException.class, () -> DeserializationProperties.readDeserializersProperties(false_deserializers_properties));
        System.out.println(exception1.getMessage());

    }
}