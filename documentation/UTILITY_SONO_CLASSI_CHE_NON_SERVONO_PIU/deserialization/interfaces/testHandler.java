package it.unipd.dei.dbdc.deserialization.interfaces;

import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Deserializer {
    List<UnitOfSearch> deserialize(String filePath) throws IOException;
}

 /*

    private static Set<File> expectedAllFiles() {
        Set<File> files = new HashSet<>();
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.csv"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.json"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.xml"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/Articles1.html"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.txt"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles2.txt"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/Articles1.pdf"));
        return files;
    }
    private static Set<File> expectedRejectedFiles() {
        Set<File> files = new HashSet<>();
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.txt"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles2.txt"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/Articles1.pdf"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Database2/Articles1.html"));
        return files;
    }
    private static Set<File> expectedCorrectFiles() {
        Set<File> files = new HashSet<>();
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.csv"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.json"));
        files.add(new File("src/test/resources/DeserializationTest/handlerTest/Database/Articles1.xml"));
        return files;
    }

    @Test
    void getFolderFiles() {

        try {
            Set<File> files = new HashSet<>();
            String folderPath = "src/test/resources/DeserializationTest/handlerTest/Database";
            DeserializationHandler handler = new DeserializationHandler(deserializers_properties);

            handler.getFolderFiles(folderPath, files);
            assertEquals(expectedAllFiles(), files);

            IllegalArgumentException exception1 = assertThrows(IllegalArgumentException.class, () -> handler.getFolderFiles(null, files));
            System.out.println(exception1.getMessage());

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> handler.getFolderFiles(folderPath, null));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Test
    void deleteUnavailableFiles() {

        try {
            Set<File> files = new HashSet<>();
            DeserializationHandler handler = new DeserializationHandler(deserializers_properties);
            handler.getFolderFiles("src/test/resources/DeserializationTest/handlerTest/Database", files);

            // qui
            Set<File> rejectedFiles = handler.deleteUnavailableFiles(files);
            assertEquals(expectedRejectedFiles(), rejectedFiles);

            // caso in cui gli passo null
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> handler.deleteUnavailableFiles(null));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getDeserializationFiles() {

        try {
            Set<File> files = new HashSet<>();
            DeserializationHandler handler = new DeserializationHandler(deserializers_properties);
            String folderDatabase = "src/test/resources/DeserializationTest/handlerTest/Database";
            Set<File> deserializationFiles = handler.getDeserializationFiles(folderDatabase);
            assertEquals(expectedCorrectFiles(), deserializationFiles);

            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> handler.getDeserializationFiles(null));


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
*/