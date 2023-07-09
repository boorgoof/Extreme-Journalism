package it.unipd.dei.dbdc.serializers.src_serializers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * This class implements the interface: {@link Serializer}
 * It uses the Jackson library's {@link XmlMapper} to serialize {@link Serializable} list into an XML file
 *
 */
public class XmlSerializer implements Serializer {

    /**
     * The only constructor of the class. It does nothing,
     * as this class has no fields.
     */
    public XmlSerializer() {}

    /**
     * Serializes a list of {@link Serializable} objects into XML file with indented formatting.
     *
     * @param objects  The list of {@link Serializable} objects to be serialized.
     * @param xmlFile  The file into which the objects will be serialized
     * @throws IOException  If an I/O error occurs while writing to the XML file.
     * @throws IllegalArgumentException  If the objects or xmlFile parameter is null.
     */
    @Override
    public void serialize(List<Serializable> objects, File xmlFile) throws IOException {

        if(objects == null){
            throw new IllegalArgumentException("The list of UnitOfSearch cannot be null");
        }
        if(xmlFile == null){
            throw new IllegalArgumentException("The xmlFile file cannot be null");
        }

        try {
            // Create an instance of XmlMapper for the serialization
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable indented formatting

            // Transform objects into XML
            String xmlString = xmlMapper.writeValueAsString(objects);

            // writing the XML string to the file
            FileWriter fileWriter = new FileWriter(xmlFile);
            fileWriter.write(xmlString);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
