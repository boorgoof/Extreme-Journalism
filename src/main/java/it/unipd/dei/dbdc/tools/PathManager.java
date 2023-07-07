package it.unipd.dei.dbdc.tools;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * This class is the manager of all the files produced and used by the program
 * (except all the properties files).
 *
 */
public class PathManager {

    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created.
     *
     */
    private PathManager() {}

    /**
     * The folder where the downloaded files will be put and where the user
     * should put the files he wants to serialize and analyse.
     *
     */
    private static final String database_folder = "./database/";
    /**
     * The folder where the output files will be put.
     *
     */
    private static final String output_folder = "./output/";
    /**
     * The name of the file which will contain the most important terms.
     *
     */
    private static final String outFile = "output";

    /**
     * The name of the file which contains the banned words.
     *
     */
    private static final String bannedWordsFile = "english_stoplist.txt";

    /**
     * The function that creates the database folder, if not present, and returns its path.
     *
     * @return The path to the database folder, ending with /
     * @throws IOException If the folder is not present and this function can't create it.
     */
    public static String getDatabaseFolder() throws IOException {
        if (new File(database_folder).listFiles() == null)
        {
            Files.createDirectories(Paths.get(database_folder));
        }
        return database_folder;
    }

    /**
     * The function that creates the output folder, if not present, and returns the name of the output file.
     *
     * @return The name of the output file, without the extension.
     * @throws IOException If the folder is not present and this function can't create it.
     */
    public static String getOutFile() throws IOException {
        if (new File(output_folder).listFiles() == null)
        {
            Files.createDirectories(Paths.get(output_folder));
        }
        return output_folder + outFile;
    }

    /**
     * The function that creates the output folder, if not present, and returns the path of the serialized file.
     *
     * @param common_format The extension of the serialized file.
     * @return The path of the serialized file
     * @throws IOException If the folder is not present and this function can't create it.
     */
    public static String getSerializedFile(String common_format) throws IOException {
        if (new File(output_folder).listFiles() == null)
        {
            Files.createDirectories(Paths.get(output_folder));
        }
        return output_folder+"serialized."+common_format;
    }

    /**
     * The function that returns the name of the banned words file.
     *
     * @return The name of the banned words file
     */
    public static String getBannedWordsFile()
    {
        return bannedWordsFile;
    }

    /**
     * A function that clears the folder specified as a parameter or creates it if it
     * was not present.
     *
     * @param folder_path The path of the folder.
     * @throws IOException If the function can't create the folder or the parameter is null or empty.
     */
    public static void clearFolder(String folder_path) throws IOException {
        if (folder_path == null || folder_path.isEmpty()) {
            throw new IOException("Invalid folder");
        }
        if (!PathManager.deleteFilesInDir(new File(folder_path))) {
            Files.createDirectories(Paths.get(folder_path));
        }
    }

    /**
     * A function that deletes all the files in the directory specified as a parameter.
     *
     * @param dir The {@link File} that denotes the directory.
     * @return False only if it is not a directory or the parameter is null.
     */
    public static boolean deleteFilesInDir(File dir)
    {
        if (dir == null)
        {
            return false;
        }
        File[] contents = dir.listFiles();
        if (contents == null) {
            return false;
        }
        for (File f : contents) {
            deleteDirOrFile(f);
        }
        return true;
    }

    /**
     * A function that deletes this directory or this file.
     *
     * @param file The {@link File} that denotes the directory or the file to delete.
     */
    public static void deleteDirOrFile(File file) {
        if (file == null)
        {
            return;
        }
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDirOrFile(f);
            }
        }
        file.delete();
    }

    public static String getFileFormat(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }
}
