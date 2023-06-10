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

    // SI POTREBBE FARE SENZA IL FORMATO. ossia che dal path troviamo tutto noi.
    public List<UnitOfSearch> deserializeFile(String format, String filePath) throws IOException {

        Deserializer deserializer = container.getDeserializer(format);
        if (deserializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }
        return deserializer.deserialize(filePath);
    }

    // NON SAPREI SI POTREBBE UNIRE QUESTA COSA CON LA DESERIALIZZAZIONE DELLA CARTELLA
    // SE UNIAMO SI SCORRE TUTTA LA CATELLA UNA SOLA VOLTA
    // COSI PERO SI POTREBBE CHIEDERE SE CONTINUARE O MENO CON LA DESERIALIZZAZIONE
    public void rejectedFilesInFolder(String folderPath, List<String> rejectedFiles) {

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if(files != null){
            for (File file : files) {
                if (file.isFile()) {
                    String fileName = file.getName();
                    String format = getFileFormat(fileName);
                    if(!container.getFormats().contains(format)){
                        rejectedFiles.add(fileName);
                    }
                } else if (file.isDirectory()) {
                    rejectedFilesInFolder(file.getAbsolutePath(), rejectedFiles);
                }
            }
        }
    }

    private static String getFileFormat(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }

    // Come gestire le eccezzioni?
    public void deserializeFolder(String format, String folderPath, List<UnitOfSearch> objects) throws IOException {

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

    // DA Verificare se funziona perche è migliore (non chiedo il formato).
    public void deserializeFolder(String folderPath, List<UnitOfSearch> objects) throws IOException {

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {
            for (File file : files) {

                String fileName = file.getName();
                String format = getFileFormat(fileName);

                if (file.isFile() && container.getFormats().contains(format)) {
                    objects.addAll(deserializeFile(format, file.getAbsolutePath()));
                } else if (file.isDirectory()) {
                    deserializeFolder(file.getAbsolutePath(), objects);
                }
            }
        }
    }
    private static void rejectFilesInfo(List<String> rejectFiles, Set<String> formatsAvailable){

        Console.printlnInteractiveInfo("Il programma, al momento, è in grado di trattare con i seguenti formati: ");

        for (String format : formatsAvailable) {
            Console.printlnInteractiveInfo("- " + format);
        }
        System.out.println(Console.YELLOW + "Dunque i seguenti formati inseriti non verranno deserializzati: " + Console.RESET);
        for (String file : rejectFiles){
            System.out.println(Console.RED + file + Console.RESET);
        }

    }

    public List<UnitOfSearch> deserializeALLFormatsFolder(String folderPath) {

        Set<String> formatsAvailable = container.getFormats();

        List<String> rejectFiles = new ArrayList<>();
        rejectedFilesInFolder(folderPath, rejectFiles);

        if (!rejectFiles.isEmpty()){
            rejectFilesInfo(rejectFiles, formatsAvailable);
        }

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


