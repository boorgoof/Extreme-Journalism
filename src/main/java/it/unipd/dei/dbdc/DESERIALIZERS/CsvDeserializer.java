package it.unipd.dei.dbdc.DESERIALIZERS;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvDeserializer implements Deserializer{
    public final static String[] CSVcustomHeaders = {"Identifier","URL","Title","Body","Date","Source Set","Source"}; // Custom header names
    public static List<Article> deserializeFromCSV(String filename, String[] headers) throws IOException {
        List<Article> articles = new ArrayList<>();

        try (Reader reader = new FileReader(filename)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(headers)
                    .build();

            CSVParser parser = new CSVParser(reader, csvFormat);

            for (CSVRecord record : parser) {
                String id = record.get("Identifier");
                String url = record.get("URL");
                String title = record.get("Title");
                String body = record.get("Body");
                String date = record.get("Date");
                String source = record.get("Source");

                Article article = new Article(id, url, title, body, date, source);
                articles.add(article);
            }

            parser.close();
        }

        return articles;
    }

    @Override
    public List<Object> deserialize(String[] fields, String filePath) throws IOException {
        List<Object> objects = new ArrayList<>();

        try (Reader reader = new FileReader(filePath)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(CSVcustomHeaders)
                    .build();

            CSVParser parser = new CSVParser(reader, csvFormat);

            for (CSVRecord record : parser) {
                String id = record.get("Identifier");
                String url = record.get("URL");
                String title = record.get("Title");
                String body = record.get("Body");
                String date = record.get("Date");
                String source = record.get("Source");

                Article article = new Article(id, url, title, body, date, source);
                objects.add(article);
            }

            parser.close();
        }

        return objects;
    }
}

