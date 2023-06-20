package it.unipd.dei.dbdc.resources;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class PropertiesTools {

    // Gestisce tutte le properties.
    public static Properties getProperties(String default_properties, String out_properties) throws IOException {
        if (out_properties != null)
        {
            try {
                return getOutProperties(out_properties);
            } catch (IOException e) {
                //Intentionally left blank
            }
        }
        return getDefaultProperties(default_properties);
    }

    public static Properties getDefaultProperties(String properties_file) throws IOException
    {
        try (InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(properties_file)) {
            if (propertiesFile == null) {
                throw new IOException();
            }
            Properties properties = new Properties();
            properties.load(propertiesFile);
            return properties;
        }
    }

    public static Properties getOutProperties(String properties_file) throws IOException
    {
        try (InputStream propertiesFile = Files.newInputStream(Paths.get(properties_file))) {
            Properties properties = new Properties();
            properties.load(propertiesFile);
            return properties;
        }
    }
}