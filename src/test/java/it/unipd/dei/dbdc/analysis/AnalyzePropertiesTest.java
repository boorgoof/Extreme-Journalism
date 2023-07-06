package it.unipd.dei.dbdc.analysis;

import it.unipd.dei.dbdc.analysis.src_printers.TxtOutPrinter;
import it.unipd.dei.dbdc.analysis.src_strategies.MapSplitAnalyzer.MapSplitAnalyzer;
import it.unipd.dei.dbdc.analysis.src_strategies.PriorityQueueSplitAnalyzer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link AnalyzeProperties}.
 */
@Order(7)
public class AnalyzePropertiesTest {

    /**
     * Tests {@link AnalyzeProperties#readProperties(String)} with valid and invalid parameters.
     */
    @Test
    public void readProperties()
    {
        assertDoesNotThrow( () -> {
            //Tests with valid properties
            Object[] props = AnalyzeProperties.readProperties(AnalyzerHandlerTest.resources_url+"default.properties");
            assertEquals(2, props.length);
            assertTrue(props[0] instanceof MapSplitAnalyzer);
            assertTrue(props[1] instanceof TxtOutPrinter);

            props = AnalyzeProperties.readProperties(null);
            assertEquals(2, props.length);
            assertTrue(props[0] instanceof MapSplitAnalyzer);
            assertTrue(props[1] instanceof TxtOutPrinter);


            props = AnalyzeProperties.readProperties(AnalyzerHandlerTest.resources_url+"priority.properties");
            assertEquals(2, props.length);
            assertTrue(props[0] instanceof PriorityQueueSplitAnalyzer);
            assertTrue(props[1] instanceof TxtOutPrinter);
        });

        //Tests with invalid properties
        assertThrows(IOException.class, () -> AnalyzeProperties.readProperties(AnalyzerHandlerTest.resources_url + "false.properties"));
        assertThrows(IOException.class, () -> AnalyzeProperties.readProperties(AnalyzerHandlerTest.resources_url+"false2.properties"));

    }
}
