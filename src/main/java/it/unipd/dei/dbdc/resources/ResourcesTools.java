package it.unipd.dei.dbdc.resources;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourcesTools {

    // Gestisce tutte le risorse (del folder e così via)
    public static java.util.Properties getProperties(String properties_file) throws IOException
    {
        //todo: problema con questo se ci passo properties da fuori (vedi test)
        try (InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(properties_file)) {
            if (propertiesFile == null)
            {
                throw new IOException();
            }
            Properties properties = new Properties();
            properties.load(propertiesFile);
            return properties;
        }
        catch (IOException e)
        {
            throw new IOException("There is no file properties with this name: "+properties_file);
        }
    }
}