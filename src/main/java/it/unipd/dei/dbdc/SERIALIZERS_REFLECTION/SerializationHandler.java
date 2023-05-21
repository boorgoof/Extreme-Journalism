package it.unipd.dei.dbdc.SERIALIZERS_REFLECTION;

import java.io.IOException;
import java.lang.reflect.Method;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SerializationHandler {

    // è una mappa che ha come key il formato di serializzazione e come valore il metodo che Serializza in quel formato
    private Map<String, Method> serializerMethods;

    public SerializationHandler() {
        serializerMethods = new HashMap<>();
    }

    // Registra un metodo di serializzazione per un formato specificato: associamo la serializzazione a un formato xxx il metodo yyy che desideriamo noi.
    public void registerSerializer(String format, Method serializeMethod) {
        serializerMethods.put(format, serializeMethod);
    }

    // Serializza una lista di oggetti nel formato specificato e salva i dati nel file specificato.
    // Cerca il formato che si desidera Serializzare, se non c'è da errore. Se c'è lo invoca
    public void serializeObjects(List<?> objects, String format, String filePath) throws IOException {

        Method serializeMethod = serializerMethods.get(format);
        if (serializeMethod == null) {
            throw new UnsupportedOperationException("No serializer found for the specified format: " + format);
        }

        try {

            serializeMethod.invoke(null, objects, filePath); // null indica che il metodo è statico.
            // !!!!!
            // il più grande difetto è che i metodi devono avere tutti lo stesso numero di parametri.
            // ALla fine è come il metodo delle interfacce non credo che sia migliore

        } catch (Exception e) {
            throw new IOException("Serialization failed for format: " + format, e);
        }
    }

}


