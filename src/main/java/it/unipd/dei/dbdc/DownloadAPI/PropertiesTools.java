package it.unipd.dei.dbdc.DownloadAPI;

import java.io.IOException;
import java.io.InputStream;

public class PropertiesTools {
    static java.util.Properties getProperties(String properties_file) throws IOException
    {
        // 1. Prendo il nome del file
        InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(properties_file);

        // 2. Creo un oggetto di Properties
        java.util.Properties appProps = new java.util.Properties();
        try {
            appProps.load(propertiesFile);
        }
        catch (IOException e)
        {
            throw new IOException("There is no file properties with this name: "+properties_file);
        }
        return appProps;
    }
}
