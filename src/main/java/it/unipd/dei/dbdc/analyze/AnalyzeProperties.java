package it.unipd.dei.dbdc.analyze;

import it.unipd.dei.dbdc.resources.PropertiesTools;
import it.unipd.dei.dbdc.analyze.interfaces.Analyzer;

import java.io.IOException;
import java.util.Properties;

/**
 * This class analyzes the properties file that are relative to the analysis part.
 *
 * @see Properties
 * @see PropertiesTools
 */
public class AnalyzeProperties {
    /**
     * The name of the default properties file. It is present in the folder resources.
     *
     */
    public final static String default_properties = "analyze.properties";

    /**
     * The key of the parameter of the properties file that specifies the {@link Analyzer} to use
     *
     */
    private final static String analyzer_key = "analyzer";

    /**
     * Short description. This description should convey the purpose and functionality of the element.
     * It is typically a multi-line comment.
     *
     * @param filePropertiesName The name of the properties file specified by the user. If it's null, the default properties file is analyzed.
     * @return An analyzer whose class is written in the properties file.
     * @throws IOException If the default analyze properties file is absent or incorrect.
     * @see PropertiesTools
     */
    public static Analyzer readProperties(String filePropertiesName) throws IOException {
        Properties analyzersProperties = PropertiesTools.getProperties(default_properties, filePropertiesName);

        String analyzer_class_name = analyzersProperties.getProperty(analyzer_key);
        Class<?> analyzer_class;
        try {
            analyzer_class = Class.forName(analyzer_class_name);
        } catch (ClassNotFoundException e) {
            throw new IOException("There is no class with the name "+analyzer_class_name);
        }

        try {
            return (Analyzer) analyzer_class.newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            throw new IOException("File properties is not right, or the class is not available");
        }
    }
}
