package it.unipd.dei.dbdc.SERIALIZERS_INTERFACE;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class XmlSerializer implements Serializer{

    @Override
    public void serialize(List<Serializable> objects, String filePath) throws IOException {

        try {
            // Creo il mapper
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT); // Abilita la formattazione indentata

            // Trasformo gli articoli in xml
            String xmlString = xmlMapper.writeValueAsString(objects);

            System.out.println(xmlString);

            // Li salvo su file
            File xmlOutput = new File(filePath);
            FileWriter fileWriter = new FileWriter(xmlOutput);
            fileWriter.write(xmlString);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
