package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.resources.ResourcesTools;

import java.io.IOException;
import java.util.Properties;

public class TotalProperties {
    private static String common_format;
    private static int words_count;

    private final static String common_format_name = "common_format";
    private final static String words_count_name = "words_count";
    private static String file = "total.properties";

    public TotalProperties() throws IOException {
        Properties properties = ResourcesTools.getProperties(file);
        common_format = properties.getProperty(common_format_name);
        words_count = Integer.parseInt(properties.getProperty(words_count_name));
    }

    public int getWordsCount()
    {
        return words_count;
    }

    public String getCommonFormat()
    {
        return common_format;
    }
}
