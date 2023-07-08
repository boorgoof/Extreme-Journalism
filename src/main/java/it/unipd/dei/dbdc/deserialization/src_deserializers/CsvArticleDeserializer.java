package it.unipd.dei.dbdc.deserialization.src_deserializers;

import it.unipd.dei.dbdc.analysis.Article;
import it.unipd.dei.dbdc.analysis.interfaces.Analyzer;
import it.unipd.dei.dbdc.deserialization.interfaces.DeserializerWithFields;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


/**
 * This class implements the interface: {@link DeserializerWithFields}.
 * It is used to deserialize CSV files into a list of {@link Serializable} objects that are instances of {@link Article}.
 * It uses Apache Commons CSV library for parsing CSV files.
 */
public class CsvArticleDeserializer implements DeserializerWithFields {

    /**
     * Default constructor of the class. It is the only constructor and it doesn't do anything,
     * as the fields' values are defined by default.
     *
     */
    public CsvArticleDeserializer() {}

    /**
     * An array of {@link String} containing the fields that are taken into account when deserializing the csv file
     */
    private String[] fields = {"Identifier", "URL", "Title", "Body", "Date", "Source Set", "Source"};

    /**
     * Provides the number of fields taken into account during deserialization
     *
     * @return An {@link Integer} representing the number of fileds
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
     * Sets the new fields to be considered during deserialization
     *
     * @param newFields The new fields that will be considered during deserialization.
     */
    public void setFields(String[] newFields) {

        int maxLength = Article.class.getDeclaredFields().length;
        if (newFields.length == maxLength){
            fields = newFields;
        } else {
            throw new IllegalArgumentException("You cannot insert an array of different length by the number of fields than those declared in the Article class");
        }
    }

    /**
     * The function deserializes a CSV file into {@link List} of {@link Serializable} objects which are instances of {@link Article}. Deserialization occurs in reference
     * to the header in the first line of the CSV file.
     * Only columns in the CSV file that have an association with fields stored in {@link CsvArticleDeserializer#fields} will be considered.
     * The fields specified in the header need not be in the same order as they are stored in {@link CsvArticleDeserializer#fields}
     * If there is no header in the CSV file, deserialization does not occur. An empty list is returned.
     * The words that are present in the header are considered valid only if they contain numbers, letters and white spaces. All other characters are not accepted.
     * <pre>
     * For example in this CSV text:
     *
     * URL,Identifier,Title,Body,Source Set,Source,Cover
     * URL 1,ID 1,Title 1,Body 1,SourceSet 1,Source 1,cover 1
     * URL 2,ID 2,Title 2,Body 2,SourceSet 2,Source 2,cover 2
     * URL 3,ID 3,Title 3,Body 3,SourceSet 3,Source 3,cover 3
     * </pre>
     *
     * In this case, the deserialization involves three {@link Article} objects.
     * The "Date" field is not present in the header therefore the {@link Article} objects will be initialized with the date {@code = null}.
     * Deserialization also works without repeating the order of {@link CsvArticleDeserializer#fields}
     *
     * @param csvFile The CSV file to deserialize into {@link Serializable} objects that are instances of {@link Article}.
     * @return the list of {@link Serializable} objects that are instances of {@link Article} obtained from deserialization.
     * @throws IOException If an I/O error occurs during the deserialization process.
     * @throws IllegalArgumentException If the file does not exist or is not null.
     */
    @Override
    public List<Serializable> deserialize(File csvFile) throws IOException {

        if(csvFile == null){
            throw new IllegalArgumentException("The CSV file cannot be null");
        }
        if (!csvFile.exists()) {
            throw new IllegalArgumentException("The CSV file does not exist");
        }

        List<Serializable> articles = new ArrayList<>();
        // I read the header of the CSV file. This is the first line of the CSV file (I only take into account the header fields that match with CsvArticleDeserializer#fields)
        String[] header = readHeader(csvFile);

        if(header == null || areAllElementsArrayX(header)){
            return articles; // If there is no header in the CSV file, deserialization does not occur. An empty list is returned
        }

        try (Reader reader = new FileReader(csvFile)) {

            // Define the CSV format with the specified header and skip the header record for the real deserialization
            CSVFormat csvFormat = CSVFormat.DEFAULT.builder()
                    .setHeader(header)
                    .setSkipHeaderRecord(true)
                    .build();

            // Create a CSV parser
            CSVParser parser = new CSVParser(reader, csvFormat);

            for (CSVRecord record : parser) {

                // Parse the CSV record into an Article object
                Article article = parseArticleRecord(record);
                articles.add(article);

            }
            // Close the CSV parser
            parser.close();
        }
        return articles;
    }

    /**
     * Parses a CSV record into an {@link Article} object.
     * Performs the  deserialization of a JSON node into an object of type Article ("POJO")
     *
     * @param record The CSV record to parse.
     * @return An {@link Article} object obtained from the parsed record.
     */
    private Article parseArticleRecord(CSVRecord record){

        // Create an array to store the deserialized strings from the CSV file to associate with the Article object.
        // The length of the array is equal to the number of fields declared by the Article class
        Class<Article> myClass = Article.class;
        String[] fieldsValues = new String[myClass.getDeclaredFields().length];

        for(int i=0; i < fieldsValues.length; i++){
            if(record.isSet(fields[i])){
                // Get the value of the field from the CSV record
                fieldsValues[i] = record.get(fields[i]);
            }
        }

        // Create a new Article object using the field values obtained from deserialization
        return new Article(fieldsValues);
    }

    /**
     * This utility function reads the CSV file header and stores it in an array of {@link String}.
     * The array is then modified according to the following criteria:
     * if the field is present in {@link CsvArticleDeserializer#fields}, it is kept in the array
     * in the position it is in, if the field is not present in {@link CsvArticleDeserializer#fields},
     * it is set to "X" (a random letter that identifies a value not to be considered). The function will then return the array which will be used
     * to set the reference header for the function {@link CsvArticleDeserializer#deserialize(File)}
     *
     * @param csvFile The file from which the header is read
     * @return An array of {@link String} which defines the reference header of the csv file according to the fields contained in @link CsvDeserializer#fields}
     */
    private String[] readHeader(File csvFile) throws IOException {

        // Define the CSV format
        CSVFormat csvFormat = CSVFormat.DEFAULT;

        try (Reader reader = new FileReader(csvFile); CSVParser parser = new CSVParser(reader, csvFormat)) {

            CSVRecord headerRecord;
            try {
                // Get the next record, which represents the header of the CSV ( It is the first line of the CSV file)
                headerRecord = parser.iterator().next();
            } catch (NoSuchElementException e) {
                // Empty csv file or wrong header
                return null;
            }

            // Save the column headers in an array
            String[] header = new String[headerRecord.size()];
            for (int i = 0; i < headerRecord.size(); i++) {
                header[i] = headerRecord.get(i).replaceAll("[^a-zA-Z0-9\\s]", "");// Remove BOM character if present
            }

            // Set to null all values that are not also present in CsvDeserializer#fields
            for (int i = 0; i < header.length; i++) {
                if (!contains(fields, header[i])) {
                    header[i] = "X";
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
     * @return {@code true} If the value is found in the array, {@code false} otherwise.
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
     * Checks if all elements in the array are "X".
     *
     * @param array The array to check.
     * @return {@code true} If all elements in the array are "X", {@code false} otherwise.
     */
    private boolean areAllElementsArrayX(String[] array) {

        for (String element : array) {
            if (!element.equals("X") ) {
                return false;
            }
        }
        return true;
    }

}