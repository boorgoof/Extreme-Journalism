package it.unipd.dei.dbdc.Deserialization.Deserializers;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import it.unipd.dei.dbdc.Interfaces.Deserializers.Deserializer;

import java.io.File;
import java.io.IOException;
import java.util.List;

// ricodati che questa funziona solo con xml costruiti da noi ( comunque xml semplici )
public class XmlDeserializer implements Deserializer<Article> {

    @Override
    public List<Article> deserialize(String filePath) throws IOException {

        XmlMapper xmlMapper = new XmlMapper();
        File xmlFile = new File(filePath);
        return xmlMapper.readValue(xmlFile, new TypeReference<List<Article>>() {});

    }
    // IN QUESTA VERSIONE RESTITUISCE UNA LISTA VUOTA SE C'E un errore
    /*
    @Override
    public List<Article> deserialize(String filePath) {

        try {
            XmlMapper xmlMapper = new XmlMapper();

            File xmlFile = new File(filePath);

            return xmlMapper.readValue(xmlFile, new TypeReference<List<Article>>() {});

        } catch (IOException e) {
            System.out.println("Errore nel file. restituisco una lista vuota");
        }

        return Collections.emptyList(); // In caso di errore, restituisce una lista vuota
    }
    */

}
