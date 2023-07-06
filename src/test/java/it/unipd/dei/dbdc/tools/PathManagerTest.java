package it.unipd.dei.dbdc.tools;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link PathManager}.
 */
@Order(7)
public class PathManagerTest {
    /**
     * The folder of the resources for all the tests, which also comprehends a final /.
     */
    public final static String resources_folder = "./src/test/resources/";

    /**
     * Tests the output of {@link PathManager#getDatabaseFolder()}.
     */
    @Test
    public void getDatabaseFolder() {
        assertDoesNotThrow( () ->
                assertEquals("./database/", PathManager.getDatabaseFolder()));
    }

    /**
     * Tests the output of {@link PathManager#getOutFile()}.
     */
    @Test
    public void getOutFile() {
        assertDoesNotThrow( () ->
                assertEquals("./output/output", PathManager.getOutFile()));
    }

    /**
     * Tests the output of {@link PathManager#getSerializedFile(String)} passing various common formats.
     */
    @Test
    public void getSerializedFile() {
        assertDoesNotThrow(() -> {
            assertEquals("./output/serialized.txt", PathManager.getSerializedFile("txt"));
            assertEquals("./output/serialized.xml", PathManager.getSerializedFile("xml"));
            assertEquals("./output/serialized.json", PathManager.getSerializedFile("json"));
            assertEquals("./output/serialized.notexist", PathManager.getSerializedFile("notexist"));
        });
    }

    /**
     * Tests the output of {@link PathManager#getBannedWordsFile()}.
     */
    @Test
    public void getBannedWordsFile() {
        assertDoesNotThrow(() -> assertEquals("english_stoplist.txt", PathManager.getBannedWordsFile()));
    }

    /**
     * Tests if the folders are really clear after {@link PathManager#clearFolder(String)}.
     * It uses various folders inside the path subdirectory of the resources' folder.
     * Then it refills the folders.
     */
    @Test
    public void clearFolder() {
        assertThrows(IOException.class, () -> PathManager.clearFolder(null));
        assertThrows(IOException.class, () -> PathManager.clearFolder(""));
        assertDoesNotThrow(() ->
        {
            //If dir.listFiles() is null, it is an error

            //Existent folder, empty
            PathManager.clearFolder(resources_folder+"path/empty");
            File dir = new File(resources_folder+"path/empty");
            assertEquals(0, dir.listFiles().length);

            //Not existent folder
            PathManager.clearFolder(resources_folder+"path/ugo");
            dir = new File(resources_folder+"path/ugo");
            assertEquals(0, dir.listFiles().length);

            //Deletes the folder if everything went fine
            dir.delete();

            //Existent folder, with files
            PathManager.clearFolder(resources_folder + "path/full");
            dir = new File(resources_folder+"path/full");
            assertEquals(0, dir.listFiles().length);

            //Not existent inside a folder that does not exist
            PathManager.clearFolder(resources_folder+"path/not/existent/yhaha");
            dir = new File(resources_folder+"path/not/existent/yhaha");
            assertEquals(0, dir.listFiles().length);

            //Deletes the folder if everything went fine
            dir.delete();
            dir = new File(resources_folder+"path/not/existent");
            dir.delete();
            dir = new File(resources_folder+"path/not");
            dir.delete();
        });
        //Refill the folder
        fullFolder();
    }

    /**
     * Tests if the directories are really empty after {@link PathManager#deleteFilesInDir(File)}.
     * It uses various folders inside the path subdirectory of the resources' folder.
     * Then it refills the folders.
     */
    @Test
    public void deleteFilesInDir()
    {
        //Null or not existent
        assertFalse(PathManager.deleteFilesInDir(null));
        assertFalse(PathManager.deleteFilesInDir(new File("")));
        assertFalse(PathManager.deleteFilesInDir(new File(resources_folder+"notexistent")));
        assertFalse(PathManager.deleteFilesInDir(new File(resources_folder+"path/empty/not")));

        //A folder that is empty
        assertTrue(PathManager.deleteFilesInDir(new File(resources_folder+"path/empty")));

        //A folder that is not empty
        assertTrue(PathManager.deleteFilesInDir(new File(resources_folder+"path/full")));

        //A file
        assertFalse(PathManager.deleteFilesInDir(new File(resources_folder+"path/full/hh.txt")));

        //Refill the folder
        fullFolder();
    }

    /**
     * Tests if the directories or files are really deleted after {@link PathManager#deleteDirOrFile(File)}.
     * It uses various folders inside the path subdirectory of the resources' folder.
     * Then it refills the folders.
     */
    @Test
    public void deleteDirOrFile()
    {
        //Files
        PathManager.deleteDirOrFile(new File(resources_folder+"path/full/anotherdir/hh.txt"));
        PathManager.deleteDirOrFile(new File(resources_folder+"path/full/anotherdir/Articles1.csv"));
        PathManager.deleteDirOrFile(new File(resources_folder+"path/full/anotherdir/Articles2.xml"));

        //Existent folder, empty
        PathManager.deleteDirOrFile(new File(resources_folder+"path/full/anotherdir"));
        //Not existent
        PathManager.deleteDirOrFile(new File(resources_folder+"path/full/notexists"));
        //Null
        PathManager.deleteDirOrFile(null);
        //Not existent
        PathManager.deleteDirOrFile(new File(""));

        //A file
        PathManager.deleteDirOrFile(new File(resources_folder+"path/full/hh.txt"));
        PathManager.deleteDirOrFile(new File(resources_folder+"path/full/hh.json"));

        //A file already deleted
        PathManager.deleteDirOrFile(new File(resources_folder+"path/full/hh.txt"));

        //Refill the folder
        fullFolder();
    }


    /**
     * Copies the files from files directory to the full folder.
     */
    private void fullFolder() {
        try {
            List<File> files = new ArrayList<>();
            files.add(new File(resources_folder + "path/files/hh.json"));
            files.add(new File(resources_folder + "path/files/hh.txt"));
            files.add(new File(resources_folder + "path/files/anotherdir"));
            String destination = resources_folder + "path/full/";
            for (File file : files) {
                Files.copy(file.toPath(),
                        (new File(destination + file.getName())).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            }
            files.clear();
            files.add(new File(resources_folder + "path/files/anotherdir/Articles1.csv"));
            files.add(new File(resources_folder + "path/files/anotherdir/Articles2.xml"));
            files.add(new File(resources_folder + "path/files/anotherdir/hh.txt"));
            destination = resources_folder + "path/full/anotherdir/";
            for (File file : files) {
                Files.copy(file.toPath(),
                        (new File(destination + file.getName())).toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            }
        }
        catch (IOException e)
        {
            fail("Unable to copy the files to the folder full");
        }
    }

    /**
     * Reads the file whose path is specified as a {@link String} and returns its content.
     *
     * @param file The path of the file to read
     * @return A {@link String} that contains the content of the file, ending with a space if not empty.
     */
    public static String readFile(String file)
    {
        try (FileReader read = new FileReader(file); Scanner sc = new Scanner(read)) {
            StringBuilder ret = new StringBuilder();
            while (sc.hasNext())
            {
                ret.append(sc.next()).append(" ");
            }
            return ret.toString();
        }
        catch (IOException e)
        {
            fail("Not able to read file");
            return null;
        }
    }
}
