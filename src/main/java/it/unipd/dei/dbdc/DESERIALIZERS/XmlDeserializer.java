package it.unipd.dei.dbdc.DESERIALIZERS;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class XmlDeserializer implements Deserializer{
    // é molto brutta cosi gli dovrei passare un array vuoto perchè non la usa.
    // bisognerebbe farla complessa come json e csv che cerca solo i campi specificati

    @Override
    public List<Object> deserialize(String[] fields, String filePath) throws IOException {
        try {
            XmlMapper xmlMapper = new XmlMapper();

            File xmlFile = new File(filePath);

            return new ArrayList<>(xmlMapper.readValue(xmlFile, new TypeReference<List<Article>>() {})); // devo migliorare la leggibilità

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList(); // In caso di errore, restituisce una lista vuota
    }
}
