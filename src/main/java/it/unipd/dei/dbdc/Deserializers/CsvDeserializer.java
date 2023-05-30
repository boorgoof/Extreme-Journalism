package it.unipd.dei.dbdc.Deserializers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class CsvDeserializer implements specificDeserializer<Article> {

    private String[] fields = {"Identifier","URL","Title","Body","Date","Source Set","Source"};

    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] newFields) {
        if( newFields.length == fields.length){
            fields = newFields;
        }
        else throw new IllegalArgumentException("Deve essere fornito un array \"header\" di dimensione " + fields.length);
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
                System.out.println(article);
                articles.add(article);

            }
            parser.close();
        }
        return articles;
    }

}

