package it.unipd.dei.dbdc.tools;

import java.io.IOException;
import java.util.Properties;

/**
 * This class analyzes the general properties files, which are the ones that contain
 * the common format to save the downloaded articles and the number of words to extract.
 *
 * @see Properties
 * @see PropertiesTools
 */
public class GeneralProperties {
    /**
     * The key of the format to save the articles into.
     *
     */
    private final static String common_format_name = "common_format";

    /**
     * The key of the number of words to extract and save in the output file.
     *
     */
    private final static String words_count_name = "words_count";

    /**
     * The name of the default properties file.
     *
     */
    public final static String default_properties = "general.properties";

    /**
     * The format to save the articles into.
     *
     */
    private final String common_format;

    /**
     * The number of words to extract and save in the output file.
     *
     */
    private final int words_count;

    /**
     * Constructor of the class: reads the properties file and saves the common format and number of words to
     * extract.
     *
     * @param out_properties The name of the properties file specified by the user. If null, the default properties file will be used.
     * @throws IOException If the out_properties file is invalid or the default one is invalid or missing.
     */
    public GeneralProperties(String out_properties) throws IOException {
        Properties properties = PropertiesTools.getProperties(default_properties, out_properties);
        common_format = properties.getProperty(common_format_name);
        if (common_format == null)
        {
            throw new IOException("Error in the format of the properties");
        }
        try {
            words_count = Integer.parseInt(properties.getProperty(words_count_name));
        }
        catch (NumberFormatException e)
        {
            throw new IOException("Error in the format of the properties");
        }
    }

    /**
     * Function to get the number of words to extract and save in the output file.
     *
     * @return The number of words to extract and save in the output file.
     */
    public int getWordsCount()
    {
        return words_count;
    }

    /**
     * Function to get the format to save the articles into.
     *
     * @return The format to save the articles into.
     */
    public String getCommonFormat()
    {
        return common_format;
    }
}
