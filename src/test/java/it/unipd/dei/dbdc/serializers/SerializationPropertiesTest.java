package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.download.DownloadProperties;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;
import it.unipd.dei.dbdc.serializers.src_serializers.XmlSerializer;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Class that tests {@link DownloadProperties}.
 */
public class SerializationPropertiesTest {
    private static final String serializers_properties = "src/test/resources/SerializationTest/properties/serializers.properties";
    private static final String nonExistentFile_properties = "src/test/resources/SerializationTest/properties/nonExistentFile.properties";


    private static final String empty_serializers_properties = "src/test/resources/SerializationTest/properties/emptySerializers.properties";
    private static final String false_serializers_properties = "src/test/resources/SerializationTest/properties/falseSerializers.properties";
    private static final String false_serializers_properties2 = "src/test/resources/SerializationTest/properties/falseSerializers2.properties";
    @Test
    void readSerializersProperties() {

        Serializer xmlSer = new XmlSerializer();

        assertDoesNotThrow(() -> {

            //Tests with default properties
            Map<String, Serializer> defaultSerializers = SerializationProperties.readSerializersProperties(null);
            assertEquals(1, defaultSerializers.size());
            assertTrue( defaultSerializers.get("xml") instanceof XmlSerializer);


            //Tests with a valid serializers.properties
            final Map<String, Serializer> serializers;
            serializers = SerializationProperties.readSerializersProperties(serializers_properties);
            assertEquals(1, serializers.size());
            assertTrue(serializers.get("xml") instanceof XmlSerializer );


        });

        assertDoesNotThrow(() -> {

            // test with a non-existing properties file.
            final Map<String, Serializer> serializers;
            serializers = SerializationProperties.readSerializersProperties(nonExistentFile_properties);
            assertEquals(1, serializers.size());
            assertTrue(serializers.get("xml") instanceof XmlSerializer );

            // empty file properties. (no exceptions thrown)
            final Map<String, Serializer> serializers2;
            serializers2 = SerializationProperties.readSerializersProperties(empty_serializers_properties);
            assertEquals(0, serializers2.size());
        });


        //Tests with invalid serializers.properties (wrong classes)
        IOException exception1 = assertThrows(IOException.class, () -> SerializationProperties.readSerializersProperties(false_serializers_properties));
        System.out.println(exception1.getMessage());

        //Tests with invalid serializers.properties (wrong classes)
        IOException exception2 = assertThrows(IOException.class, () -> SerializationProperties.readSerializersProperties(false_serializers_properties2));
        System.out.println(exception2.getMessage());

    }
}

