package it.unipd.dei.dbdc.serializers.src_serializers;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.serializers.interfaces.Serializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Classe che implementa l'interfaccia "Serializer".
 * La classe serializza in un file ".xml" una lista di oggetti "UnitOfSearch" tramite la libreria Jackson.
 * @see UnitOfSearch
 */
public class XmlSerializer implements Serializer {

    /**
     * Serializza una lista di oggetti UnitOfSearch in un file JSON.
     *
     * @param objects   Lista di oggetti da serializzare.
     *
     * @throws IOException Se si verifica un errore durante la scrittura del file JSON.
     */
    @Override
    public void serialize(List<UnitOfSearch> objects, File xmlFile) throws IOException {

        if (xmlFile == null) {
            throw new IOException("The XML file cannot be null"); // TODO: non è meglio illegalargument?
        }

        try {
            // Create an instance of XmlMapper for the serialization
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT); // Enable indented formatting

            // Transform objects into XML
            String xmlString = xmlMapper.writeValueAsString(objects);

            //System.out.println(xmlString); // da togliere

            // Create a new XML file (if it doesn't already exist) and write the XML content to the file
            FileWriter fileWriter = new FileWriter(xmlFile);
            fileWriter.write(xmlString);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
