package it.unipd.dei.dbdc.analysis;

import it.unipd.dei.dbdc.analysis.interfaces.Analyzer;
import it.unipd.dei.dbdc.analysis.src_strategies.MapArraySplitAnalyzer.MapSplitAnalyzer;
import it.unipd.dei.dbdc.analysis.src_strategies.PriorityQueueSplitAnalyzer;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

public class AnalyzePropertiesTest {
    @Test
    public void readProperties()
    {
        Analyzer analyzer;
        try {
            //Tests with valid properties (default)
            analyzer = AnalyzeProperties.readProperties(AnalyzerHandlerTest.resources_url+"default.properties");
            assertTrue(analyzer instanceof MapSplitAnalyzer);

            //Tests with valid properties
            analyzer = AnalyzeProperties.readProperties(null);
            assertTrue(analyzer instanceof MapSplitAnalyzer);

            analyzer = AnalyzeProperties.readProperties(AnalyzerHandlerTest.resources_url+"priority.properties");
            assertTrue(analyzer instanceof PriorityQueueSplitAnalyzer);
        } catch (IOException e) {
            fail("Failed the reading of correct properties, or download ones");
        }

        //Tests with invalid properties
        assertThrows(IOException.class, () -> AnalyzeProperties.readProperties(AnalyzerHandlerTest.resources_url + "false.properties"));

        assertThrows(IOException.class, () -> AnalyzeProperties.readProperties(AnalyzerHandlerTest.resources_url+"false2.properties"));

    }
}
