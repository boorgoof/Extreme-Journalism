package it.unipd.dei.dbdc;

import it.unipd.dei.dbdc.tools.PropertiesTools;

import java.io.IOException;
import java.util.Properties;

public class TotalProperties {
    //TODO: prendere da esterno anche queste properties?
    private static String common_format;
    private static int words_count;

    private final static String common_format_name = "common_format";
    private final static String words_count_name = "words_count";
    public final static String default_properties = "total.properties";

    public TotalProperties(String tot_properties) throws IOException {
        Properties properties;
        if (tot_properties != null)
        {
            properties = PropertiesTools.getOutProperties(tot_properties);
        }
        else {
            properties = PropertiesTools.getDefaultProperties(default_properties);
        }
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
