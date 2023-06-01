package it.unipd.dei.dbdc;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesTools {
    public static java.util.Properties getProperties(String properties_file) throws IOException
    {
        try (InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(properties_file)) {
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
