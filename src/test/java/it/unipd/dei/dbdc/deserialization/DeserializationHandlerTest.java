package it.unipd.dei.dbdc.deserialization;

import it.unipd.dei.dbdc.Console;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class DeserializationHandlerTest {
    private static final String deserializers_properties = "deserializers.properties";
    @Test
    public void deserializeFile() {
    }

    @Test
    public void deserializeFolder() {
    }

    @Test
    public void deserializeALLFormatsFolder() {
    }

    @Test
    public void testDeserializeFile() {
    }

    private static List<String> expectedFiles() {
        List<String> files = new ArrayList<>();
        files.add("Articles1.txt");
        files.add("Articles2.txt");
        files.add("Articles1.pdf");
        files.add("Articles1.html");
        return files;
    }
    @Test
    public void rejectedFilesInFolder() {

        List<String> files = expectedFiles();
        List<String> rejectFiles = new ArrayList<>();

        try {

            DeserializationHandler handler = new DeserializationHandler(deserializers_properties);
            handler.rejectedFilesInFolder("src/test/DeserializationTest/handlerTest", rejectFiles);

            assertNotNull(rejectFiles);
            assertFalse(rejectFiles.isEmpty());

            Collections.sort(files);
            Collections.sort(rejectFiles);

            assertEquals(files, rejectFiles);

        }catch (IOException e)
        {
            Console.printlnError("Errore del programma: non sono stati caricati correttamente i deserializzatori del file "+deserializers_properties);
            e.printStackTrace();
        }

    }

    @Test
    public void testDeserializeFolder() {
    }

    @Test
    public void testDeserializeALLFormatsFolder() {
    }
}