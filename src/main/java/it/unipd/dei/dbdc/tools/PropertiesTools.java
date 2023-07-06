package it.unipd.dei.dbdc.tools;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * This class has the logic to return the {@link Properties} specified in a file, which
 * can be an external file or a default file.
 *
 * @see Properties
 */
public class PropertiesTools {
    /**
     * This function accepts the name of the default properties file and the path of the properties
     * file specified by the user: it tries to read the user's properties file, calling {@link PropertiesTools#getOutProperties(String)}
     * and if an Exception occurs or the parameter passed is null, reads the default properties.
     *
     * @param default_properties The name of the default properties file to read if the user's one was not specified or had some exceptions
     * @param out_properties The path to the properties file specified by the user. If it was not specified, null
     * @throws IOException If the default properties file has any error or is missing
     * @return A {@link Properties} object with the properties inside the out properties, if those are right, or inside the default properties.
     */
    public static Properties getProperties(String default_properties, String out_properties) throws IOException {
        try {
            return getOutProperties(out_properties);
        } catch (IOException e) {
            //Intentionally left blank.
            //We don't check if the out_properties is null because it is done inside the function getOutProperties
        }
        return getDefaultProperties(default_properties);
    }

    /**
     * This function accepts the name of the default properties file: it reads this file,
     * and throws an Exception if the file is missing or has an error.
     *
     * @param properties_file The name of the default properties file to read.
     * @throws IOException If the default properties file has any error or is missing, or the parameter is null or empty
     * @return A {@link Properties} object with the properties inside the default properties, if those are right.
     */
    public static Properties getDefaultProperties(String properties_file) throws IOException
    {
        if (properties_file == null || properties_file.isEmpty())
        {
            throw new IOException();
        }
        try (InputStream propertiesFile = Thread.currentThread().getContextClassLoader().getResourceAsStream(properties_file)) {
            if (propertiesFile == null) {
                throw new IOException();
            }
            Properties properties = new Properties();
            properties.load(propertiesFile);
            return properties;
        }
    }

    /**
     * This function accepts the path of the properties file specified by the user:
     * it tries to read the user's properties file, and throws an Exception if there is an error or the file
     * is missing.
     *
     * @param properties_file The path to the properties file specified by the user.
     * @throws IOException If the specified properties file has any error or is missing, or the parameter is null or empty
     * @return A {@link Properties} object with the properties inside the out properties, if those are right and the file exists.
     */
    public static Properties getOutProperties(String properties_file) throws IOException
    {
        if (properties_file == null || properties_file.isEmpty())
        {
            throw new IOException();
        }
        try (InputStream propertiesFile = Files.newInputStream(Paths.get(properties_file))) {
            Properties properties = new Properties();
            properties.load(propertiesFile);
            return properties;
        }
    }
}