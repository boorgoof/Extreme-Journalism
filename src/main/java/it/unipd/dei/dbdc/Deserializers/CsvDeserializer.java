package it.unipd.dei.dbdc.Deserializers;

import it.unipd.dei.dbdc.Interfaces.Deserializers.specificDeserializer;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static it.unipd.dei.dbdc.Deserializers.Article.instanceArticle;

public class CsvDeserializer implements specificDeserializer<Article> {

    private String[] fields;

    public CsvDeserializer(){

        fields = new String[]{"Identifier", "URL", "Title", "Fulltext", "Date", "Source Set", "Source"};
    }
    public String[] getFields() {
        return fields;
    }

    public void setFields(String[] newFields) {
        fields = newFields;
    }

    @Override
    public List<Article> deserialize(String filePath) throws IOException {
        List<Article> articles = new ArrayList<>();

        try (Reader reader = new FileReader(filePath)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .setHeader(fields)
                    .build();

            CSVParser parser = new CSVParser(reader, csvFormat);

            for (CSVRecord record : parser) {
                String id = record.get(fields[0]);
                String url = record.get(fields[1]);
                String title = record.get(fields[2]);
                String body = record.get(fields[3]);
                String date = record.get(fields[4]);
                String sourceSet = record.get(fields[5]);
                String source = record.get(fields[6]);

                Article article = new Article(id, url, title, body, date, sourceSet, source);
                //System.out.println(article);
                articles.add(article);

            }
            parser.close();
        }
        return articles;
    }
    /*
    @Override
    public List<Article> deserialize(String filePath) throws IOException {
        List<Article> articles = new ArrayList<>();

        String[] header = readHeader(filePath);

        try (Reader reader = new FileReader(filePath)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(header)
                    .setSkipHeaderRecord(true)
                    .build();

            CSVParser parser = new CSVParser(reader, csvFormat);

            for (CSVRecord record : parser) {
                Class<Article> myClass = Article.class;
                String[] fieldsValues = new String[myClass.getDeclaredFields().length];

                for(int i=0; i < fields.length; i++){
                    if(contains(header, fields[i])){
                        fieldsValues[i] = record.get(fields[i]);
                    }
                }

                Article article = instanceArticle(fieldsValues);

                //System.out.println(article);
                articles.add(article);

            }
            parser.close();
        }
        return articles;
    }

    private String[] readHeader(String filePath) throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT;
        try (Reader reader = new FileReader(filePath); CSVParser parser = new CSVParser(reader, csvFormat)) {

            // Leggi la prima riga del file
            CSVRecord headerRecord = parser.iterator().next();

            // Salva gli header delle colonne in un array
            String[] header = new String[headerRecord.size()];
            for (int i = 0; i < headerRecord.size(); i++) {
                header[i] = headerRecord.get(i).trim();
            }

            // IL PRIMO ELEMENTO DELL' HEDER NON SO PERCHE MA NON é UGUALE. NON RIESCO A CAPIRE IL PERCHE

            // Imposta a null i valori non presenti nell'array di riferimento //
            for (int i = 0; i < header.length; i++) {
                if (!contains(fields, header[i])) {
                    header[i] = "X"; // posso setterlo a qualsisi cosa
                }

            }
            header[0] = "Identifier"; // SONO COSTRETTO A METTERLO MA NON HA SENSOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO MODDDDODODODODODODODODODODOOonnnnnnma

            for(String a : header){
                System.out.println(a);
            }

            return header;
        }
    }

    private boolean contains(String[] array, String value) {
        for (String element : array) {
            if (element != null && element.equals(value)) {
                return true;
            }
        }
        return false;
    }
    */

}






























/*
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
        String sourceSet = record.get(fields[5]);
        String source = record.get(fields[6]);

        Article article = new Article(id, url, title, body, date, sourceSet, source);
        //System.out.println(article);
        articles.add(article);

        }
        parser.close();
        }
        return articles;
}*/

/*
 public String[] deserializeHeader(String filePath) throws IOException {

        try (Reader reader = new FileReader(filePath)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT;

            CSVParser parser = new CSVParser(reader, csvFormat);

            // Leggi la prima riga del file
            CSVRecord headerRecord = parser.iterator().next();

            // Salva gli header delle colonne in un array
            String[] columnHeaders = new String[headerRecord.size()];

            for (int i = 0; i < headerRecord.size(); i++) {
                columnHeaders[i] = headerRecord.get(i);
            }

            parser.close();
            return columnHeaders;
        }
    }
 */

/*
    private String[] readHeader(String filePath) throws IOException {
        CSVFormat csvFormat = CSVFormat.DEFAULT;
        try (Reader reader = new FileReader(filePath); CSVParser parser = new CSVParser(reader, csvFormat)) {

            // Leggi la prima riga del file
            CSVRecord headerRecord = parser.iterator().next();

            // Salva gli header delle colonne in un array
            String[] header = new String[headerRecord.size()];
            for (int i = 0; i < headerRecord.size(); i++) {
               header[i] = headerRecord.get(i);
            }
            /*
            for(int i = 0; i < header.length; i++){
                if(!contains(fields, header[i])){
                    header[i] = null;
                }
            }

            return header;
        }
    }
    public static boolean contains(String[] array, String elemento) {
        List<String> lista = Arrays.asList(array);
        return lista.contains(elemento);
    }
    */