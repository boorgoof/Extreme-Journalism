package it.unipd.dei.dbdc.serializers;

import it.unipd.dei.dbdc.serializers.interfaces.Serializer;
import it.unipd.dei.dbdc.analysis.interfaces.UnitOfSearch;

import java.io.*;
import java.util.List;

import it.unipd.dei.dbdc.tools.PathManager;
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
            throw new IOException("The XML file cannot be null"); //TODo viene controllato anche in xmlserialize, mettere illegal
        }

        String format = PathManager.getFileFormat(file.getName());
        Serializer serializer = container.getSerializer(format);
        if (serializer == null) {
            throw new IOException("Objects cannot be serialized in the specified file. The file provided is " + file.getName());
        }

        serializer.serialize(objects, file);
    }

}




