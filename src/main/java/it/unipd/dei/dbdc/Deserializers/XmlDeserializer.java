package it.unipd.dei.dbdc.Deserializers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import it.unipd.dei.dbdc.Interfaces.Deserializers.Deserializer;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

// ricodati che questa funziona solo con xml costruiti da noi ( comunque xml semplici )
public class XmlDeserializer implements Deserializer<Article> {

    @Override
    public List<Article> deserialize(String filePath) throws IOException {

        try {
            XmlMapper xmlMapper = new XmlMapper();

            File xmlFile = new File(filePath);

            return xmlMapper.readValue(xmlFile, new TypeReference<List<Article>>() {});

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList(); // In caso di errore, restituisce una lista vuota
    }
}
