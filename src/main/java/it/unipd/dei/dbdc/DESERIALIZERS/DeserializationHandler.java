package it.unipd.dei.dbdc.DESERIALIZERS;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DeserializationHandler {
    private Map<String, Deserializer> deserializers;

    public DeserializationHandler() {
        deserializers = new HashMap<>();
    }

    public void registerDeserializer(String format, Deserializer deserializer) {
        deserializers.put(format, deserializer);
    }

    public List<Object> deserializeFile(String format, String[] fields, String filePath) throws IOException {

        Deserializer deserializer = deserializers.get(format);

        if (deserializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }

        try {
            return deserializer.deserialize(fields, filePath);

        } catch (Exception e) {
            throw new IOException("Deserialization failed for format: " + format, e);
        }
    }

    // spero funzioni
    public List<Object> deserializeFolder(String format, String[] fields, String folderPath, List<Object> objects) throws IOException {

        File folder = new File(folderPath);
        File[] files = folder.listFiles();

        if (files != null) {

            for (File file : files) {

                if (file.isFile() && file.getName().endsWith(format)) {

                    objects.add(deserializeFile(format, fields, file.getAbsolutePath()));

                } else if (file.isDirectory()) {

                    deserializeFolder(format, fields, file.getAbsolutePath(), objects);
                }
            }
        }

        return objects;
    }
}
