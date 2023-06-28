package it.unipd.dei.dbdc.analysis;

import it.unipd.dei.dbdc.tools.PropertiesTools;
import it.unipd.dei.dbdc.analysis.interfaces.Analyzer;
import it.unipd.dei.dbdc.analysis.interfaces.OutPrinter;

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
     * The name of the default properties file. It is present in the folder tools.
     *
     */
    public final static String default_properties = "analyze.properties";

    /**
     * The key of the parameter of the properties file that specifies the {@link Analyzer} to use
     *
     */
    private final static String analyzer_key = "analyzer";

    /**
     * The key of the parameter of the properties file that specifies the {@link OutPrinter} to use
     *
     */
    private final static String printer_key = "printer";

    /**
     * The function that reads the properties file and returns the specified {@link Analyzer}.
     *
     * @param filePropertiesName The name of the properties file specified by the user. If it's null, the default properties file is analyzed.
     * @return An array of {@link Object} whose first Object is an {@link Analyzer} and the second one an {@link OutPrinter} class is written in the properties file.
     * @throws IOException If the default analysis properties file is absent or incorrect.
     * @see PropertiesTools
     */
    public static Object[] readProperties(String filePropertiesName) throws IOException {
        Properties analyzersProperties = PropertiesTools.getProperties(default_properties, filePropertiesName);

        Object[] ret = new Object[2];

        //The analyzer
        String analyzer_class_name = analyzersProperties.getProperty(analyzer_key);
        Class<?> analyzer_class;
        try {
            analyzer_class = Class.forName(analyzer_class_name);
        } catch (ClassNotFoundException | NullPointerException e) {
            throw new IOException("There is no class with the name "+analyzer_class_name);
        }

        try {
            ret[0] = analyzer_class.newInstance();
            if (!(ret[0] instanceof Analyzer))
                throw new IOException("The class specified does not implement the right interface");
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            throw new IOException("File properties is not right, or the class is not available");
        }

        //The printer
        String printer_class_name = analyzersProperties.getProperty(printer_key);
        Class<?> printer_class;
        try {
            printer_class = Class.forName(printer_class_name);
        } catch (ClassNotFoundException | NullPointerException e) {
            throw new IOException("There is no class with the name "+printer_class_name);
        }

        try {
            ret[1] = printer_class.newInstance();
            if (!(ret[1] instanceof OutPrinter))
                throw new IOException("The class specified does not implement the right interface");
        } catch (InstantiationException | IllegalAccessException | ClassCastException e) {
            throw new IOException("File properties is not right, or the class is not available");
        }

        return ret;
    }
}
