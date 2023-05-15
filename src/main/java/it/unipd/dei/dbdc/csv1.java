package it.unipd.dei.dbdc;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Deserializzazione csv
// Questa è con apache.commons per me non è male.
// Si puo specificare l'header Tutto tranquillo.
// Unica cosa è che non si usa jackson, ma non credo bisogna fare tutto unico.

// Metto sempre una funzione per serializzare per verificare il tutto

// RICORDA: si puo fare anche in jackson a me non va non so il perchè.
// Link che puo servire https://www.baeldung.com/java-converting-json-to-csv
public class csv1 {

    // Example usage
    public static void main(String[] args) {
        String filename = ".\\database\\csv\\nytimes_articles_v2.csv";

        try {

            List<Article> articles = deserializeCSV(filename, CSVcustomHeaders);
            for (Article article : articles) {
                System.out.println(article);
            }

            serializeToXML(".\\database\\fileSerializzato2.xml", articles);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    public final static String[] CSVcustomHeaders = {"Identifier","URL","Title","Body","Date","Source Set","Source"}; // Custom header names
    public static List<Article> deserializeCSV(String filename, String[] headers) throws IOException {
        List<Article> articles = new ArrayList<>();

        // Leggo il file
        Reader reader = new FileReader(filename);

        // Lo parso e lo metto dentro a questo, che contiene records.
        // TODO: withHeader e' deprecato, dice di usare CSVFormat.Builder.setHeader(Class)
        CSVParser parser = new CSVParser(reader, CSVFormat.DEFAULT.withHeader(headers));

        for (CSVRecord record : parser) {

            Article article = new Article(record.get(headers[0]), record.get(headers[1]),record.get(headers[2]), record.get(headers[3]),record.get(headers[4]),record.get(headers[5]));
            articles.add(article);
        }

        parser.close();
        reader.close();

        return articles;
    }
    public static void serializeToXML(String path, List<Article> articles) {

        try {
            // Creo il mapper
            XmlMapper xmlMapper = new XmlMapper();
            xmlMapper.enable(SerializationFeature.INDENT_OUTPUT); // Abilita la formattazione indentata

            // Trasformo gli articoli in xml
            String xmlString = xmlMapper.writeValueAsString(articles);

            System.out.println(xmlString);

            // Li salvo su file
            File xmlOutput = new File(path);
            FileWriter fileWriter = new FileWriter(xmlOutput);
            fileWriter.write(xmlString);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
