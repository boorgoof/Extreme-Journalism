package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;

import java.io.File;
import java.io.IOException;

import java.util.*;

import it.unipd.dei.dbdc.deserialization.interfaces.DeserializerWithFields;
import it.unipd.dei.dbdc.tools.PathManager;


public class DeserializationHandler {

    private final DeserializersContainer container; // Dovrei metterlo public se voglio modificare i fields

    public DeserializationHandler(String fileProperties) throws IOException {
        container = new DeserializersContainer(fileProperties);
    }

    public void getFolderFiles(String folderPath, Set<File> allFiles) {

        if(folderPath == null){
            throw new IllegalArgumentException("The path cannot be null");
        }
        if(allFiles == null){
            throw new IllegalArgumentException("The file list cannot be null");
        }

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if(files != null){
            for (File file : files) {
                if (file.isFile()) {
                    allFiles.add(file);
                } else if (file.isDirectory()) {
                    getFolderFiles(file.getPath(), allFiles);
                }
            }
        }
    }

    public Set<File> deleteUnavailableFiles(Set<File> allFiles) {

        if(allFiles == null){
            throw new IllegalArgumentException("The file list cannot be null");
        }

        Set<File> rejectedFiles = new HashSet<>();
        for(File file : allFiles){
            String format = PathManager.getFileFormat(file.getName());
            if(!container.getFormats().contains(format)){
                rejectedFiles.add(file);
            }
        }
        allFiles.removeAll(rejectedFiles); // io mettevo  allFiles.remove(file); dentro all'if ma cosi si rompe. per me è piu efficiente dentro all'if, solo che non saprei fixare.

        return rejectedFiles;
    }
    public Set<File> getDeserializationFiles(String folderPath) {

        if(folderPath == null){
            throw new IllegalArgumentException("The path cannot be null");
        }

        Set<File> files = new HashSet<>();
        Set<File> rejectedFiles;
        getFolderFiles(folderPath, files);
        rejectedFiles = deleteUnavailableFiles(files);

        if(!rejectedFiles.isEmpty()){
            rejectedFilesInfo(rejectedFiles);
        }

        return files;
    }

    private void rejectedFilesInfo(Set<File> rejectedFiles){

        if(rejectedFiles == null){
            throw new IllegalArgumentException("The list cannot be null");
        }

        System.out.println("Il programma, al momento, è in grado di deserializzare solo i seguenti formati: ");
        Set<String> formatsAvailable = container.getFormats();
        for (String format : formatsAvailable) {
            System.out.println("- " + format);
        }

        System.out.println("I file non disonibili per la deserializzazione sono: ");
        for(File file : rejectedFiles){
            System.out.print("NAME: ");
            System.out.print(file.getName());
            System.out.print("  PATH: ");
            System.out.println(file.getAbsolutePath());
        }

    }

    public List<UnitOfSearch> deserializeFile(File file) throws IOException {

        if(file == null){
            throw new IllegalArgumentException("The file cannot be null");
        }

        String format = PathManager.getFileFormat(file.getName());
        Deserializer deserializer = container.getDeserializer(format);

        if (deserializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }

        try{
            return deserializer.deserialize(file);
        }catch (IOException e){
            throw new IOException("Error occurred during " + file.getName()  + " deserialization");
        }

    }

    public List<UnitOfSearch> deserializeFolder(String folderPath) throws IOException {

        if(folderPath == null){
            throw new IllegalArgumentException("The folder path cannot be null");
        }

        Set<File> files = getDeserializationFiles(folderPath);

        // Cerco di deserializzare l'intero folder, con tutti i formati possibili

        List<UnitOfSearch> objects = new ArrayList<>();
        for(File file : files){
            objects.addAll(deserializeFile(file));
        }

        return objects;
    }

    // TODO se mettono roba sbagliata lancia eccezione non posso farci molto; da cambiare?
    public void deserializerSetFields(String format) throws IOException {

        if(format == null){
            throw new IllegalArgumentException("the format cannot be null");
        }

        DeserializerWithFields deserializer;

        if(container.getDeserializer(format) instanceof DeserializerWithFields){
            deserializer = (DeserializerWithFields) container.getDeserializer(format); // mi serve per sapere il numero di fields
        } else {
            throw new IOException("The selected deserializer does not implement field specification");
        }

        int numberOfFields = deserializer.numberOfFields();
        String[] newFields = new String[numberOfFields];
        Scanner scanner = new Scanner(System.in);

        for(int i = 0; i < numberOfFields; i++){
            System.out.println("Enter field" + (i+1) + ": ");
            String field = scanner.nextLine();
            newFields[i] = field;
        }

        System.out.println("The entered fields are:");
        for (String field : newFields) {
            System.out.print(field + "  ");
        }

        scanner.close();

    }

}


