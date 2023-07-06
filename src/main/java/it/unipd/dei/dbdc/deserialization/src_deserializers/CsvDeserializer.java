package it.unipd.dei.dbdc.deserialization.src_deserializers;

import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.interfaces.DeserializerWithFields;
import it.unipd.dei.dbdc.serializers.SerializationProperties;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * This class implements the interface: {@link DeserializerWithFields}.
 * It is used to deserialize CSV files into a list of {@link UnitOfSearch} objects.
 * It uses Apache Commons CSV library for parsing CSV files.
 */
public class CsvDeserializer implements DeserializerWithFields {

    /**
     * An array of {@link String} containing the fields that are taken into account when deserializing the csv file
     */
    private String[] fields;

    public CsvDeserializer(){

        fields = new String[]{"Identifier", "URL", "Title", "Body", "Date", "Source Set", "Source"};
    }

    /**
     * Provides the number of fields taken into account during deserialization
     *
     * @return An {@link Integer} representing number of fileds
     */
    public int numberOfFields(){
        return fields.length;
    }

    /**
     * Provides the fields taken into account during deserialization
     *
     * @return An array of {@link String} representing the fields.
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * sets the new fields to be considered during deserialization
     */
    public void setFields(String[] newFields) {
        fields = newFields;
    }

    /**
     * The function deserializes a CSV file in {@link List} of {@link UnitOfSearch} referring to
     * the header present in the first line of the csv file.
     * Only columns that have an association with fields stored in {@link CsvDeserializer#fields} will be considered.
     * The fields specified in the header need not be in the same order as they are stored in {@link CsvDeserializer#fields}
     *
     * @param csvFile The CSV file to deserialize into {@link UnitOfSearch}
     * @return the list of {@link UnitOfSearch} objects obtained from deserialization
     * @throws IOException If an I/O error occurs during the deserialization process.
     * @throws IllegalArgumentException if the file does not exist or is not null
     */
    @Override
    public List<UnitOfSearch> deserialize(File csvFile) throws IOException {

        if(csvFile == null){
            throw new IllegalArgumentException("The CSV file cannot be null");
        }
        if (!csvFile.exists()) {
            throw new IllegalArgumentException("The CSV file does not exist");
        }

        List<UnitOfSearch> articles = new ArrayList<>();
        // Leggo gli header
        String[] header = readHeader(csvFile);

        if(header == null || areAllElementsArrayNull(header)){
            return articles; // se non c'è un header non deserializza nulla
        }

        try (Reader reader = new FileReader(csvFile)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(header)
                    .setSkipHeaderRecord(true)
                    .build();

            CSVParser parser = new CSVParser(reader, csvFormat);

            for (CSVRecord record : parser) {
                Article article = parseArticleRecord(record);
                articles.add(article);
            }
            parser.close();
        }
        return articles;
    }

    /**
     * Parses a CSV record into an {@link Article} object.
     *
     * @param record The CSV record to parse.
     * @return An {@link Article} object obtained from the parsed record.
     */
    private Article parseArticleRecord(CSVRecord record){

        Class<Article> myClass = Article.class;
        String[] fieldsValues = new String[fields.length];

        for(int i=0; i < fieldsValues.length; i++){
            if(record.isSet(fields[i])){
                fieldsValues[i] = record.get(fields[i]);
            }
        }

        return new Article(fieldsValues);
    }

    /**
     * This utility function reads the CSV file header and stores it in an array of {@link String}.
     * The array is then modified according to the following criteria:
     * if the field is present in {@link CsvDeserializer#fields},it is kept in the array
     * in the position it is in, if the field is not present in {@link CsvDeserializer#fields},
     * it is set to null. The function will then return the array which will be used
     * to set the reference header for the function {@link CsvDeserializer#deserialize(File)}
     *
     * @param csvFile The file from which the header is read
     * @return An array of {@link String} which defines the reference header of the csv file according to the fields contained in @link CsvDeserializer#fields}
     */
    private String[] readHeader(File csvFile) throws IOException {

        CSVFormat csvFormat = CSVFormat.DEFAULT;

        try (Reader reader = new FileReader(csvFile); CSVParser parser = new CSVParser(reader, csvFormat)) {

            CSVRecord headerRecord = null;
            try {
                headerRecord = parser.iterator().next();
            } catch (NoSuchElementException e) {
                return null; // empty csv file or wrong header
            }

            // Salva gli header delle colonne in un array
            String[] header = new String[headerRecord.size()];
            for (int i = 0; i < headerRecord.size(); i++) {
                header[i] = headerRecord.get(i).replace("\uFEFF", "");
            }

            // Imposta a null i valori non presenti nell'array di riferimento
            for (int i = 0; i < header.length; i++) {
                if (!contains(fields, header[i])) {
                    header[i] = null;
                }

            }

            return header;
        }
    }

    /**
     * Checks if a given value is present in the array.
     *
     * @param array The array to search in.
     * @param value The value to search for.
     * @return {@code true} if the value is found in the array, {@code false} otherwise.
     */
    private boolean contains(String[] array, String value) {
        for (String element : array) {
            if (element != null && element.equals(value)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if all elements in the array are null.
     *
     * @param array The array to check.
     * @return {@code true} if all elements in the array are null, {@code false} otherwise.
     */
    private boolean areAllElementsArrayNull(Object[] array) {

        for (Object element : array) {
            if (element != null) {
                return false;
            }
        }
        return true;
    }

}





























