package it.unipd.dei.dbdc.analysis.src_printers;

import it.unipd.dei.dbdc.analysis.OrderedEntryStringInt;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            String output = readFile(printer.outFile(list));
            String expected = readFile(printerTest.outFile(list));
            assertEquals("a 2 ", output);
            assertEquals(output, expected);

            list.add(new OrderedEntryStringInt("all", 5));
            output = readFile(printer.outFile(list));
            expected = readFile(printerTest.outFile(list));
            assertEquals("a 2 all 5 ", output);
            assertEquals(output, expected);

            list.add(new OrderedEntryStringInt("u", 1));
            output = readFile(printer.outFile(list));
            expected = readFile(printerTest.outFile(list));
            assertEquals("a 2 all 5 u 1 ", output);
            assertEquals(output, expected);

            list.add(new OrderedEntryStringInt("zeta", 567));
            output = readFile(printer.outFile(list));
            expected = readFile(printerTest.outFile(list));
            assertEquals("a 2 all 5 u 1 zeta 567 ", output);
            assertEquals(output, expected);

            list.add(new OrderedEntryStringInt("ugo", 34));
            list.add(new OrderedEntryStringInt("nic", 345));
            list.add(new OrderedEntryStringInt("ubo", 34));
            list.add(new OrderedEntryStringInt("nick", 345));
            output = readFile(printer.outFile(list));
            expected = readFile(printerTest.outFile(list));
            assertEquals("a 2 all 5 u 1 zeta 567 ugo 34 nic 345 ubo 34 nick 345 ", output);
            assertEquals(output, expected);
        });
    }

    public static String readFile(String file)
    {
        try (FileReader read = new FileReader(file); Scanner sc = new Scanner(read)) {
            StringBuilder ret = new StringBuilder();
            while (sc.hasNext())
            {
                ret.append(sc.next()).append(" ");
            }
            return ret.toString();
        }
        catch (IOException e)
        {
            fail("Not able to read file");
            return null;
        }
    }
}
