package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.Console;
import it.unipd.dei.dbdc.search.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;

import java.io.File;
import java.io.IOException;

import java.util.*;

/**
 *
 */
public class DeserializationHandler {

    private final DeserializersContainer container;

    public DeserializationHandler(String fileProperties) throws IOException {
        container = new DeserializersContainer(fileProperties);
    }

    public List<UnitOfSearch> deserializeFile(String format, String filePath) throws IOException {

        Deserializer deserializer = container.getDeserializer(format);
        if (deserializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }
        return deserializer.deserialize(filePath);
    }


    public void deserializeFolder(String format, String folderPath, List<UnitOfSearch> objects) throws IOException {

        // Come gestire le eccezzioni. Facciamo qui? non saprei.
        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(format)) {
                    objects.addAll(deserializeFile(format, file.getAbsolutePath()));
                } else if (file.isDirectory()) {
                    deserializeFolder(format, file.getAbsolutePath(), objects);
                }
            }
        }
    }


    public List<UnitOfSearch> deserializeALLFormatsFolder(String folderPath) {

        // in futuro penso di fare una funzione che trovi i formati inseriti nel database e dice che per i seguenti file non è possibile fare la deserializzazione.

        Console.printlnInteractiveInfo("Sono stati forniti i deserializzatori per i seguenti formati:");
        Set<String> formatsAvailable = container.getFormats();

        for (String format : formatsAvailable) {
            Console.printlnInteractiveInfo(format);
        }
        Console.printlnInteractiveInfo("Nel caso in cui ci fossero file di formato differente da questi elencati non verranno presi in considerazione");


        // Cerco di deserializzare l'intero folder, con tutti i formati possibili
        long start = System.currentTimeMillis();

        List<UnitOfSearch> objects = new ArrayList<>();
        try {
            for (String format : formatsAvailable) {
                deserializeFolder(format, folderPath, objects);
            }
        } catch (IOException e) {
            Console.printlnError("Deserializzazione fallita per il formato: " + e.getMessage());

        }

        long end = System.currentTimeMillis();
        System.out.println(Console.YELLOW+"Tempo deserializzazione: "+(end-start)+ Console.RESET);
        return objects;
    }

}


