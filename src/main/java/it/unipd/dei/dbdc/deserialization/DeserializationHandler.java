package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;
import it.unipd.dei.dbdc.deserialization.interfaces.Deserializer;

import java.io.File;
import java.io.IOException;

import java.io.Serializable;
import java.util.*;

import it.unipd.dei.dbdc.deserialization.interfaces.DeserializerWithFields;
import it.unipd.dei.dbdc.tools.PathManager;

/**
 * This class handles the deserialization of the database into a list of {@link Serializable}
 *
 * @see DeserializersContainer
 */
public class DeserializationHandler {
    /**
     * The {@link DeserializersContainer} instance that supplies all the {@link Deserializer} we have
     *
     */
    private static DeserializersContainer container; // Dovrei metterlo public se voglio modificare i fields

    /**
     * Function that initializes the {@link DeserializersContainer}.
     *
     * @param deserializers_properties The file properties specified by the user. If null, the default ones will be used.
     * @throws IOException If the download properties files (the default one and the one specified by the user) are not present or are not correct.
     */
    public static void instantiate(String deserializers_properties) throws IOException {

        container = DeserializersContainer.getInstance(deserializers_properties);
    }

    /**
     * The function stores all the files present in a folder.
     * All files in subfolders are also taken into account
     * The function is implemented through recursion so the files are stored in a Set of {@link File} supplied as a parameter
     *
     * @param folderPath The path of the folder to scan for files
     * @param allFiles Set of {@link File} in which all files in the specified folder will be stored
     * @throws IllegalArgumentException If either the objects or file parameter is null
     */
    public static void getFolderFiles(String folderPath, Set<File> allFiles) {

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
                    // If it is a file it is added to the set
                    allFiles.add(file);
                } else if (file.isDirectory()) {
                    // If it is a folder, recursion is performed
                    getFolderFiles(file.getPath(), allFiles);
                }
            }
        }
    }

    /**
     * The function removes from the {@link Set} of {@link File} passed as a parameter,
     * all the files for which there is no deserializer contained in the {@link DeserializationHandler#container}.
     * The function returns the files removed from the Set
     *
     * @param allFiles Set of {@link File} from which files that do not have a deserilizer will be removed
     * @return A Set of {@link File} that have been removed from the set of files passed to the function
     * @throws IllegalArgumentException If allFiles parameter is null
     */
    public static Set<File> deleteUnavailableFiles(Set<File> allFiles) {

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
        allFiles.removeAll(rejectedFiles);

        return rejectedFiles;
    }

    /**
     * The function stores only files contained within the folder passed as a parameter
     * and for which there is a deserializer for their specific format
     *
     * @param folderPath The path of the folder to scan for files
     * @return A Set of {@link File} for which a deserializer is available
     * @throws IllegalArgumentException If folderPath parameter is null
     */
    public static Set<File> getDeserializationFiles(String folderPath) {

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
    /**
     * Function that shows the user all the main information of the files
     * that will not be deserialized because they do not have a deserializer contained in {@link DeserializationHandler#container}
     *
     * @param rejectedFiles Set of {@link File} that have been removed from the set of files to deserialize
     * @throws IllegalArgumentException If rejectedFiles parameter is null
     */
    private static void rejectedFilesInfo(Set<File> rejectedFiles){

        if(rejectedFiles == null){
            throw new IllegalArgumentException("The list cannot be null");
        }

        // Print the message to inform the user about the available file formats for deserialization
        System.out.println("Il programma, al momento, è in grado di deserializzare solo i seguenti formati: ");
        Set<String> formatsAvailable = container.getFormats();
        for (String format : formatsAvailable) {
            System.out.println("- " + format);
        }

        // Print the information about the rejected files
        System.out.println("I file non disonibili per la deserializzazione sono: ");
        for(File file : rejectedFiles){
            System.out.print("NAME: ");
            System.out.print(file.getName());
            System.out.print("  PATH: ");
            System.out.println(file.getAbsolutePath());
        }
    }

    /**
     * The function deserializes a file into a list of {@link Serializable}
     * The function correctly selects the {@link Deserializer} to use starting from the extension of the file passed as a parameter
     *
     * @param file File to deserialize
     * @throws IOException  If the file passed as a parameter has no associated {@link Deserializer}
     * @throws IllegalArgumentException  If file parameter is null.
     */
    public static List<Serializable> deserializeFile(File file) throws IOException {

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

    /**
     * The function deserializes all the files contained within the folder passed as a parameter into a list of {@link Serializable},
     * provided that there is a deserializer available for their specific format (contained in the {@link DeserializationHandler#container})
     * The function correctly selects the {@link Deserializer} to use starting from the extension of the file to be deserialized.
     * Files that do not have a deserializer are not treated
     *
     * @param folderPath The path to the folder containing the files to deserialize
     * @throws IOException IOException If an I/O error occurs during deserialization
     * @throws IllegalArgumentException  If the folderPath parameter is null.
     */
    public static List<Serializable> deserializeFolder(String folderPath) throws IOException {

        if(folderPath == null){
            throw new IllegalArgumentException("The folder path cannot be null");
        }

        Set<File> files = getDeserializationFiles(folderPath);

        // Cerco di deserializzare l'intero folder, con tutti i formati possibili
        List<Serializable> objects = new ArrayList<>();
        for(File file : files){
            objects.addAll(deserializeFile(file));
        }

        return objects;
    }

    /**
     * The function interactively sets the new fields that will be considered
     * when deserializing the specified format (Fields are prompted to the user).
     * The function sets the fields only if the requested {@link Deserializer} implements
     * the {@link DeserializerWithFields} interface
     *
     * @throws IllegalArgumentException If folderPath parameter is null or the selected deserializer does not implement field specification
     */
    // TODO se mettono roba sbagliata lancia eccezione non posso farci molto; da cambiare? Devo fare i test
    public static void deserializerSetFields()  {

        Scanner scanner = new Scanner(System.in);
        System.out.println("If no other fields are desired, enter an empty string");
        String format = scanner.nextLine();

        DeserializerWithFields deserializer;
        try {
            if(container.getDeserializer(format) instanceof DeserializerWithFields){
                deserializer = (DeserializerWithFields) container.getDeserializer(format);
            } else {
                throw new IllegalArgumentException("The selected deserializer does not implement field specification");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        int numberOfFields = deserializer.numberOfFields();
        String[] newFields = new String[numberOfFields];

        System.out.println("The number of fields to enter is : " + numberOfFields );
        System.out.println("If no other fields are desired, enter an empty string");

        for(int i = 0; i < numberOfFields; i++){
            System.out.println("Enter field" + (i+1) + ": ");
            String field = scanner.nextLine();
            newFields[i] = field;
        }

        deserializer.setFields(newFields);

        System.out.println("The entered fields are:");
        for (String field : newFields) {
            System.out.print(field + "  ");
        }

        scanner.close();

    }

}


