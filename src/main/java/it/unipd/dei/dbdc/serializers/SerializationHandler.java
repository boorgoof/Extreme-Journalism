package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.serializers.interfaces.Serializer;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.io.*;
import java.util.List;

/**
 * Classe per la gestione della serializzazione.
 *
 */
public class SerializationHandler {
    /**
     * Costruttore della classe SerializationHandler.
     *
     * @param filePropertiesName Nome del file delle proprietà per i serializer.
     * @throws IOException
     *
     */
    private final SerializersContainer container;
    public SerializationHandler(String fileProperties) throws IOException {
        container = new SerializersContainer(fileProperties);
    }

    public void serializeObjects(List<UnitOfSearch> objects, File file) throws IOException {

        if (file == null) {
            throw new IOException("The XML file cannot be null"); //TODo viene controllato anche in xmlserialize
        }

        String format = getFileFormat(file.getName());
        Serializer serializer = container.getSerializer(format);
        if (serializer == null) {
            throw new IOException("Objects cannot be serialized in the specified file. The file provided is " + file.getName());
        }

        serializer.serialize(objects, file);
    }
    // viene usata due volte dove posso metterla? in entrambi gli handeler
    private static String getFileFormat(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1).toLowerCase();
        }
        return null;
    }

}




