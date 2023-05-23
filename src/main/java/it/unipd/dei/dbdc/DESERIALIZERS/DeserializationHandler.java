package it.unipd.dei.dbdc.DESERIALIZERS;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


public class DeserializationHandler<T> {
    private final Map<String, Deserializer<T>> deserializers;

    public DeserializationHandler() {
        deserializers = new HashMap<>();
    }

    public Set<String> getFormats(){
        return deserializers.keySet();
    }
    public void registerDeserializer(String format, Deserializer<T> deserializer) {
        deserializers.put(format, deserializer);
    }

    public List<T> deserializeFile(String format, String filePath) throws IOException {

        Deserializer<T> deserializer = deserializers.get(format);

        if (deserializer == null) {
            throw new IOException("No deserializer found for the specified format: " + format);
        }

        try {
            return deserializer.deserialize(filePath);

        } catch (Exception e) {
            throw new IOException("Deserialization failed for format: " + format, e);
        }
    }

    public void deserializeFolder(String format, String folderPath, List<T> objects) throws IOException {
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


}
