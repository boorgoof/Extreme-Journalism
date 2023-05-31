package it.unipd.dei.dbdc.Deserializers;

import org.junit.Test;

import static org.junit.Assert.*;

public class CsvDeserializerTest {

    @Test
    public void getFields() {

        CsvDeserializer deserializer = new CsvDeserializer();

        String[] expectedFields = {"Identifier", "URL", "Title", "Body", "Date", "Source Set", "Source"};
        String[] fields = deserializer.getFields();

        assertArrayEquals(expectedFields, fields);
    }

    @Test
    public void setFields() {

        CsvDeserializer deserializer = new CsvDeserializer();

        String[] newFields = {"ID", "Link", "Titolo", "Testo", "Data", "Fonte", "Set di fonti"};
        deserializer.setFields(newFields);

        String[] fields = deserializer.getFields();
        assertArrayEquals(newFields, fields);
    }
    @Test
    public void deserialize() {
    }
}