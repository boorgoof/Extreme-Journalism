package it.unipd.dei.dbdc.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class PathManager {

    private static final String database_folder = "./database/";
    private static final String output_folder = "./output/";
    private static final String outFile = "output.txt";
    private static final String bannedWordsFile = "english_stoplist_v1.txt";

    public static String getDatabaseFolder() throws IOException {
        if (new File(database_folder).listFiles() == null)
        {
            Files.createDirectories(Paths.get(database_folder));
        }
        return database_folder;
    }

    public static String getOutFile() throws IOException {
        if (new File(output_folder).listFiles() == null)
        {
            Files.createDirectories(Paths.get(output_folder));
        }
        return output_folder + outFile;
    }

    // TODO: migliora logica di output_folder

    public static String getSerializedFile(String common_format) throws IOException {
        if (new File(output_folder).listFiles() == null)
        {
            Files.createDirectories(Paths.get(output_folder));
        }
        return output_folder+"serialized."+common_format;
    }

    public static String getBannedWordsFile()
    {
        return bannedWordsFile;
    }

    public static void clearFolder(String folder_path) throws IOException {
        if (!PathManager.deleteFilesInDir(new File(folder_path))) {
            // Se non era presente, lo crea
            Files.createDirectories(Paths.get(folder_path));
        }
    }

    public static boolean deleteFilesInDir(File dir)
    {
        File[] contents = dir.listFiles();
        if (contents == null) {
            return false;
        }
        for (File f : contents) {
            deleteDir(f);
        }
        return true;
    }

    public static void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                deleteDir(f);
            }
        }
        file.delete();
    }
}
