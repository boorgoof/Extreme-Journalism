package it.unipd.dei.dbdc.Handlers;

import it.unipd.dei.dbdc.PropertiesTools;
import it.unipd.dei.dbdc.Search_terms.Analyzer;
import java.util.Properties;

import java.io.*;
import java.util.*;
public class AnalyzerHandler<T> {

    private Map<String, Analyzer<T>> analyzers;
    public AnalyzerHandler(String filePropertiesName) throws IOException{
        Properties analyzersProperties = PropertiesTools.getProperties(filePropertiesName);
        analyzers = setAnalyzersMap(analyzersProperties);
    }

    private Map<String, Analyzer<T>> setAnalyzersMap(Properties analyzersProperties) throws IOException{
        Map<String, Analyzer<T>> analyzerMap = new HashMap<>();

        for (String format : analyzersProperties.stringPropertyNames()) {

            String analyzerClassName = analyzersProperties.getProperty(format);
            if (analyzerClassName == null) {
                throw new IOException("No analyzer found for the format: " + format);
            }

            try {
                Class<?> analyzerClass = Class.forName(analyzerClassName);
                Analyzer<T> analyzer = (Analyzer<T>) analyzerClass.getDeclaredConstructor().newInstance();
                analyzerMap.put(format, analyzer);
            } catch (Exception e) {
                throw new IOException("Failed to instantiate the deserializer for the format: " + format, e);
            }

        }
        return analyzerMap;
    }
}
