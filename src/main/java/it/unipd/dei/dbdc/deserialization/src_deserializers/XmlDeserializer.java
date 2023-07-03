package it.unipd.dei.dbdc.deserialization.src_deserializers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

// ricodati che questa funziona solo con xml costruiti da noi ( comunque xml semplici )
public class XmlDeserializer implements Deserializer {

    @Override
    public List<UnitOfSearch> deserialize(File xmlFile) throws IOException {

        XmlMapper xmlMapper = new XmlMapper();
        List<Article> articles = xmlMapper.readValue(xmlFile, new TypeReference<List<Article>>() {}); // FIXME: funziona, ma non ha senso
        return new ArrayList<>(articles);


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

     /*
        XmlMapper xmlMapper = new XmlMapper();

        // read file and put contents into the string
        String readContent = new String(Files.readAllBytes(Paths.get("file.xml")));

        // deserialize from the XML into a Phone object
        PhoneDetails deserializedData = xmlMapper.readValue(readContent, Articles.class);

         */

}
