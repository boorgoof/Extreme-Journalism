package it.unipd.dei.dbdc.analysis.src_printers;

import it.unipd.dei.dbdc.analysis.AnalyzerHandlerTest;
import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;
import it.unipd.dei.dbdc.tools.PathManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import it.unipd.dei.dbdc.tools.PathManagerTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Class that tests {@link TxtOutPrinter}. It also uses {@link PathManagerTest#readFile(String)} to check the printed files.
 */
@Order(7)
public class TxtOutPrinterTest {

    /**
     * Initializes by setting the output folder to the one that this class will access
     *
     */
    @BeforeAll
    public static void initialize()
    {
        PathManager.setOutputFolder(AnalyzerHandlerTest.resources_url);
    }

    /**
     * Restores the output folder
     *
     */
    @AfterAll
    public static void end()
    {
        PathManager.setOutputFolder("./output/");
    }


    /**
     * Tests of {@link TxtOutPrinter#outFile(List)}.
     * It uses {@link PathManagerTest#readFile(String)} to check the printed files.
     */
    @Test
    public void outFile()
    {
        TxtOutPrinter printer = new TxtOutPrinter();
        TestPrinter printerTest = new TestPrinter();

        //Tests with null
        assertThrows(IllegalArgumentException.class, ()-> printer.outFile(null));
        final List<OrderedEntryStringInt> list = new ArrayList<>();
        assertThrows(IllegalArgumentException.class, ()-> printer.outFile(list));

        list.add(new OrderedEntryStringInt("a", 2));
        assertDoesNotThrow( () -> assertEquals(AnalyzerHandlerTest.resources_url+"output.txt", printer.outFile(list)));

        //Check the files produced
        assertDoesNotThrow( () ->
        {
            String output = PathManagerTest.readFile(printer.outFile(list));
            String expected = PathManagerTest.readFile(printerTest.outFile(list));
            assertEquals("a 2 ", output);
            assertEquals(output, expected);

            list.add(new OrderedEntryStringInt("all", 5));
            output = PathManagerTest.readFile(printer.outFile(list));
            expected = PathManagerTest.readFile(printerTest.outFile(list));
            assertEquals("a 2 all 5 ", output);
            assertEquals(output, expected);

            list.add(new OrderedEntryStringInt("u", 1));
            output = PathManagerTest.readFile(printer.outFile(list));
            expected = PathManagerTest.readFile(printerTest.outFile(list));
            assertEquals("a 2 all 5 u 1 ", output);
            assertEquals(output, expected);

            list.add(new OrderedEntryStringInt("zeta", 567));
            output = PathManagerTest.readFile(printer.outFile(list));
            expected = PathManagerTest.readFile(printerTest.outFile(list));
            assertEquals("a 2 all 5 u 1 zeta 567 ", output);
            assertEquals(output, expected);

            list.add(new OrderedEntryStringInt("ugo", 34));
            list.add(new OrderedEntryStringInt("nic", 345));
            list.add(new OrderedEntryStringInt("ubo", 34));
            list.add(new OrderedEntryStringInt("nick", 345));
            output = PathManagerTest.readFile(printer.outFile(list));
            expected = PathManagerTest.readFile(printerTest.outFile(list));
            assertEquals("a 2 all 5 u 1 zeta 567 ugo 34 nic 345 ubo 34 nick 345 ", output);
            assertEquals(output, expected);
        });
    }

    /**
     * The only constructor of the class. It is declared as private to
     * prevent the default constructor to be created.
     */
    private TxtOutPrinterTest() {}
}
