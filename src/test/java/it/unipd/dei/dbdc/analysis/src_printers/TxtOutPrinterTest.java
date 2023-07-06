package it.unipd.dei.dbdc.analysis.src_printers;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import it.unipd.dei.dbdc.tools.PathManagerTest;

import static org.junit.jupiter.api.Assertions.*;

public class TxtOutPrinterTest {
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
        assertDoesNotThrow( () -> assertEquals("./output/output.txt", printer.outFile(list)));

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
}
