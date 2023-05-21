package it.unipd.dei.dbdc.METODI_DESERIALIZZAZIONE_SERIALIZZAZIONE_PROVE;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.Collections;
import java.util.List;

// Vedere l'originalità perché comunque sono presi mezzi online

public class xml0 {

    public static void main(String[] args) {

        //xml
        List<Article> articles = deserializeFromXML(".\\database\\fileSerializzato2.xml");

        for(Article article : articles ){

            System.out.println(article.toString());
        }

    }

    // Funziona perché non è una merdina annidata ma sono articoli uno dopo l'altro (altrimenti devo vedere altre soluzioni più complesse).
    // Questa è una soluzione che esiste anche per json (sarebbe file json0)
    public static List<Article> deserializeFromXML(String path) {
        try {
            XmlMapper xmlMapper = new XmlMapper();
            File xmlFile = new File(path);
            return xmlMapper.readValue(xmlFile, new TypeReference<List<Article>>() {});

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Collections.emptyList(); // In caso di errore, restituisce una lista vuota
    }

    public static void serializeToXML(String path, List<Article> articles) {

        try {
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT); // Abilita la formattazione indentata

            String xmlString = xmlMapper.writeValueAsString(articles);

            System.out.println(xmlString);

            File xmlOutput = new File(path);
            FileWriter fileWriter = new FileWriter(xmlOutput);
            fileWriter.write(xmlString);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
