package it.unipd.dei.dbdc.UTILITY_SONO_CLASSI_CHE_NON_SERVONO_PIU.DESERIALIZERS_INTERFACE;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvDeserializer implements Deserializer<Article>{

    private String[] fields;

    public CsvDeserializer(String[] fields) {
        this.fields = fields;
    }

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] fields) {
        this.fields = fields;
    }

    @Override
    public List<Article> deserialize(String filePath) throws IOException {
        List<Article> articles = new ArrayList<>();

        try (Reader reader = new FileReader(filePath)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(fields)
                    .build();

            CSVParser parser = new CSVParser(reader, csvFormat);

            for (CSVRecord record : parser) {
                String id = record.get(fields[0]);
                String url = record.get(fields[1]);
                String title = record.get(fields[2]);
                String body = record.get(fields[3]);
                String date = record.get(fields[4]);
                String source = record.get(fields[5]);

                Article article = new Article(id, url, title, body, date, source);
                articles.add(article);
            }

            parser.close();
        }

        return articles;
    }
}

