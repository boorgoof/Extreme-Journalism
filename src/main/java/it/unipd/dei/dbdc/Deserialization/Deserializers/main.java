package it.unipd.dei.dbdc.Deserialization.Deserializers;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

public class main {
    public static void main( String[] args ) {

        // Salva gli header delle colonne in un array
        String[] header = {"Identifier", "URL", "Title", "Fulltext", "Date", "Source Set", "Source"};
        String[] h = {"Identifier", "URL", "Title", "Fulltext", "Date", "Source Set", "Source"};
        for (String a : header) {
            System.out.println(a);
        }

        // Imposta a null i valori non presenti nell'array di riferimento //
        for (int i = 0; i < header.length; i++) {
            if (!contains(h, header[i])) {
                header[i] = "X"; // posso setterlo a qualsisi cosa
            }

        }
        for (String a : header) {
            System.out.println(a);
        }

    }
    private static boolean contains (String[]array, String value){
        for (String element : array) {
            if (element != null && element.equals(value)) {
                return true;
            }
        }
        return false;
    }
}
