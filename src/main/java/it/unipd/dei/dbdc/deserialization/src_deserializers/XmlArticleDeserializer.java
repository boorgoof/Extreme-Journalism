package it.unipd.dei.dbdc.deserialization.src_deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;

import java.io.File;
import java.io.IOException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;


/**
 * This class implements the interface: {@link Deserializer}.
 * It is used to deserialize CSV files into a list of {@link Serializable} objects that are instances of {@link Article}.
 *
 */
public class XmlArticleDeserializer implements Deserializer {

    /**
     * Deserializes an XML file into a list of {@link Serializable}.
     *
     * @param xmlFile The XML file to deserialize.
     * @return The list of {@link UnitOfSearch} objects obtained from deserialization.
     * @throws IOException If an I/O error occurs during the deserialization process. //TODO fix
     * @throws IllegalArgumentException If the provided XML file is null or does not exist.
     */
    @Override
    public List<Serializable> deserialize(File xmlFile) throws IOException {

        if(xmlFile == null){
            throw new IllegalArgumentException("The xmlFile file cannot be null");
        }
        if (!xmlFile.exists()) {
            throw new IllegalArgumentException("The XML file does not exist");
        }
        List<Article> articles;
        XmlMapper xmlMapper = new XmlMapper();

        try {
            articles = xmlMapper.readValue(xmlFile, new TypeReference<List<Article>>() {});

        } catch (IOException e){
            System.out.print("XML file with wrong structure. " );
            throw new IOException(e);
        }
        return new ArrayList<>(articles);

    }

}
