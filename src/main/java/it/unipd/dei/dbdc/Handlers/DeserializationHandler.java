package it.unipd.dei.dbdc.Handlers;

// NON FINITO
import it.unipd.dei.dbdc.ConsoleTextColors;
import it.unipd.dei.dbdc.Deserialization.DeserializersContainer;
import it.unipd.dei.dbdc.Deserializers.Serializable;
import it.unipd.dei.dbdc.Interfaces.Deserializers.Deserializer;

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
    public List<Serializable> deserializeFile(String format, String filePath) throws IOException {

        Deserializer deserializer = container.getDeserializer(format);
        if (deserializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }
        return deserializer.deserialize(filePath);
    }


    public void deserializeFolder(String format, String folderPath, List<Serializable> objects) throws IOException {
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

    public List<Serializable> deserializeALLFormatsFolder(String folderPath) {

        ConsoleTextColors.printlnProcess("Sono stati forniti i deserializzatori per i seguenti formati:");
        Set<String> formatsAvailable = container.getFormats();
        for (String format : formatsAvailable) {
            ConsoleTextColors.printlnInfo(format);
        }
        ConsoleTextColors.printlnInfo("Nel caso in cui ci fossero file di formato differente da questi elencati non verranno presi in considerazione");

        // Cerco di deserializzare l'intero folder, con tutti i formati possibili
        long start = System.currentTimeMillis();

        List<Serializable> objects = new ArrayList<>();
        try {
            for (String format : formatsAvailable) {
                container.deserializeFolder(format, folderPath, objects);
            }
        } catch (IOException e) {
            ConsoleTextColors.printlnError("Deserializzazione fallita per il formato: " + e.getMessage());

        }

        long end = System.currentTimeMillis();
        System.out.println(ConsoleTextColors.YELLOW+"Tempo deserializzazione: "+(end-start)+ConsoleTextColors.RESET);
        return objects;
    }



}


