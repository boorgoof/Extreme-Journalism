package it.unipd.dei.dbdc.tools;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class PathManagerTest {
    public final static String resources_folder = "./src/test/resources/";

    @Test
    public void getDatabaseFolder() {
        assertDoesNotThrow( () ->
                assertEquals("./database/", PathManager.getDatabaseFolder()));
    }

    @Test
    public void getOutFile() {
        assertDoesNotThrow( () ->
                assertEquals("./output/output", PathManager.getOutFile()));
    }

    @Test
    public void getSerializedFile() {
        assertDoesNotThrow(() -> {
            assertEquals("./output/serialized.txt", PathManager.getSerializedFile("txt"));
            assertEquals("./output/serialized.xml", PathManager.getSerializedFile("xml"));
            assertEquals("./output/serialized.json", PathManager.getSerializedFile("json"));
            assertEquals("./output/serialized.notexist", PathManager.getSerializedFile("notexist"));
        });
    }

    @Test
    public void getBannedWordsFile() {
        assertDoesNotThrow(() -> assertEquals("english_stoplist.txt", PathManager.getBannedWordsFile()));
    }

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
            //Refill the folder
            fullFolder();

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
    }

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
        //Refill the folder
        fullFolder();

        //A file
        assertFalse(PathManager.deleteFilesInDir(new File(resources_folder+"path/full/hh.txt")));
    }

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

        fullFolder();
    }


    //Copies the files from files directory to the full folder
    public void fullFolder() {
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

}
