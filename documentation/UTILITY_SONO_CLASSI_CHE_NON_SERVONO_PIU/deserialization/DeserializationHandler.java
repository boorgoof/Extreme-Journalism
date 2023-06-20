package it.unipd.dei.dbdc.deserialization;

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
    // il mio dubbio piu grande è dove mettere la gestione dell'errore della deserializzaione. cosa vogliamo specificare? il file che non è andato bene?
    // Solo la cartella? solo il formato?
    public DeserializationHandler(String fileProperties) throws IOException {
        container = new DeserializersContainer(fileProperties);
    }

    // SI POTREBBE FARE SENZA IL FORMATO. ossia che dal path troviamo tutto noi. ANCHE se non ha molto senso la ricerca dovrebbe farla chi lo chiama.
    // diventerebbe pesante per ninete.


    public List<UnitOfSearch> deserializeFile(String format, String filePath) throws IOException {

        Deserializer deserializer = container.getDeserializer(format);
        if (deserializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }
        return deserializer.deserialize(filePath);
        // todo mettere una eccezione.
        // comunque deserialize puo lanciare una eccezione. IO direi che a questo livello viene buttato in output il path del file (o il nome del file) con cui ho avuto problemi.
        // tutti gli altri livelli sucessivi basta che riportano questa eccezione in caso.
    }



    // NON SAPREI SI POTREBBE UNIRE QUESTA COSA CON LA DESERIALIZZAZIONE DELLA CARTELLA
    // SE UNIAMO SI SCORRE TUTTA LA CATELLA UNA SOLA VOLTA
    // COSI PERO SI POTREBBE CHIEDERE SE CONTINUARE O MENO CON LA DESERIALIZZAZIONE

    // IMPORRATANTE
    /*
    // todo cambaire tutto ( prendere tutti i file -> metterli un lista  -> togliere quelli sbagliati -> deserializzare i file della lista rimanenti.
    OK ci ho pensato e non voglio fare cosi. vorrei prendere tutti i file del folder path. poi eliminare quelli cattivi (metterli in output per avvisare l'utente)
    poi tratto con una lista di file da deserializzare.
     */
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

    // DA Verificare se funziona perche è migliore (non chiedo il formato). in realta non per forza è una cosa migliore ( da vedere )
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

        System.out.println("Il programma, al momento, è in grado di trattare con i seguenti formati: ");

        for (String format : formatsAvailable) {
            System.out.println("- " + format);
        }
        System.out.println("Dunque i seguenti file inseriti non verranno deserializzati: ");
        for (String file : rejectFiles){
            System.out.println(file);
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

        List<UnitOfSearch> objects = new ArrayList<>();
        try {
            for (String format : formatsAvailable) {
                deserializeFolder(format, folderPath, objects);
            }
        } catch (IOException e) {
            System.err.println("Deserializzazione fallita per il formato: " + e.getMessage());
        }

        return objects;
    }

}


