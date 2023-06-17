package it.unipd.dei.dbdc.search;

import it.unipd.dei.dbdc.resources.ResourcesTools;
import it.unipd.dei.dbdc.search.interfaces.Analyzer;

import java.io.IOException;
import java.util.Properties;

public class AnalyzeProperties {
    private static String analyze_properties = "analyze.properties";
    private final static String analyzer_key = "analyzer";

    public static Analyzer readProperties(String filePropertiesName) throws IOException {
        if (filePropertiesName != null)
        {
            analyze_properties = filePropertiesName;
        }
        Properties analyzersProperties = ResourcesTools.getProperties(analyze_properties);
        String analyzer_class_name = analyzersProperties.getProperty(analyzer_key);
        Class<?> analyzer_class;
        try {
            analyzer_class = Class.forName(analyzer_class_name);
        } catch (ClassNotFoundException e) {
            throw new IOException("There is no component analyzer in the properties file");
        }
        try {
            return (Analyzer) analyzer_class.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            throw new IOException("File properties is not right, or the class is not available");
        }
    }
}
